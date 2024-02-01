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
    val coversRes: List<Int>
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
    return coversRes == other.coversRes
}

inline val IProduct.originalPriceText: String
    get() = "${price.price} ${price.unit}"

inline val IProduct.priceWithDiscountText: String
    get() = "${price.priceWithDiscount} ${price.unit}"

inline val IProduct.discountText: String
    get() = "-${price.discount}%"

inline val IProduct.ratingText: String
    get() = String.format("%.1f", feedback.rating)

inline val IProduct.feedbackCountText: String
    get() = "(${feedback.count})"