package com.dxckie.capri

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.view.View
import android.view.ViewGroup
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random

/**
 * Capri's lightning teleport: a heavy, flat red zigzag slash from where she was to where she lands.
 * Timed to her 17-frame walk strip at 24 fps: frames 0-3 she shatters (the strip), 4-12 the head of the
 * bolt runs to the target while she is hidden, 13-16 the bolt collapses onto the target as she re-forms.
 * Full-screen and touch-transparent; removes itself when done. Main thread only.
 */
internal class BoltView(context: Context, private val route: List<PointF>) : View(context) {
	private val started = System.nanoTime()
	private val fill = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = 0xFFFF003D.toInt(); style = Paint.Style.FILL }
	private val cum = FloatArray(route.size).also { for (i in 1 until route.size) it[i] = it[i - 1] + hypot(route[i].x - route[i - 1].x, route[i].y - route[i - 1].y) }
	private val total = cum.last().coerceAtLeast(1f)
	private val radius = resources.displayMetrics.density * 7f

	override fun onDraw(canvas: Canvas) {
		val f = ((System.nanoTime() - started) / 1e9f * FPS).toInt()
		if (f >= FRAMES) { (parent as? ViewGroup)?.post { (parent as? ViewGroup)?.removeView(this) }; return }
		val (a, b) = when {
			f < 3 -> 0f to 0f
			f == 3 -> 0f to 0.05f
			f <= 12 -> 0f to HEAD[f - 4]
			else -> (1f - REACH[f - 13]) to 1f
		}
		if (b > a) canvas.drawPath(ribbon(a, b), fill)
		postInvalidateOnAnimation()
	}

	private fun at(frac: Float): PointF {
		val d = frac * total
		var i = 1
		while (i < route.size - 1 && cum[i] < d) i++
		val seg = (cum[i] - cum[i - 1]).coerceAtLeast(0.001f)
		val t = ((d - cum[i - 1]) / seg).coerceIn(0f, 1f)
		return PointF(route[i - 1].x + (route[i].x - route[i - 1].x) * t, route[i - 1].y + (route[i].y - route[i - 1].y) * t)
	}

	/** The route between two fractions of its length as a jagged, asymmetric ribbon, thickest in the middle. */
	private fun ribbon(a: Float, b: Float): Path {
		val pts = ArrayList<PointF>()
		pts.add(at(a))
		for (i in 1 until route.size - 1) if (cum[i] > a * total && cum[i] < b * total) pts.add(route[i])
		pts.add(at(b))
		val up = ArrayList<PointF>(); val low = ArrayList<PointF>()
		for ((i, p) in pts.withIndex()) {
			val l = pts[max(0, i - 1)]; val r = pts[min(pts.size - 1, i + 1)]
			val len = hypot(r.x - l.x, r.y - l.y).coerceAtLeast(0.001f)
			val nx = -(r.y - l.y) / len; val ny = (r.x - l.x) / len
			val u = if (pts.size > 1) i / (pts.size - 1f) else 0f
			val rad = radius * (0.25f + 0.75f * sin(Math.PI.toFloat() * (0.08f + 0.84f * u)))
			val hi = rad * (if (i % 2 == 1) 0.63f else 1.12f); val lo = rad * (if (i % 2 == 1) 1.06f else 0.59f)
			up.add(PointF(p.x + nx * hi, p.y + ny * hi)); low.add(PointF(p.x - nx * lo, p.y - ny * lo))
		}
		val path = Path()
		path.moveTo(up[0].x, up[0].y)
		for (p in up.drop(1)) path.lineTo(p.x, p.y)
		for (p in low.reversed()) path.lineTo(p.x, p.y)
		path.close()
		return path
	}

	companion object {
		private const val FPS = 24f
		private const val FRAMES = 17
		private val HEAD = floatArrayOf(0.11f, 0.19f, 0.30f, 0.42f, 0.55f, 0.67f, 0.79f, 0.90f, 1f)
		private val REACH = floatArrayOf(0.26f, 0.19f, 0.11f, 0.055f)
		private val CORNERS = arrayOf(
			floatArrayOf(0.15f, -0.28f), floatArrayOf(0.29f, 0.62f), floatArrayOf(0.40f, -0.59f), floatArrayOf(0.56f, 0.76f),
			floatArrayOf(0.69f, 0.24f), floatArrayOf(0.79f, -0.70f), floatArrayOf(0.90f, 0.15f),
		)

		/** A zigzag from [from] to [to] with sharp corners, kept inside the screen. */
		fun route(from: PointF, to: PointF, w: Float, h: Float, density: Float): List<PointF> {
			val dx = to.x - from.x; val dy = to.y - from.y
			val dist = hypot(dx, dy).coerceAtLeast(1f)
			val ax = dx / dist; val ay = dy / dist
			val amp = (dist * 0.22f).coerceIn(density * 36f, density * 120f)
			val mirror = if (Random.nextBoolean()) 1f else -1f
			val pts = arrayListOf(from)
			for ((t, off) in CORNERS) {
				val jitter = 0.73f + Random.nextFloat() * 0.45f
				val px = from.x + ax * dist * t - ay * off * amp * mirror * jitter
				val py = from.y + ay * dist * t + ax * off * amp * mirror * jitter
				pts.add(PointF(px.coerceIn(12f, w - 12f), py.coerceIn(12f, h - 12f)))
			}
			pts.add(to)
			return pts
		}
	}
}
