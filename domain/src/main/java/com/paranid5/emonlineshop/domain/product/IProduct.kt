package com.paranid5.emonlineshop.domain.product

import android.os.Parcelable

interface IProduct : Parcelable {
    val id: String
    val title: String
    val subtitle: String
    val price: Price
    val feedback: Feedback
    val tags: List<String>
    val available: Long
    val description: String
    val info: List<Info>
    val ingredients: String
    val coversPaths: List<String>
}

infix fun IProduct.equalTo(other: IProduct): Boolean {
    if (this === other) return true
    if (javaClass != other.javaClass) return false
    if (id != other.id) return false
    if (title != other.title) return false
    if (subtitle != other.subtitle) return false
    if (price != other.price) return false
    if (feedback != other.feedback) return false
    if (tags != other.tags) return false
    if (available != other.available) return false
    if (description != other.description) return false
    if (info != other.info) return false
    if (ingredients != other.ingredients) return false
    return coversPaths == other.coversPaths
}

inline val IProduct.ratingText
    get() = String.format("%.1lf", feedback.rating)

inline val IProduct.feedbackCountText
    get() = "(${feedback.count})"