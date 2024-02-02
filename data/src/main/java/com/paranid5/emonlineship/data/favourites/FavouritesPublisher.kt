package com.paranid5.emonlineship.data.favourites

import com.paranid5.emonlineshop.domain.product.IProduct
import javax.inject.Inject

interface FavouritesPublisher {
    suspend fun addFavourite(product: IProduct)

    suspend fun removeFavourite(productId: String)
}

class FavouritesPublisherImpl @Inject constructor(
    private val favouritesDataSource: FavouritesDataSource
) : FavouritesPublisher {
    override suspend fun addFavourite(product: IProduct): Unit =
        favouritesDataSource.addFavouriteAsync(product).join()

    override suspend fun removeFavourite(productId: String): Unit =
        favouritesDataSource.removeFavouriteAsync(productId).join()
}