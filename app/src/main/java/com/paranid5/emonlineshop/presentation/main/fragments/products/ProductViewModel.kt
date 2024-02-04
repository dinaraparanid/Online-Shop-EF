package com.paranid5.emonlineshop.presentation.main.fragments.products

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.lifecycleScope
import com.paranid5.emonlineship.data.favourites.FavouritesDataSource
import com.paranid5.emonlineship.data.favourites.FavouritesPublisher
import com.paranid5.emonlineship.data.favourites.FavouritesPublisherImpl
import com.paranid5.emonlineship.data.favourites.FavouritesSubscriber
import com.paranid5.emonlineship.data.favourites.FavouritesSubscriberImpl
import com.paranid5.emonlineshop.BR
import com.paranid5.emonlineshop.R
import com.paranid5.emonlineshop.domain.product.IProduct
import com.paranid5.emonlineshop.domain.product.Info
import com.paranid5.emonlineshop.domain.product.discountText
import com.paranid5.emonlineshop.domain.product.originalPriceText
import com.paranid5.emonlineshop.domain.product.priceWithDiscountText
import com.paranid5.emonlineshop.domain.product.ratingText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

class ProductViewModel(
    private val product: IProduct,
    private val favouritesDataSource: FavouritesDataSource
) : BaseObservable(),
    FavouritesSubscriber by FavouritesSubscriberImpl(favouritesDataSource),
    FavouritesPublisher by FavouritesPublisherImpl(favouritesDataSource) {
    @OptIn(ExperimentalCoroutinesApi::class)
    val likeFlow: Flow<Boolean> by lazy {
        favouritesFlow.mapLatest { favs ->
            favs.any { it.id == product.id }
        }
    }

    val coversRes: List<Int>
        get() = product.coversRes

    val brand: String
        get() = product.title

    val subtitle: String
        get() = product.subtitle

    val available: Long
        get() = product.available

    val rating: Double
        get() = product.feedback.rating

    val ratingText: String
        get() = product.ratingText

    val feedbackCount: Long
        get() = product.feedback.count

    val finalPriceText: String
        get() = product.priceWithDiscountText

    val originalPriceText: String
        get() = product.originalPriceText

    val discountText: String
        get() = product.discountText

    val description: String
        get() = product.description

    val ingredients: String
        get() = product.ingredients

    val info: List<Info>
        get() = product.info

    private var isDescriptionShown = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.descriptionShownText)
            notifyPropertyChanged(BR.descriptionVisibility)
        }

    fun onDescriptionShownClicked() {
        isDescriptionShown = !isDescriptionShown
    }

    val descriptionShownText: Int
        @Bindable
        get() = when {
            isDescriptionShown -> R.string.hide
            else -> R.string.show
        }

    val descriptionVisibility: Int
        @Bindable
        get() = when {
            isDescriptionShown -> View.VISIBLE
            else -> View.GONE
        }

    private var areIngredientsShown = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.ingredientsShownText)
            notifyPropertyChanged(BR.ingredientsLines)
        }

    fun onIngredientsShownClicked() {
        areIngredientsShown = !areIngredientsShown
    }

    val ingredientsShownText: Int
        @Bindable
        get() = when {
            areIngredientsShown -> R.string.hide
            else -> R.string.show
        }

    val ingredientsLines: Int
        @Bindable
        get() = when {
            areIngredientsShown -> Int.MAX_VALUE
            else -> 2
        }

    fun likeImage(isLiked: Boolean) = when {
        isLiked -> R.drawable.heart_liked
        else -> R.drawable.heart
    }

    suspend fun onLikeClicked(isLiked: Boolean) =
        when {
            isLiked -> removeFavourite(product.id)
            else -> addFavourite(product)
        }
}