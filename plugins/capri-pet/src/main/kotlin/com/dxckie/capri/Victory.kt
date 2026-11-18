package com.dxckie.capri

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.util.Base64
import android.view.Choreographer
import android.view.ViewGroup
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random

/**
 * Capri's happy moment: a burst of white, red and purple rays behind her, then a slanted banner with her
 * face slides in beside her and out again. Redrawn 24 times a second. Drawn on the window's overlay, so it
 * works on any Android. Main thread only.
 */
internal object Victory {
	private const val SECONDS = 2.6f
	private const val BURST = 0.8f
	private const val BANNER_IN = 0.5f
	private const val BANNER_OUT = 2.3f
	private const val FPS = 24f
	private const val RED = 0xFFFF003D.toInt()
	private const val PURPLE = 0xFF7A3DFF.toInt()
	private const val WHITE = 0xFFFFFFFF.toInt()
	private const val INK = 0xFF14101F.toInt()

	private val fill = Paint().apply { style = Paint.Style.FILL; isAntiAlias = true }
	private val stroke = Paint().apply { style = Paint.Style.STROKE; isAntiAlias = true; strokeCap = Paint.Cap.BUTT }

	private var decor: ViewGroup? = null
	private var layer: Layer? = null
	private var running = false
	private var startedAt = 0f
	private var lastEnd = -100f
	private var capriX = 0f
	private var capriY = 0f
	private var rays = emptyList<FloatArray>()
	private var portrait: Bitmap? = null

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
		if (portrait == null) runCatching {
			val bytes = Base64.decode(CapriBubbleData.PORTRAIT, Base64.DEFAULT)
			portrait = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
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
			if (t > BANNER_IN) drawBanner(canvas, root.width.toFloat(), t, rnd)
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

		/** A slanted banner with her face, in from the side with more room (mirrored when that is the left), speed lines trailing. */
		private fun drawBanner(canvas: Canvas, screenW: Float, t: Float, rnd: Random) {
			val bw = dp(150f); val bh = dp(52f); val skew = dp(16f)
			val right = capriX < screenW / 2f
			val cx = if (right) capriX else screenW - capriX
			val inK = ((t - BANNER_IN) / 0.25f).coerceIn(0f, 1f).let { 1f - (1f - it) * (1f - it) }
			val outK = ((t - BANNER_OUT) / 0.25f).coerceIn(0f, 1f)
			val slide = inK * (1f - outK * outK)
			val restX = min(cx + dp(34f), screenW - bw - skew - dp(8f))
			val shake = if (t < BANNER_IN + 0.5f) (rnd.nextFloat() - 0.5f) * dp(3f) else 0f
			val bx = screenW + (restX - screenW) * slide + shake
			val y0 = capriY - bh * 0.9f
			canvas.save()
			if (!right) canvas.scale(-1f, 1f, screenW / 2f, 0f)
			path.reset()
			path.moveTo(bx + skew, y0); path.lineTo(bx + bw + skew, y0); path.lineTo(bx + bw, y0 + bh); path.lineTo(bx, y0 + bh); path.close()
			fill.color = INK; fill.alpha = 255
			canvas.drawPath(path, fill)
			portrait?.let { p ->
				canvas.save(); canvas.clipPath(path)
				val ph = bh * 1.5f
				val mx = bx + bw / 2f + skew / 2f; val my = y0 + bh / 2f
				canvas.drawBitmap(p, null, RectF(mx - ph / 2f, my - ph / 2f, mx + ph / 2f, my + ph / 2f), null)
				canvas.restore()
			}
			stroke.strokeWidth = dp(4f); stroke.color = PURPLE
			canvas.drawLine(bx, y0 + bh, bx + bw, y0 + bh, stroke)
			stroke.strokeWidth = dp(1.5f); stroke.color = RED
			canvas.drawLine(bx + skew, y0, bx + bw + skew, y0, stroke)
			// Speed lines trailing the banner while it moves.
			if (slide in 0.05f..0.95f) {
				stroke.strokeWidth = dp(1.2f); stroke.color = WHITE
				repeat(3) {
					val ly = y0 + bh * (0.2f + rnd.nextFloat() * 0.6f)
					val lx = bx + bw + skew + dp(8f) + rnd.nextFloat() * dp(30f)
					canvas.drawLine(lx, ly, lx + dp(14f + rnd.nextFloat() * 24f), ly, stroke)
				}
			}
			canvas.restore()
		}

		override fun setAlpha(alpha: Int) {}
		override fun setColorFilter(colorFilter: ColorFilter?) {}
		@Deprecated("Deprecated in Java")
		override fun getOpacity() = PixelFormat.TRANSLUCENT
	}
}
