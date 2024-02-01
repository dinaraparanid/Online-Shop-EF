package com.paranid5.emonlineshop.presentation.main.fragments.catalog

import androidx.lifecycle.ViewModel
import com.paranid5.emonlineship.data.ktor.fetchProducts
import com.paranid5.emonlineshop.domain.product.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val ktorClient: HttpClient
) : ViewModel() {
    suspend fun fetchProducts(): List<Product> =
        ktorClient.fetchProducts()
}