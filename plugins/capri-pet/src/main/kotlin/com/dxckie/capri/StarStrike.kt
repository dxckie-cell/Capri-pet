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
import kotlin.math.hypot
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random

/**
 * Capri's star strike, in place of the spell: a faint constellation and one bright star appear, a trail of
 * sparkles runs from her to the star, then a bolt strikes it and it bursts into rays and a ring, and a
 * crosshair holds on it as everything fades. Drawn on the window's overlay, so it works on any Android.
 * Main thread only.
 */
internal object StarStrike {
	private const val SECONDS = 4.4f
	private const val BOLT_AT = 2.0f
	private const val RED = 0xFFFF003D.toInt()
	private const val PURPLE = 0xFF7A3DFF.toInt()
	private const val LAVENDER = 0xFFC9B8FF.toInt()

	private val fill = Paint().apply { style = Paint.Style.FILL; isAntiAlias = true }
	private val line = Paint().apply { style = Paint.Style.STROKE; isAntiAlias = true; strokeJoin = Paint.Join.MITER; strokeCap = Paint.Cap.SQUARE }

	private var decor: ViewGroup? = null
	private var layer: Layer? = null
	private var running = false
	private var startedAt = 0f
	private var fromX = 0f
	private var fromY = 0f
	private var toX = 0f
	private var toY = 0f
	private var stars = FloatArray(0)
	private var rays = emptyList<FloatArray>()
	private var zig = emptyList<Pair<Float, Float>>()

	private fun now() = SystemClock.uptimeMillis() / 1000f

	val active get() = running

	/** Starts it, from Capri at ([x], [y]); a no-op while one is playing. */
	fun start(root: ViewGroup, x: Float, y: Float) {
		if (running) return
		val w = root.width.toFloat(); val h = root.height.toFloat()
		if (w <= 0f) return
		decor = root
		fromX = x; fromY = y
		// The star hangs high, on the side farther from her.
		toX = if (x < w / 2f) w * (0.62f + Random.nextFloat() * 0.25f) else w * (0.13f + Random.nextFloat() * 0.25f)
		toY = h * (0.12f + Random.nextFloat() * 0.18f)
		// The constellation: dots in a patch beside her, x y size.
		val cx = if (x < w / 2f) w * 0.3f else w * 0.7f
		stars = FloatArray(30 * 3).also { a ->
			for (i in 0 until 30) {
				a[i * 3] = cx + (Random.nextFloat() - 0.5f) * w * 0.5f
				a[i * 3 + 1] = h * (0.25f + Random.nextFloat() * 0.3f)
				a[i * 3 + 2] = dp(1f + Random.nextFloat() * 1.6f)
			}
		}
		// Burst rays: angle, length, half-width (radians), colour.
		rays = List(30) {
			floatArrayOf(Random.nextFloat() * 6.2832f, dp(30f + Random.nextFloat() * 70f), 0.02f + Random.nextFloat() * 0.05f,
				when (Random.nextInt(4)) { 0 -> RED.toFloat(); 1 -> PURPLE.toFloat(); else -> 0xFFFFFFFF.toInt().toFloat() })
		}
		// A tight zigzag from her to the star.
		val n = 16
		val dx = toX - x; val dy = toY - y
		val len = hypot(dx, dy).coerceAtLeast(1f)
		val nx = -dy / len; val ny = dx / len
		zig = List(n + 1) { i ->
			val k = i / n.toFloat()
			val off = if (i == 0 || i == n) 0f else (if (i % 2 == 0) 1f else -1f) * dp(9f + Random.nextFloat() * 6f)
			(x + dx * k + nx * off) to (y + dy * k + ny * off)
		}
		startedAt = now()
		layer = Layer().also { root.overlay.add(it) }
		running = true
		Choreographer.getInstance().postFrameCallback(frame)
	}

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

	/** A four-pointed star with concave sides, [r] tall and wide. */
	private fun starPath(cx: Float, cy: Float, r: Float): Path {
		val i = r * 0.2f
		return Path().apply {
			moveTo(cx, cy - r); lineTo(cx + i, cy - i); lineTo(cx + r, cy); lineTo(cx + i, cy + i)
			lineTo(cx, cy + r); lineTo(cx - i, cy + i); lineTo(cx - r, cy); lineTo(cx - i, cy - i); close()
		}
	}

	private class Layer : Drawable() {
		override fun draw(canvas: Canvas) {
			val root = decor ?: return
			if (bounds.width() != root.width || bounds.height() != root.height) setBounds(0, 0, root.width, root.height)
			val w = root.width.toFloat(); val h = root.height.toFloat()
			val t = now() - startedAt
			val fade = min(1f, (SECONDS - t) / 0.7f).coerceIn(0f, 1f)
			fun a(v: Float) = (v.coerceIn(0f, 1f) * fade * 255).toInt()

			// 1. Constellation fades in; the bright star twinkles in a little later.
			val dim = (t / 0.6f).coerceIn(0f, 1f)
			for (i in 0 until stars.size / 3) {
				fill.color = LAVENDER; fill.alpha = a(dim * (0.4f + 0.4f * sin(t * 3f + i)))
				canvas.drawCircle(stars[i * 3], stars[i * 3 + 1], stars[i * 3 + 2], fill)
			}
			line.color = PURPLE; line.strokeWidth = dp(0.8f); line.alpha = a(dim * 0.5f)
			for (i in 0 until 9) canvas.drawLine(stars[i * 3], stars[i * 3 + 1], stars[(i + 1) * 3], stars[(i + 1) * 3 + 1], line)
			val grow = ((t - 0.3f) / 0.4f).coerceIn(0f, 1f)
			val twinkle = 1f + 0.12f * sin(t * 9f)
			val struck = t >= BOLT_AT
			val burst = ((t - BOLT_AT) / 0.6f).coerceIn(0f, 1f)

			// 2. Trail of sparkles from her to the star, growing toward it.
			val trail = ((t - 1.0f) / 0.9f).coerceIn(0f, 1f)
			val dots = 36
			for (i in 0 until (dots * trail).toInt()) {
				val k = i / dots.toFloat()
				fill.color = if (i % 5 == 0) 0xFFFFFFFF.toInt() else LAVENDER
				fill.alpha = a(0.35f + 0.6f * k)
				canvas.drawCircle(fromX + (toX - fromX) * k, fromY + (toY - fromY) * k, dp(0.8f + 2.2f * k), fill)
			}

			// 3. The strike: a ring round her, the zigzag bolt, then the burst at the star.
			if (struck) {
				val fl = (1f - (t - BOLT_AT) / 0.55f).coerceIn(0f, 1f)
				if (fl > 0f) {
					line.color = RED; line.alpha = a(fl); line.strokeWidth = dp(1.5f)
					canvas.drawCircle(fromX, fromY, dp(34f) + dp(8f) * (1f - fl), line)
					val path = Path()
					zig.forEachIndexed { i, (px, py) -> if (i == 0) path.moveTo(px, py) else path.lineTo(px, py) }
					line.color = PURPLE; line.strokeWidth = dp(7f); line.alpha = a(fl); canvas.drawPath(path, line)
					line.color = RED; line.strokeWidth = dp(5f); canvas.drawPath(path, line)
					line.color = 0xFFFFFFFF.toInt(); line.strokeWidth = dp(2.2f); canvas.drawPath(path, line)
				}
				val rayFade = (1f - burst).coerceIn(0f, 1f) * (if (burst < 0.1f) 1f else 0.9f)
				for (r in rays) {
					val len = r[1] * (0.4f + 0.6f * burst); val r0 = dp(22f) + dp(14f) * burst
					val ang = r[0]; val half = r[2]
					val p = Path()
					p.moveTo(toX + cos(ang - half) * r0, toY + sin(ang - half) * r0)
					p.lineTo(toX + cos(ang) * (r0 + len), toY + sin(ang) * (r0 + len))
					p.lineTo(toX + cos(ang + half) * r0, toY + sin(ang + half) * r0)
					p.close()
					fill.color = r[3].toInt(); fill.alpha = a(rayFade)
					canvas.drawPath(p, fill)
				}
			}

			// 4. The star itself, ringed in pink and purple; after the strike, a crosshair holds on it.
			val sr = dp(11f) * grow * twinkle * (if (struck) 1.9f else 1f)
			if (grow > 0f) {
				if (struck) {
					line.color = RED; line.strokeWidth = dp(1.6f); line.alpha = a(1f)
					canvas.drawCircle(toX, toY, dp(40f), line)
					line.color = PURPLE; line.alpha = a(0.8f)
					canvas.drawCircle(toX, toY, dp(44f), line)
					if (burst >= 0.7f) {
						line.color = LAVENDER; line.strokeWidth = dp(1.4f); line.alpha = a(0.9f)
						canvas.drawLine(toX, toY - dp(110f), toX, toY + dp(110f), line)
						canvas.drawLine(toX - dp(110f), toY, toX + dp(110f), toY, line)
					}
				}
				fill.color = 0xFFFFFFFF.toInt(); fill.alpha = a(1f)
				canvas.drawPath(starPath(toX, toY, sr), fill)
				if (struck) { fill.color = RED; canvas.drawPath(starPath(toX, toY, sr * 0.42f), fill) }
			}
		}

		override fun setAlpha(alpha: Int) {}
		override fun setColorFilter(colorFilter: ColorFilter?) {}
		@Deprecated("Deprecated in Java")
		override fun getOpacity() = PixelFormat.TRANSLUCENT
	}
}
