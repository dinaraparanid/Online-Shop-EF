package com.paranid5.emonlineshop.presentation.ui

import androidx.databinding.BindingAdapter
import com.willy.ratingbar.ScaleRatingBar

@BindingAdapter("app:rating")
fun setRating(ratingBar: ScaleRatingBar, rating: Double) {
    ratingBar.rating = rating.toFloat()
}