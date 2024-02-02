package com.paranid5.emonlineshop.presentation.main.fragments.catalog

import androidx.lifecycle.ViewModel
import com.paranid5.emonlineship.data.favourites.FavouritesDataSource
import com.paranid5.emonlineship.data.favourites.FavouritesPublisher
import com.paranid5.emonlineship.data.favourites.FavouritesPublisherImpl
import com.paranid5.emonlineship.data.favourites.FavouritesSubscriber
import com.paranid5.emonlineship.data.favourites.FavouritesSubscriberImpl
import com.paranid5.emonlineship.data.ktor.fetchProducts
import com.paranid5.emonlineshop.domain.product.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val ktorClient: HttpClient,
    private val favouritesDataSource: FavouritesDataSource
) : ViewModel(),
    FavouritesSubscriber by FavouritesSubscriberImpl(favouritesDataSource),
    FavouritesPublisher by FavouritesPublisherImpl(favouritesDataSource) {
    private val _productsState by lazy {
        MutableStateFlow(listOf<Product>())
    }

    val productsState: StateFlow<List<Product>> by lazy {
        _productsState.asStateFlow()
    }

    suspend fun fetchProducts(): Unit =
        _productsState.update { ktorClient.fetchProducts() }
}