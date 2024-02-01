package com.paranid5.emonlineshop.presentation.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.paranid5.emonlineshop.R

class ObliqueStrikeTextView : AppCompatTextView {
    private var dividerColor = 0
    private lateinit var paint: Paint

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) :
            super(context, attrs, defStyle) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) =
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ObliqueStrikeTextView,
            0, 0
        ).run {
            dividerColor = getColor(
                R.styleable.ObliqueStrikeTextView_strike_color,
                context.getColorCompat(R.color.grey)
            )

            paint = Paint().apply {
                color = dividerColor
                strokeWidth = resources.getDimension(R.dimen.oblique_strike_stroke)
            }
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawLine(0F - width.toFloat() * 1.2F, height.toFloat(), width.toFloat() * 1.2F, height.toFloat() * 11 / 32, paint)
    }
}