package com.example.musicapp.ui.player.animation

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.LinearInterpolator
import java.time.Duration

class RotationAnimation(
    view: View
) {

    private var _isRotating:Boolean = false
    private val animator:ObjectAnimator = ObjectAnimator.ofFloat(view, View.ROTATION, 0f, 360f)

    fun start(infinity:Boolean = true, duration: Long = 300L){
        if (!_isRotating){
            animator.apply {
                this.duration = duration
                interpolator = LinearInterpolator()
                if (infinity) {
                    repeatCount = ObjectAnimator.INFINITE
                    repeatMode = ObjectAnimator.RESTART
                }
                start()
            }
            _isRotating = true
        }
    }

    fun stop(){
        if (_isRotating) {
            animator.cancel()
            _isRotating = false
        }
    }
}