package com.paranid5.emonlineshop.presentation.ui

import android.app.Activity
import android.os.Build
import androidx.annotation.ColorRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

inline fun LifecycleOwner.launchOnStarted(
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            block()
        }
    }
}

@Suppress("DEPRECATION")
fun Activity.getColorCompat(@ColorRes color: Int) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        resources.getColor(color, null)
    else
        resources.getColor(color)