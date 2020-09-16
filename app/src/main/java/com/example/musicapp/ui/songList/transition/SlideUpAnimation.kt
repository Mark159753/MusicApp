package com.example.musicapp.ui.songList.transition

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Point
import android.view.View
import android.view.WindowManager
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

class SlideUpAnimation(context: Context) {

    private val windowHeight by lazy {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val size = Point()
        wm.defaultDisplay.getSize(size)
        size.y
    }

    private val animSet:MutableList<ObjectAnimator> = ArrayList()

    fun addAnimView(view:View, duration:Long = 300L, delay:Long = 0L){
        val position = view.y
        view.y = windowHeight.toFloat()
        val anim = ObjectAnimator.ofFloat(view, View.Y, position).apply {
            this.duration = duration
            this.startDelay = delay
            interpolator = FastOutSlowInInterpolator()
        }
        animSet.add(anim)
    }


    fun start(){
        animSet.forEach {anim ->
            anim.start()
        }
    }
}