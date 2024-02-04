package com.paranid5.emonlineshop.presentation.main.fragments.catalog

import android.content.Context
import androidx.lifecycle.ViewModel
import com.paranid5.domain.R
import com.paranid5.emonlineship.data.favourites.FavouritesDataSource
import com.paranid5.emonlineship.data.favourites.FavouritesPublisher
import com.paranid5.emonlineship.data.favourites.FavouritesPublisherImpl
import com.paranid5.emonlineship.data.favourites.FavouritesSubscriber
import com.paranid5.emonlineship.data.favourites.FavouritesSubscriberImpl
import com.paranid5.emonlineship.data.ktor.fetchProducts
import com.paranid5.emonlineshop.domain.product.IProduct
import com.paranid5.emonlineshop.domain.product.Product
import com.paranid5.emonlineshop.domain.product.ProductOrder
import com.paranid5.emonlineshop.domain.product.ProductWithLike
import com.paranid5.emonlineshop.domain.product.sortedBy
import com.paranid5.emonlineshop.domain.product.tag.TagWithSelect
import com.paranid5.emonlineshop.domain.product.tag.retrieveTags
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
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

    private val _productOrderState by lazy {
        MutableStateFlow(ProductOrder.POPULARITY)
    }

    private val _tagsState by lazy {
        MutableStateFlow(listOf<String>())
    }

    private val selectedTagsState by lazy {
        MutableStateFlow(listOf<String>())
    }

    private val selectAllTagState by lazy {
        MutableStateFlow("")
    }

    private inline val selectAllTag
        get() = selectAllTagState.value

    val tagsState: Flow<List<TagWithSelect>> by lazy {
        combine(_tagsState, selectedTagsState) { tags, selectedTags ->
            val selectHash = selectedTags.toHashSet()
            val (select, noSelect) = tags.partition { it in selectHash }

            val selected = select.map { TagWithSelect(tag = it, isSelected = true) }
            val notSelected = noSelect.map { TagWithSelect(tag = it, isSelected = false) }

            selected + notSelected
        }
    }

    val productsFlow: Flow<List<ProductWithLike>> by lazy {
        combine(
            _productsState,
            favouritesFlow,
            _productOrderState,
            selectedTagsState,
            selectAllTagState
        ) { products, favourites, order, selectedTags, selectAll ->
            withContext(Dispatchers.Default) {
                val favIds = favourites.map(IProduct::id).toHashSet()

                val prods = when (selectAll) {
                    in selectedTags -> products.asSequence()

                    else -> products
                        .asSequence()
                        .filter { p -> selectedTags.all { it in p.tags } }
                }

                prods
                    .map { ProductWithLike(it, isLiked = it.id in favIds) }
                    .toList()
                    .sortedBy(order)
            }
        }
    }

    suspend fun fetchProductsAndTags(context: Context) {
        fun allNonSelectedTags(products: List<Product>) =
            products.retrieveTags().map { it }

        val selectAllTag = context.getString(R.string.show_all_tag)
        val products = ktorClient.fetchProducts()
        val tags = listOf(selectAllTag) + allNonSelectedTags(products)

        selectAllTagState.update { selectAllTag }
        _tagsState.update { tags }
        _productsState.update { products }
        selectedTagsState.update { listOf(selectAllTag) }
    }

    infix fun setProductOrder(order: ProductOrder): Unit =
        _productOrderState.update { order }

    fun selectTag(tag: String): Unit =
        selectedTagsState.update { listOf(tag) }

    fun unselectTag(tag: String): Unit =
        selectedTagsState.update { listOf(selectAllTag) }
}
