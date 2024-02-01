package com.paranid5.emonlineshop.presentation.ui

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat

fun Context.getColorCompat(@ColorRes res: Int): Int =
    ResourcesCompat.getColor(resources, res, null)

fun Context.getDrawableCompat(@DrawableRes res: Int): Drawable? =
    ResourcesCompat.getDrawable(resources, res, null)