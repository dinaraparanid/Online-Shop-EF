package com.paranid5.emonlineshop.presentation.login

import com.paranid5.emonlineshop.R

val LoginAndroidViewModel.nameBoxColor
    get() = boxColor(hasNameError)

val LoginAndroidViewModel.familyBoxColor
    get() = boxColor(hasFamilyError)

val LoginAndroidViewModel.phoneBoxColor
    get() = boxColor(hasPhoneError)

private fun boxColor(hasError: Boolean) = when {
    hasError -> android.R.color.holo_red_dark
    else -> R.color.light_gray
}