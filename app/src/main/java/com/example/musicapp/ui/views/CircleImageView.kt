package com.example.musicapp.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.example.musicapp.R
import de.hdodenhof.circleimageview.CircleImageView

class CircleImageView(context: Context, attributeSet: AttributeSet? = null):
    CircleImageView(context, attributeSet) {

    var innerCircleRadius:Float = 0f
        set(value) {
            field = value
            invalidate()
        }
    private val paint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
    }

    init {
        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.CircleImageView,
            0, 0
        ).apply {
            try {
                innerCircleRadius = getDimension(R.styleable.CircleImageView_innerCircleRadius, 15f)
            }finally {
                recycle()
            }
        }

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val x = width / 2f
        val y = height / 2f
        canvas?.drawCircle(x, y, innerCircleRadius, paint)
    }

}