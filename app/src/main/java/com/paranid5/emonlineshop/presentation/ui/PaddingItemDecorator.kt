package com.paranid5.emonlineshop.presentation.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun PaddingItemDecorator(
    topPadding: Int = 0,
    bottomPadding: Int = 0,
    leftPadding: Int = 0,
    rightPadding: Int = 0
): RecyclerView.ItemDecoration =
    object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)

            outRect.left = leftPadding
            outRect.right = rightPadding

            if (parent.getChildLayoutPosition(view) > 0)
                outRect.top = topPadding

            if (parent.getChildLayoutPosition(view) < parent.childCount - 1)
                outRect.bottom = bottomPadding
        }
    }

fun PaddingItemDecorator(verticalPadding: Int = 0, horizontalPadding: Int = 0): RecyclerView.ItemDecoration =
    PaddingItemDecorator(
        topPadding = verticalPadding,
        bottomPadding = verticalPadding,
        leftPadding = horizontalPadding,
        rightPadding = horizontalPadding
    )

fun PaddingItemDecorator(padding: Int = 0): RecyclerView.ItemDecoration =
    PaddingItemDecorator(verticalPadding = padding, horizontalPadding = padding)