package com.paranid5.emonlineshop.domain.favourites

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Favourite(
    val id: String,
    val title: String,
    val subtitle: String,
    val price: Price,
    val feedback: Feedback,
    val tags: List<String>,
    val available: Long,
    val description: String,
    val info: List<Info>,
    val ingredients: String
) : Parcelable
