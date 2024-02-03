package com.paranid5.emonlineshop.domain.product

enum class ProductOrder {
    POPULARITY,
    PRICE_ASC,
    PRICE_DESC
}

infix fun List<ProductWithLike>.sortedBy(trackOrder: ProductOrder): List<ProductWithLike> =
    when (trackOrder) {
        ProductOrder.POPULARITY -> sortedByDescending { it.product.feedback.rating }
        ProductOrder.PRICE_ASC -> sortedBy { it.product.price.priceWithDiscount }
        ProductOrder.PRICE_DESC -> sortedByDescending { it.product.price.priceWithDiscount }
    }