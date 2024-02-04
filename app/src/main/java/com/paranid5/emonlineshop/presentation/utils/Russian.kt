package com.paranid5.emonlineshop.presentation.utils

import androidx.annotation.StringRes
import com.paranid5.emonlineshop.R

@StringRes
fun productOnRussianRes(numberOfProducts: Int): Int =
    when (numberOfProducts % 10) {
        1 -> R.string.product_rus_1
        in 2..4 -> R.string.product_rus_2_4
        else -> R.string.product_rus_5_0
    }

@StringRes
fun availableOnRussianRes(available: Long): Int =
    when (available % 10) {
        1L -> R.string.available_rus_1
        in 2..4 -> R.string.available_rus_2_4
        else -> R.string.available_rus_5_0
    }

@StringRes
fun feedbackOnRussianRes(feedback: Long): Int =
    when (feedback % 10) {
        1L -> R.string.feedback_rus_1
        in 2..4 -> R.string.feedback_rus_2_4
        else -> R.string.feedback_rus_5_0
    }