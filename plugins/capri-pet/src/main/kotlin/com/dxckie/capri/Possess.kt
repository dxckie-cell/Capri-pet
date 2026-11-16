package com.dxckie.capri

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.view.Choreographer
import android.view.ViewGroup
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random

/**
 * Capri's possession, in place of bubbles: she dives into the screen. Red lightning runs round its
 * frame, eyes open across it with pupils that follow her, then they close and it all fades.
 * With the screen glitch on, bars of red, purple and green tear across the screen as well.
 * Drawn on the window's overlay, so it works on any Android. Main thread only.
 */
internal object Possess {
	private const val SECONDS = 3.6f
	private const val EYES = 9

	private class Eye(val x: Float, val y: Float, val r: Float, val open: Float, val close: Float)

	private val red = Paint().apply { color = 0xFFFF003D.toInt(); style = Paint.Style.STROKE; strokeWidth = dp(2.2f); isAntiAlias = false }
	private val purple = Paint().apply { color = 0xFFB44CFF.toInt(); style = Paint.Style.STROKE; strokeWidth = dp(1.6f); isAntiAlias = false }
	private val pink = Paint().apply { color = 0xFFFF96BE.toInt(); style = Paint.Style.STROKE; strokeWidth = dp(1.2f); isAntiAlias = false }
	private val fill = Paint().apply { style = Paint.Style.FILL; isAntiAlias = true }

	private var decor: ViewGroup? = null
	private var layer: Layer? = null
	private var running = false
	private var startedAt = 0f
	private var capriX = 0f
	private var capriY = 0f
	private var eyes = emptyList<Eye>()

	private fun now() = SystemClock.uptimeMillis() / 1000f

	val active get() = running

	/** Starts it, from Capri at ([x], [y]). Does nothing while one is already playing. */
	fun start(root: ViewGroup, x: Float, y: Float) {
		if (running) return
		decor = root
		capriX = x; capriY = y
		startedAt = now()
		val w = root.width.toFloat(); val h = root.height.toFloat()
		eyes = List(EYES) {
			val open = 0.4f + Random.nextFloat() * 1.1f
			Eye(
				w * (0.1f + Random.nextFloat() * 0.8f), h * (0.12f + Random.nextFloat() * 0.76f),
				dp(14f + Random.nextFloat() * 30f), open, SECONDS - 0.9f + Random.nextFloat() * 0.4f,
			)
		}
		layer = Layer().also { root.overlay.add(it) }
		running = true
		Choreographer.getInstance().postFrameCallback(frame)
	}

	fun follow(x: Float, y: Float) { capriX = x; capriY = y }

	fun clear() {
		running = false
		Choreographer.getInstance().removeFrameCallback(frame)
		layer?.let { l -> decor?.overlay?.remove(l) }
		layer = null
		decor = null
	}

	private val frame = object : Choreographer.FrameCallback {
		override fun doFrame(frameTimeNanos: Long) {
			if (!running) return
			if (now() - startedAt > SECONDS) { clear(); return }
			layer?.invalidateSelf()
			Choreographer.getInstance().postFrameCallback(this)
		}
	}

	private class Layer : Drawable() {
		private val clip = Path()
		private val bar = Paint().apply { style = Paint.Style.FILL }

		override fun draw(canvas: Canvas) {
			val root = decor ?: return
			if (bounds.width() != root.width || bounds.height() != root.height) setBounds(0, 0, root.width, root.height)
			val w = root.width.toFloat(); val h = root.height.toFloat()
			val t = now() - startedAt
			// Fades in over the first 0.3 s and out over the last 0.5 s.
			val fade = min(min(t / 0.3f, (SECONDS - t) / 0.5f), 1f).coerceIn(0f, 1f)
			val tick = (t * 14f).toInt()
			if (Settings.bubbleFx) drawGlitch(canvas, w, h, tick, fade)
			drawFrame(canvas, w, h, tick, fade)
			for ((i, e) in eyes.withIndex()) drawEye(canvas, e, i, t, fade)
		}

		/** A jagged bolt running the whole way round the screen edge, redrawn 14 times a second. */
		private fun drawFrame(canvas: Canvas, w: Float, h: Float, tick: Int, fade: Float) {
			val rnd = Random(tick * 31 + 7)
			val inset = dp(5f); val jag = dp(5f); val step = dp(15f)
			val path = Path()
			var first = true
			fun pt(x: Float, y: Float, nx: Float, ny: Float) {
				val o = (rnd.nextFloat() - 0.5f) * 2f * jag
				val px = x + nx * o; val py = y + ny * o
				if (first) { path.moveTo(px, py); first = false } else path.lineTo(px, py)
			}
			var x = inset; while (x < w - inset) { pt(x, inset, 0f, 1f); x += step }
			var y = inset; while (y < h - inset) { pt(w - inset, y, 1f, 0f); y += step }
			x = w - inset; while (x > inset) { pt(x, h - inset, 0f, 1f); x -= step }
			y = h - inset; while (y > inset) { pt(inset, y, 1f, 0f); y -= step }
			path.close()
			val a = (fade * 255).toInt()
			purple.alpha = a; pink.alpha = a; red.alpha = a
			canvas.save(); canvas.translate(dp(2f), -dp(1f)); canvas.drawPath(path, purple); canvas.restore()
			canvas.save(); canvas.translate(-dp(2f), dp(1f)); canvas.drawPath(path, pink); canvas.restore()
			canvas.drawPath(path, red)
		}

		/** Thin horizontal bars of red, purple and green that jump about, like a torn picture. */
		private fun drawGlitch(canvas: Canvas, w: Float, h: Float, tick: Int, fade: Float) {
			val rnd = Random(tick * 131 + 3)
			repeat(9) {
				val y = rnd.nextFloat() * h
				val x0 = rnd.nextFloat() * w * 0.7f
				val len = w * (0.1f + rnd.nextFloat() * 0.5f)
				bar.color = when (rnd.nextInt(3)) { 0 -> 0xFFFF003D.toInt(); 1 -> 0xFF7A3DFF.toInt(); else -> 0xFF00F452.toInt() }
				bar.alpha = (fade * (90 + rnd.nextInt(120))).toInt()
				canvas.drawRect(x0, y, x0 + len, y + dp(1.5f + rnd.nextFloat() * 2f), bar)
			}
		}

		/** One eye: green ring, blue iris and a white pupil turned toward Capri; opens, blinks, shuts. */
		private fun drawEye(canvas: Canvas, e: Eye, i: Int, t: Float, fade: Float) {
			val opening = ((t - e.open) / 0.25f).coerceIn(0f, 1f)
			val closing = ((e.close - t) / 0.25f).coerceIn(0f, 1f)
			// A quick blink now and then in between.
			val blink = if (((t * 1.3f + i * 0.37f) % 2.4f) < 0.12f) 0.15f else 1f
			val openness = min(opening, closing) * blink
			if (openness <= 0.02f) return
			val ang = atan2(capriY - e.y, capriX - e.x)
			val dist = min(hypot(capriX - e.x, capriY - e.y) / dp(200f), 1f)
			val shift = e.r * 0.28f * dist
			val ex = cos(ang) * shift; val ey = sin(ang) * shift
			canvas.save()
			canvas.translate(e.x, e.y)
			canvas.scale(1f, openness)
			clip.reset(); clip.addOval(RectF(-e.r, -e.r, e.r, e.r), Path.Direction.CW)
			canvas.clipPath(clip)
			fill.color = 0xFF6A3DFF.toInt(); fill.alpha = (fade * 255).toInt()
			canvas.drawCircle(0f, 0f, e.r, fill)
			fill.color = 0xFF00F452.toInt(); fill.alpha = (fade * 255).toInt()
			canvas.drawCircle(0f, 0f, e.r - dp(2f), fill)
			fill.color = 0xFF3D6BFF.toInt(); fill.alpha = (fade * 255).toInt()
			canvas.drawCircle(ex, ey, e.r * 0.62f, fill)
			fill.color = 0xFFFFFFFF.toInt(); fill.alpha = (fade * 255).toInt()
			canvas.drawOval(ex - e.r * 0.15f, ey - e.r * 0.26f, ex + e.r * 0.15f, ey + e.r * 0.26f, fill)
			canvas.restore()
		}

		override fun setAlpha(alpha: Int) {}
		override fun setColorFilter(colorFilter: ColorFilter?) {}
		@Deprecated("Deprecated in Java")
		override fun getOpacity() = PixelFormat.TRANSLUCENT
	}
}
