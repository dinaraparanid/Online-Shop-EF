package com.paranid5.emonlineshop.presentation.utils

import android.os.Build
import android.os.Bundle

@Suppress("DEPRECATION")
fun <T> Bundle.getParcelableCompat(key: String, clazz: Class<T>): T =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        getParcelable(key, clazz)!!
    else
        getParcelable(key)!!