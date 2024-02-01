package com.paranid5.emonlineship.data.favourites

import com.paranid5.emonlineshop.domain.product.FavouriteProduct
import javax.inject.Inject

interface FavouritesPublisher {
    suspend fun addFavourite(favouriteProduct: FavouriteProduct)

    suspend fun removeFavourite(favouriteProduct: FavouriteProduct)
}

class FavouritesPublisherImpl @Inject constructor(
    private val favouritesDataSource: FavouritesDataSource
) : FavouritesPublisher {
    override suspend fun addFavourite(favouriteProduct: FavouriteProduct): Unit =
        favouritesDataSource.addFavouriteAsync(favouriteProduct).join()

    override suspend fun removeFavourite(favouriteProduct: FavouriteProduct): Unit =
        favouritesDataSource.removeFavouriteAsync(favouriteProduct).join()
}