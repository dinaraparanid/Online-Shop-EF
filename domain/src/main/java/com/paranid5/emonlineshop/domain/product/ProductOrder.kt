package com.paranid5.emonlineshop.domain.product

enum class ProductOrder {
    POPULARITY,
    PRICE_ASC,
    PRICE_DESC
}

infix fun List<Product>.sortedBy(trackOrder: ProductOrder): List<Product> =
    when (trackOrder) {
        ProductOrder.POPULARITY -> sortedByDescending { it.feedback.rating }
        ProductOrder.PRICE_ASC -> sortedBy { it.price.priceWithDiscount }
        ProductOrder.PRICE_DESC -> sortedByDescending { it.price.priceWithDiscount }
    }