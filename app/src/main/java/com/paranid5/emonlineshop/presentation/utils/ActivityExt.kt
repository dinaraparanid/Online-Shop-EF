package com.paranid5.emonlineshop.presentation.utils

import android.app.Activity
import androidx.annotation.IdRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

fun Activity.applyInsets(@IdRes mainLayoutRes: Int) =
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(mainLayoutRes)) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
    }