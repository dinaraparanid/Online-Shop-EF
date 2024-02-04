package com.paranid5.emonlineshop.presentation.main.fragments.products

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

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

    val coversUrls: List<String>
        get() = product.coversUrls

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

    private var areIngredientsShown = View.VISIBLE
        set(value) {
            field = value
            notifyPropertyChanged(BR.ingredientsShownText)
            notifyPropertyChanged(BR.ingredientsLines)
            notifyPropertyChanged(BR.ingredientsShownVisibility)
        }

    fun onIngredientsShownClicked() {
        areIngredientsShown = when (areIngredientsShown) {
            View.VISIBLE -> View.INVISIBLE
            View.INVISIBLE -> View.VISIBLE
            else -> View.GONE
        }
    }

    val ingredientsShownText: Int
        @Bindable
        get() = when (areIngredientsShown) {
            View.VISIBLE -> R.string.hide
            else -> R.string.show
        }

    val ingredientsLines: Int
        @Bindable
        get() = when (areIngredientsShown) {
            View.VISIBLE -> Int.MAX_VALUE
            else -> 2
        }

    val ingredientsShownVisibility: Int
        @Bindable
        get() = when (areIngredientsShown) {
            View.GONE -> View.GONE
            else -> View.VISIBLE
        }

    fun setIngredientsShownVisibility(lines: Int) {
        areIngredientsShown = when {
            lines <= 2 -> View.GONE
            else -> View.INVISIBLE
        }
    }

    fun likeImage(isLiked: Boolean): Int = when {
        isLiked -> R.drawable.heart_liked
        else -> R.drawable.heart
    }

    suspend fun onLikeClicked(isLiked: Boolean): Unit =
        when {
            isLiked -> removeFavourite(product.id)
            else -> addFavourite(product)
        }
}