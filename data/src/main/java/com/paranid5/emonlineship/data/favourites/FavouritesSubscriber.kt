package com.paranid5.emonlineship.data.favourites

import com.paranid5.emonlineshop.domain.product.FavouriteProduct
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FavouritesSubscriber {
    val favouritesFlow: Flow<List<FavouriteProduct>>
}

class FavouritesSubscriberImpl @Inject constructor(
    private val favouritesDataSource: FavouritesDataSource
) : FavouritesSubscriber {
    override val favouritesFlow: Flow<List<FavouriteProduct>>
        get() = favouritesDataSource.favouritesFlow
}