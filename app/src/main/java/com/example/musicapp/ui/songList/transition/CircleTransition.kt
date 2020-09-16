package com.example.musicapp.ui.songList.transition

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.transition.Transition
import androidx.transition.TransitionValues
import com.example.musicapp.extention.findCenterPositionOnScreen
import kotlin.math.min
import kotlin.math.sqrt

class CircleTransition:Transition() {

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        if (startValues == null || endValues == null)
            return null
        val cx = startValues.values[PROPNAME_CENTER_X] as Int
        val cy = startValues.values[PROPNAME_CENTER_Y] as Int
        val startWith = startValues.values[PROPNAME_WIDTH] as Int
        val startHeight = startValues.values[PROPNAME_HEIGHT] as Int

        val endWith = endValues.values[PROPNAME_WIDTH] as Int
        val endHeight = endValues.values[PROPNAME_HEIGHT] as Int

        val startRadius = min(startWith, startHeight).toFloat() / 2
        val endRadius = sqrt((endWith * endWith + endHeight * endHeight).toDouble()).toFloat()

        val anim = ViewAnimationUtils.createCircularReveal(endValues.view, cx, cy, startRadius, endRadius).apply {
            duration = 500
            interpolator = FastOutSlowInInterpolator()
        }
        return anim
    }

    private fun captureValues(transitionValues:TransitionValues){
        val view = transitionValues.view
        val location = view.findCenterPositionOnScreen()

        transitionValues.values.apply {
            put(PROPNAME_CENTER_X, location.first)
            put(PROPNAME_CENTER_Y, location.second)
            put(PROPNAME_WIDTH, view.width)
            put(PROPNAME_HEIGHT, view.height)
        }
    }

    companion object{
        private const val PROPNAME_CENTER_X = "com.example.musicapp.ui.songList.transition:CircleTransition:CENTER_X"
        private const val PROPNAME_CENTER_Y = "com.example.musicapp.ui.songList.transition:CircleTransition:CENTER_Y"
        private const val PROPNAME_WIDTH = "com.example.musicapp.ui.songList.transition:CircleTransition:WIDTH"
        private const val PROPNAME_HEIGHT = "com.example.musicapp.ui.songList.transition:CircleTransition:HEIGHT"
    }
}
