package com.paranid5.emonlineshop.domain.product.tag

import android.content.Context
import com.paranid5.domain.R
import com.paranid5.emonlineshop.domain.product.IProduct
import java.util.Locale

fun List<IProduct>.retrieveTags(): List<String> =
    asSequence()
        .flatMap(IProduct::tags)
        .distinct()
        .toList()

fun Context.translateTag(tag: String) = when (tag) {
    "body" -> getString(R.string.body_tag)
    "face" -> getString(R.string.face_tag)
    "mask" -> getString(R.string.mask_tag)
    "suntan" -> getString(R.string.suntan_tag)
    else -> tag.capitalized
}

inline val String.capitalized: String
    get() = replaceFirstChar {
        when {
            it.isLowerCase() -> it.titlecase(Locale.getDefault())
            else -> it.toString()
        }
    }