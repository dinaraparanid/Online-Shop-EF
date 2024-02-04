package com.paranid5.emonlineshop.domain.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Feedback(val count: Long = 0, val rating: Double = 0.0) : Parcelable
