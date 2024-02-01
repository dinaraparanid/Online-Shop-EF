package com.paranid5.emonlineshop.domain.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ProductList(val items: List<Product>) : Parcelable