package com.lee.avengergone

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AccelerateInterpolator

class DisappearView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    companion object {
        @JvmStatic
        fun attach(activity: Activity): DisappearView {
            val root = activity.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
            val disappearView = DisappearView(activity)
            root.addView(disappearView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            return disappearView
        }
    }

    private val mDisappearAnimators = mutableListOf<DisappearAnimator>()


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (animator in mDisappearAnimators) {
            animator.draw(canvas);
        }
    }

    @JvmOverloads
    fun execute(
        view: View,
        duration: Long = 4000,
        interpolator: TimeInterpolator = AccelerateInterpolator(0.5f),
        needDisappear: Boolean = true
    ) {
        val rect = Rect()
        view.getGlobalVisibleRect(rect)
        val location = IntArray(2)
        getLocationOnScreen(location)
        rect.offset(0, -location[1])
        val bitmap = createBitmapFromView(view) ?: return
        if (needDisappear) {
            view.alpha = 0f
        }
        DisappearAnimator(this, bitmap, rect).run {
            this.duration = duration
            this.interpolator = interpolator
            setFloatValues(0f, 1F)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    mDisappearAnimators.remove(animation)
                    this@run.recycle()
                }
            })
            mDisappearAnimators.add(this)
            start()
        }
    }

    private val sCanvas = Canvas()
    private fun createBitmapFromView(view: View): Bitmap? {
        view.clearFocus()
        val bitmap = createBitmapSafely(
            view.width,
            view.height, Bitmap.Config.ARGB_8888, 1
        )
        if (bitmap != null) {
            synchronized(sCanvas) {
                val canvas = sCanvas
                canvas.setBitmap(bitmap)
                view.draw(canvas)
                canvas.setBitmap(null)
            }
        }
        return bitmap
    }

    private fun createBitmapSafely(width: Int, height: Int, config: Bitmap.Config, retryCount: Int): Bitmap? {
        try {
            return Bitmap.createBitmap(width, height, config)
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
            if (retryCount > 0) {
                System.gc()
                return createBitmapSafely(width, height, config, retryCount - 1)
            }
            return null
        }

    }

}