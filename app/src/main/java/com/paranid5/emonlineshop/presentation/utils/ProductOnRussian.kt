package com.paranid5.emonlineshop.presentation.utils

import androidx.annotation.StringRes
import com.paranid5.emonlineshop.R

@StringRes
fun productOnRussianRes(numberOfProducts: Int) =
    when (numberOfProducts % 10) {
        1 -> R.string.product_rus_1
        in 2..4 -> R.string.product_rus_2_4
        else -> R.string.product_rus_5_0
    }