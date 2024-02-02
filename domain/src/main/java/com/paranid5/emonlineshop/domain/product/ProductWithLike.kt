package com.paranid5.emonlineshop.domain.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ProductWithLike(
    val product: IProduct,
    val isLiked: Boolean
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductWithLike

        if (isLiked != other.isLiked) return false
        return product equalTo  other.product
    }

    override fun hashCode(): Int {
        var result = product.hashCode()
        result = 31 * result + isLiked.hashCode()
        return result
    }
}
