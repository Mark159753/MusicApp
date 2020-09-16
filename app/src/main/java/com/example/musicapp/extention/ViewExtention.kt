package com.example.musicapp.extention

import android.view.View

fun View.findCenterPositionOnScreen():Pair<Int, Int>{
    val location = IntArray(2)
    this.getLocationOnScreen(location)
    val cx = location[0] + this.width /2
    val cy = location[1] + this.height /2
    return Pair(cx, cy)
}