package com.paranid5.emonlineshop.domain.product.tag

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class TagWithSelect(
    val tag: String,
    val isSelected: Boolean
) : Parcelable