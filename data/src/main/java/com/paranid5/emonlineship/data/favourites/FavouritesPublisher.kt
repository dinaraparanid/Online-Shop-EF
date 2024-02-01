package com.paranid5.emonlineship.data.favourites

import com.paranid5.emonlineshop.domain.favourites.Favourite
import javax.inject.Inject

interface FavouritesPublisher {
    suspend fun addFavourite(favourite: Favourite)

    suspend fun removeFavourite(favourite: Favourite)
}

class FavouritesPublisherImpl @Inject constructor(
    private val favouritesDataSource: FavouritesDataSource
) : FavouritesPublisher {
    override suspend fun addFavourite(favourite: Favourite): Unit =
        favouritesDataSource.addFavouriteAsync(favourite).join()

    override suspend fun removeFavourite(favourite: Favourite): Unit =
        favouritesDataSource.removeFavouriteAsync(favourite).join()
}