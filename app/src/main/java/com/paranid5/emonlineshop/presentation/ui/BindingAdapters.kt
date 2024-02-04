package com.paranid5.emonlineshop.presentation.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.willy.ratingbar.BaseRatingBar


@BindingAdapter("app:rating")
fun setRating(ratingBar: BaseRatingBar, rating: Double) {
    ratingBar.rating = rating.toFloat()
}

@BindingAdapter("app:animateLines")
fun animateTextViewMaxLinesChange(textView: TextView, lines: Int) {
    val startHeight = textView.measuredHeight
    textView.maxLines = lines

    textView.measure(
        View.MeasureSpec.makeMeasureSpec(
            textView.width, View.MeasureSpec.EXACTLY
        ),
        View.MeasureSpec.makeMeasureSpec(
            0,
            View.MeasureSpec.UNSPECIFIED
        )
    )

    val endHeight = textView.measuredHeight

    val animation = ObjectAnimator.ofInt(
        textView, "maxHeight", startHeight, endHeight
    )

    animation.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            if (textView.maxHeight == endHeight) {
                textView.maxLines = lines
            }
        }
    })

    animation.setDuration(300).start()
}