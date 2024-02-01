package com.paranid5.emonlineshop.domain.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Price(
    val price: String,
    val discount: Long,
    val priceWithDiscount: String,
    val unit: String
) : Parcelable