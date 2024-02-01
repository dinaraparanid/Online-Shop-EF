package com.paranid5.emonlineshop.domain.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Info(val title: String, val value: String) : Parcelable
