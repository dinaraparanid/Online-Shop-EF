package com.paranid5.emonlineshop.presentation.ui

import androidx.databinding.BindingAdapter
import com.willy.ratingbar.BaseRatingBar

@BindingAdapter("app:rating")
fun setRating(ratingBar: BaseRatingBar, rating: Double) {
    ratingBar.rating = rating.toFloat()
}