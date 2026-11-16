package com.dxckie.capri

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.util.Base64
import android.view.Choreographer
import android.view.ViewGroup
import kotlin.math.min
import kotlin.random.Random

/**
 * Capri's intro, once each time Discord opens: a banner across the middle of the screen plays her 4-frame
 * intro (arms flying in, her face, a wave), torn sideways in bands for a glitchy look, then fades out.
 * Drawn on the window's overlay, so it works on any Android. Main thread only.
 */
internal object Intro {
	private const val FRAMES = 4
	private const val SECONDS = 3.0f
	/** Which frame shows from what second: arms in, face, then a wave back and forth, then the face again. */
	private val TIMELINE = listOf(0f to 0, 0.18f to 1, 0.5f to 2, 0.8f to 3, 1.05f to 2, 1.3f to 3, 1.55f to 2, 1.8f to 3, 2.05f to 1)

	private var played = false
	private var decor: ViewGroup? = null
	private var layer: Layer? = null
	private var running = false
	private var startedAt = 0f
	private val strip: Bitmap? by lazy {
		runCatching {
			val bytes = Base64.decode(IntroStrip.data, Base64.DEFAULT)
			BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
		}.getOrNull()
	}

	private fun now() = SystemClock.uptimeMillis() / 1000f

	/** Plays it the first time this is called after Discord starts; later calls do nothing. */
	fun playOnce(root: ViewGroup) {
		if (played || running || strip == null || root.width <= 0) return
		played = true
		decor = root
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

	private class Layer : Drawable() {
		private val paint = Paint().apply { isFilterBitmap = false }
		private val scrim = Paint().apply { color = 0xFF000000.toInt() }

		override fun draw(canvas: Canvas) {
			val root = decor ?: return
			val bmp = strip ?: return
			if (bounds.width() != root.width || bounds.height() != root.height) setBounds(0, 0, root.width, root.height)
			val w = root.width.toFloat(); val h = root.height.toFloat()
			val t = now() - startedAt
			val fade = min(min(t / 0.12f, (SECONDS - t) / 0.5f), 1f).coerceIn(0f, 1f)
			val fw = bmp.width / FRAMES; val fh = bmp.height
			val f = TIMELINE.last { it.first <= t }.second
			val bannerH = w * fh / fw
			val top = (h - bannerH) / 2f
			scrim.alpha = (fade * 150).toInt()
			canvas.drawRect(0f, 0f, w, h, scrim)
			paint.alpha = (fade * 255).toInt()
			// Bands of the picture, a few thrown sideways each frame; it jolts hardest as the arms land.
			val rnd = Random((t * 24f).toInt() * 41 + 3)
			val chance = if (t < 0.6f) 0.35f else 0.1f
			val band = 5
			var y = 0
			while (y < fh) {
				val bh = min(band, fh - y)
				val dx = if (rnd.nextFloat() < chance) rnd.nextInt(-10, 11) * w / 340f else 0f
				val src = Rect(f * fw, y, (f + 1) * fw, y + bh)
				val dst = RectF(dx, top + y * w / fw, dx + w, top + (y + bh) * w / fw)
				canvas.drawBitmap(bmp, src, dst, paint)
				y += bh
			}
		}

		override fun setAlpha(alpha: Int) {}
		override fun setColorFilter(colorFilter: ColorFilter?) {}
		@Deprecated("Deprecated in Java")
		override fun getOpacity() = PixelFormat.TRANSLUCENT
	}
}
