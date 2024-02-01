package com.paranid5.emonlineshop.domain.favourites

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Feedback(val count: Long, val rating: Double) : Parcelable
