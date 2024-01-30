package com.paranid5.emonlineshop.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class User(
    val name: String,
    val family: String,
    val phone: String
) : Parcelable
