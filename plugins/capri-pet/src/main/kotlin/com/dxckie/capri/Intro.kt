package com.dxckie.capri

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.LightingColorFilter
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
import kotlin.math.sin
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
	private val TIMELINE = listOf(0f to 0, 0.18f to 1, 0.5f to 2) + (0 until 11).map { 0.5f + it * 0.13f to (if (it % 2 == 0) 3 else 2) } + listOf(1.95f to 1)

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
			val idx = TIMELINE.indexOfLast { it.first <= t }
			val f = TIMELINE[idx].second
			val since = t - TIMELINE[idx].first
			val bannerH = w * fh / fw
			scrim.alpha = (fade * 150).toInt()
			canvas.drawRect(0f, 0f, w, h, scrim)
			// Punches in from larger and settles; wave frames sway and bob.
			val zoom = 1f + 0.22f * (1f - min(t / 0.35f, 1f)).let { it * it }
			val wave = if (f >= 2 && t < 1.9f) sin(t * 24f) else 0f
			canvas.save()
			canvas.translate(w / 2f, h / 2f + wave * bannerH * 0.012f)
			canvas.scale(zoom, zoom)
			canvas.rotate(wave * 0.9f)
			canvas.translate(-w / 2f, -bannerH / 2f)
			// Each new frame dissolves in from the last over a moment, with colour-split ghosts that settle.
			val k = (since / 0.07f).coerceIn(0f, 1f)
			if (idx > 0 && k < 1f) drawBanner(canvas, bmp, TIMELINE[idx - 1].second, fade * (1f - k), w, fw, fh, 0f, null)
			drawBanner(canvas, bmp, f, fade * (if (idx > 0) k else 1f), w, fw, fh, 1f, null)
			val split = w * 0.012f * (1f - min(since / 0.25f, 1f)).coerceAtLeast(if (t < 0.6f) 0.4f else 0f)
			if (split > 0f) {
				drawBanner(canvas, bmp, f, fade * 0.35f, w, fw, fh, 1f, redTint, -split)
				drawBanner(canvas, bmp, f, fade * 0.35f, w, fw, fh, 1f, cyanTint, split)
			}
			canvas.restore()
		}

		private val redTint = LightingColorFilter(0xFFFF2040.toInt(), 0)
		private val cyanTint = LightingColorFilter(0xFF20FFE0.toInt(), 0)

		/** Frame [f] in torn bands across the banner (a few thrown sideways), shifted by [shift]. */
		private fun drawBanner(canvas: Canvas, bmp: Bitmap, f: Int, alpha: Float, w: Float, fw: Int, fh: Int, torn: Float, tint: ColorFilter?, shift: Float = 0f) {
			if (alpha <= 0f) return
			paint.alpha = (alpha.coerceIn(0f, 1f) * 255).toInt(); paint.colorFilter = tint
			val t = now() - startedAt
			val rnd = Random((t * 24f).toInt() * 41 + 3)
			val chance = (if (t < 0.6f) 0.35f else 0.1f) * torn
			val band = 5
			var y = 0
			while (y < fh) {
				val bh = min(band, fh - y)
				val dx = shift + if (rnd.nextFloat() < chance) rnd.nextInt(-10, 11) * w / 340f else 0f
				canvas.drawBitmap(bmp, Rect(f * fw, y, (f + 1) * fw, y + bh), RectF(dx, y * w / fw, dx + w, (y + bh) * w / fw), paint)
				y += bh
			}
			paint.colorFilter = null
		}

		override fun setAlpha(alpha: Int) {}
		override fun setColorFilter(colorFilter: ColorFilter?) {}
		@Deprecated("Deprecated in Java")
		override fun getOpacity() = PixelFormat.TRANSLUCENT
	}
}
