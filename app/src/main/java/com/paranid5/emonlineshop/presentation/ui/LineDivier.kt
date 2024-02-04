package com.paranid5.emonlineshop.presentation.ui

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.paranid5.emonlineshop.R

fun LineDivider(context: Context): DividerItemDecoration =
    DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
        setDrawable(ContextCompat.getDrawable(context, R.drawable.divider)!!)
    }