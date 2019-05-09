package com.lee.avengergone

import android.animation.ValueAnimator
import android.graphics.*
import android.view.View
import kotlin.random.Random
import android.graphics.PathMeasure

class DisappearAnimator(
    root: DisappearView,
    bitmap: Bitmap,
    rect: Rect
) : ValueAnimator() {

    var mRoot: View? = null
    var mBitmap: Bitmap? = null
    var mRect: Rect = Rect()
    var mElement: MutableList<Element> = mutableListOf()

    var mPaint: Paint = Paint()

    init {

        mPaint.isAntiAlias = true
        mRoot = root
        mBitmap = bitmap
        mRect = Rect(rect)
        val elementLen = 20

        val w = mBitmap!!.width / elementLen + 1
        val h = mBitmap!!.height / elementLen + 1
        for (i in 0..w) {
            for (j in 0..h) {
                var element = generateElement(i, j, elementLen, mBitmap!!)
                if (element != null) {
                    mElement.add(element)
                }
            }
        }

    }

    private fun generateElement(i: Int, j: Int, elementLen: Int, bitmap: Bitmap): Element? {
        val element = Element()
        element.row = i
        element.col = j
        element.x = i * elementLen + mRect.left
        element.y = j * elementLen + mRect.top
        element.originX = element.x
        element.originY = element.y
        if (element.x + elementLen <= mRect.right) {
            element.w = elementLen
        } else {
            element.w = mRect.right - element.x
        }
        if (element.w <= 0) {
            return null
        }
        if (element.y + elementLen <= mRect.bottom) {
            element.h = elementLen
        } else {
            element.h = mRect.bottom - element.y
        }

        if (element.h <= 0) {
            return null
        }
        element.alpha = 1F
        val path = Path()

        path.moveTo(element.x.toFloat(), element.y.toFloat())
        path.rQuadTo(
            (Random.nextInt(150)).toFloat(),
            (-Random.nextInt(150)).toFloat(),
            (100 + Random.nextInt(150)).toFloat(),
            (-100 - Random.nextInt(150)).toFloat()
        )
        element.path = path

        element.bitmap = Bitmap.createBitmap(bitmap, i * elementLen, j * elementLen, element.w, element.h)

        return element
    }

    fun draw(canvas: Canvas?): Boolean {

        if (!isStarted) {
            return false
        }

        var top = mRect.top
        var bottom = mRect.bottom
        var left = mRect.left
        var right = mRect.right
        val line = Line()


        line.k = (bottom - top).toFloat() / (right - left)
        if (animatedFraction <= 0.4) {
            line.x = (right - (right - left) * 2.5 * animatedFraction).toFloat()
            line.y = top.toFloat()

        } else if (animatedFraction > 0.4 && animatedFraction <= 0.8) {
            line.x = left.toFloat()
            line.y = (top + (bottom - top) * 2.5 * (animatedFraction - 0.4)).toFloat()
        }
        for (element in mElement) {
            element.animate(animatedFraction, line)
            if (element.alpha > 0f) {
                // alpha change
                mPaint.alpha = (element.alpha * 255).toInt()
                canvas?.drawBitmap(element.bitmap!!, element.x.toFloat(), element.y.toFloat(), mPaint)
            }
        }
        mRoot!!.invalidate()
        return true
    }

    override fun start() {
        super.start()
        mRoot!!.invalidate()
    }

    fun recycle() {
        for (element in mElement) {
            element.bitmap?.recycle()
            element.bitmap = null
        }
    }


    inner class Element {


        var row: Int = 0
        var col: Int = 0
        var x: Int = 0
        var y: Int = 0
        var bitmap: Bitmap? = null
        var w: Int = 0
        var h: Int = 0
        var alpha: Float = 1f
        var path: Path = Path()
        var originX = 0;
        var originY = 0;

        fun animate(faction: Float, line: Line) {
            //If any of the elements are not completely below the line,
            // then start moving
            var below = judgeBelowLine(line)

            if (!below || faction > 0.8f) {
                x = (originX + faction * 300).toInt()
                y = (originY - faction * 300).toInt()
                alpha = 1 - faction
                val pathMeasure = PathMeasure()
                pathMeasure.setPath(path, false)
                val pathLength = pathMeasure.length
                val pos = FloatArray(2)
                pathMeasure.getPosTan(pathLength * faction, pos, null)
                x = pos[0].toInt()
                y = pos[1].toInt()

            }
        }

        private fun judgeBelowLine(line: Line): Boolean {
            return line.y + line.k * (x + w - line.x) < y
        }

        override fun toString(): String {
            return "Element(x=$x, y=$y, bitmap=$bitmap, w=$w, h=$h, alpha=$alpha, path=$path)"
        }

    }

    // A Line
    data class Line(var k: Float = 0f, var x: Float = 0f, var y: Float = 0f)
}