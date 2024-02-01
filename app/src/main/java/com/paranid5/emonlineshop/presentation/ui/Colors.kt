package com.paranid5.emonlineshop.presentation.ui

import android.content.Context
import android.os.Build
import androidx.annotation.ColorRes

fun Context.getColorCompat(@ColorRes res: Int) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        resources.getColor(res, null)
    else
        resources.getColor(res)