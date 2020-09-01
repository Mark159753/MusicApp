package com.example.musicapp.ui.home.adapters.vp

import android.content.Context
import android.view.View
import androidx.viewpager.widget.ViewPager

class CarouselEffectTransformer(context: Context) : ViewPager.PageTransformer {
    private val maxTranslateOffsetX: Int
    private var viewPager: ViewPager? = null

    override fun transformPage(view: View, position: Float) {
        if (viewPager == null) {
            viewPager = view.parent as ViewPager?
        }
        val leftInScreen: Int = view.left - viewPager!!.scrollX

        val centerXInViewPager: Int = ((leftInScreen + view.measuredWidth / 2)*1.4).toInt() // for the future remove *1.4f

        val offsetX = centerXInViewPager - viewPager!!.measuredWidth / 2
        val offsetRate =
            offsetX.toFloat() * 0.21f / viewPager!!.measuredWidth
        val scaleFactor = 1 - Math.abs(offsetRate)
        if (scaleFactor > 0) {
            view.scaleX = scaleFactor
            view.scaleY = scaleFactor
            //view.translationX = -maxTranslateOffsetX * offsetRate
            //ViewCompat.setElevation(view, 0.0f);
        }
        //ViewCompat.setElevation(view, scaleFactor)
    }

    /**
     * Dp to pixel conversion
     */
    private fun dp2px(context: Context, dipValue: Float): Int {
        val m: Float = context.resources.displayMetrics.density
        return (dipValue * m + 0.5f).toInt()
    }

    init {
        maxTranslateOffsetX = dp2px(context, 180f)
    }
}