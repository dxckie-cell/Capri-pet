package com.dxckie.capri

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.view.Choreographer
import android.view.ViewGroup
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random

/**
 * Capri's happy moment: a burst of white, red and purple rays behind her while shards of her hair fly off.
 * Redrawn 24 times a second. Drawn on the window's overlay, so it works on any Android. Main thread only.
 */
internal object Victory {
	private const val SECONDS = 1.0f
	private const val BURST = 0.8f
	private const val FPS = 24f
	private const val RED = 0xFFFF003D.toInt()
	private const val PURPLE = 0xFF7A3DFF.toInt()
	private const val WHITE = 0xFFFFFFFF.toInt()
	private const val GREEN = 0xFF00F452.toInt()

	private val fill = Paint().apply { style = Paint.Style.FILL; isAntiAlias = true }

	private var decor: ViewGroup? = null
	private var layer: Layer? = null
	private var running = false
	private var startedAt = 0f
	private var lastEnd = -100f
	private var capriX = 0f
	private var capriY = 0f
	private var rays = emptyList<FloatArray>()
	/** Hair shards: angle, speed, size, spin, colour. */
	private var shards = emptyList<FloatArray>()

	private fun now() = SystemClock.uptimeMillis() / 1000f

	val active get() = running

	/** Starts it, from Capri at ([x], [y]). Skips while one plays or one ended less than 8 s ago. */
	fun start(root: ViewGroup, x: Float, y: Float) {
		if (running || now() - lastEnd < 8f || root.width <= 0) return
		decor = root
		capriX = x; capriY = y
		// Rays: angle, length, half-width at the outer end (radians), colour.
		rays = List(34) {
			floatArrayOf(Random.nextFloat() * 6.2832f, dp(180f + Random.nextFloat() * 520f), 0.012f + Random.nextFloat() * 0.03f,
				when (Random.nextInt(10)) { 0, 1 -> RED.toFloat(); 2, 3 -> PURPLE.toFloat(); else -> WHITE.toFloat() })
		}
		shards = List(22) {
			floatArrayOf(Random.nextFloat() * 6.2832f, dp(70f + Random.nextFloat() * 230f), dp(2.5f + Random.nextFloat() * 4.5f), (Random.nextFloat() - 0.5f) * 14f,
				when (Random.nextInt(8)) { 0 -> PURPLE.toFloat(); 1 -> GREEN.toFloat(); 2 -> WHITE.toFloat(); else -> RED.toFloat() })
		}
		startedAt = now()
		layer = Layer().also { root.overlay.add(it) }
		running = true
		Choreographer.getInstance().postFrameCallback(frame)
	}

	fun follow(x: Float, y: Float) { capriX = x; capriY = y }

	fun clear() {
		if (running) lastEnd = now()
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
		private val path = Path()

		override fun draw(canvas: Canvas) {
			val root = decor ?: return
			if (bounds.width() != root.width || bounds.height() != root.height) setBounds(0, 0, root.width, root.height)
			val t = now() - startedAt
			val tick = (t * FPS).toInt()
			val rnd = Random(tick * 59 + 3)
			if (t < BURST) drawRays(canvas, t, rnd)
			drawShards(canvas, t)
		}

		/** Thin wedges flying out from behind her: they shoot out in a fifth of a second, hold, then fade. */
		private fun drawRays(canvas: Canvas, t: Float, rnd: Random) {
			val out = min(t / 0.2f, 1f).let { 1f - (1f - it) * (1f - it) }
			val alpha = ((BURST - t) / 0.35f).coerceIn(0f, 1f)
			for (r in rays) {
				val inner = dp(26f) + dp(34f) * out
				val outer = inner + r[1] * out
				val a = r[0]
				val hw = r[2]
				path.reset()
				path.moveTo(capriX + cos(a) * inner, capriY + sin(a) * inner)
				path.lineTo(capriX + cos(a - hw) * outer, capriY + sin(a - hw) * outer)
				path.lineTo(capriX + cos(a + hw) * outer, capriY + sin(a + hw) * outer)
				path.close()
				fill.color = r[3].toInt(); fill.alpha = (alpha * 255).toInt()
				canvas.drawPath(path, fill)
			}
		}

		/** Diamonds of her hair flying off in all directions, turning, slowing and fading. */
		private fun drawShards(canvas: Canvas, t: Float) {
			val k = (t * FPS).toInt() / FPS
			val alpha = ((SECONDS - k) / 0.45f).coerceIn(0f, 1f)
			for (sh in shards) {
				val dist = sh[1] * (1f - (1f - min(k / 0.7f, 1f)).let { it * it }) * 0.7f
				val px = capriX + cos(sh[0]) * (dp(14f) + dist)
				val py = capriY + sin(sh[0]) * (dp(14f) + dist) + k * k * dp(40f)
				val rot = sh[3] * k
				val s = sh[2]
				canvas.save(); canvas.rotate(rot * 57.3f, px, py)
				path.reset()
				path.moveTo(px, py - s * 1.5f); path.lineTo(px + s * 0.7f, py); path.lineTo(px, py + s * 1.5f); path.lineTo(px - s * 0.7f, py); path.close()
				fill.color = sh[4].toInt(); fill.alpha = (alpha * 255).toInt()
				canvas.drawPath(path, fill)
				canvas.restore()
			}
		}

		override fun setAlpha(alpha: Int) {}
		override fun setColorFilter(colorFilter: ColorFilter?) {}
		@Deprecated("Deprecated in Java")
		override fun getOpacity() = PixelFormat.TRANSLUCENT
	}
}
