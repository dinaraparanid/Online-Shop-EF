package com.paranid5.emonlineship.data.favourites

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.paranid5.emonlineshop.data.Favourites
import com.paranid5.emonlineshop.data.FavouritesQueries
import com.paranid5.emonlineshop.domain.product.FavouriteProduct
import com.paranid5.emonlineshop.domain.product.Feedback
import com.paranid5.emonlineshop.domain.product.Info
import com.paranid5.emonlineshop.domain.product.Price
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouritesDataSource @Inject constructor(driver: SqlDriver) :
    CoroutineScope by CoroutineScope(Dispatchers.IO) {
    private val database by lazy {
        Favourites(driver)
    }

    private val queries by lazy {
        database.favouritesQueries
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val favouritesFlow
        get() = queries
            .selectAndMapToFavourite()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .mapLatest { favourites ->
                favourites
                    .groupBy(FavouriteProduct::id)
                    .mapNotNull { (_, same) ->
                        same.firstOrNull()?.copy(
                            tags = same.flatMap(FavouriteProduct::tags),
                            info = same.flatMap(FavouriteProduct::info)
                        )
                    }
            }

    fun addFavouriteAsync(favouriteProduct: FavouriteProduct): Job =
        launch(Dispatchers.IO) { addFavourite(favouriteProduct) }

    private fun addFavourite(favouriteProduct: FavouriteProduct) = queries.transaction {
        val priceId = queries.insertPriceIfNotExists(favouriteProduct.price)
        val feedbackId = queries.insertFeedbackIfNotExists(favouriteProduct.feedback)
        val infoIds = queries.insertInfo(favouriteProduct.info)
        queries.insertTags(favouriteProduct.tags)

        queries.insertFavourite(favouriteProduct, priceId, feedbackId)
        infoIds.forEach { queries.insertFavouriteInfo(favouriteProduct.id, it) }
        favouriteProduct.tags.forEach { queries.insertFavouriteTag(favouriteProduct.id, it) }
    }

    fun removeFavouriteAsync(favouriteProduct: FavouriteProduct): Job =
        launch(Dispatchers.IO) { removeFavourite(favouriteProduct) }

    private fun removeFavourite(favouriteProduct: FavouriteProduct) =
        queries.removeFavourite(favouriteProduct.id)
}

internal fun FavouritesQueries.selectAndMapToFavourite() =
    selectFavourites { id, title, subtitle, price, discount, priceWithDiscount, unit, count, rating, tag, available, description, infoTitle, infoValue, ingredients ->
        FavouriteProduct(
            id = id,
            title = title,
            subtitle = subtitle,
            price = Price(
                price = price,
                discount = discount,
                priceWithDiscount = priceWithDiscount,
                unit = unit
            ),
            feedback = Feedback(
                count = count,
                rating = rating
            ),
            tags = listOf(tag),
            available = available,
            description = description,
            info = listOf(Info(infoTitle, infoValue)),
            ingredients = ingredients
        )
    }

internal fun FavouritesQueries.insertPriceIfNotExists(price: Price): Long {
    insertPriceIfNotExists(
        price = price.price,
        discount = price.discount,
        priceWithDiscount = price.priceWithDiscount,
        unit = price.unit
    )

    return insertedPriceId().executeAsOne()
}

internal fun FavouritesQueries.insertFeedbackIfNotExists(feedback: Feedback): Long {
    insertFeedbackIfNotExists(
        count = feedback.count,
        rating = feedback.rating
    )

    return insertedFeedbackId().executeAsOne()
}

internal fun FavouritesQueries.insertTags(tags: List<String>) =
    tags.forEach { insertTagIfNotExists(it) }

internal fun FavouritesQueries.insertInfo(info: List<Info>) =
    info.map {
        insertInfoIfNotExists(it.title, it.value)
        insertedInfoId().executeAsOne()
    }

internal fun FavouritesQueries.insertFavourite(
    favouriteProduct: FavouriteProduct,
    priceId: Long,
    feedbackId: Long,
) = insertFavourite(
    title = favouriteProduct.title,
    favouriteProduct.subtitle,
    priceId = priceId,
    feedbackId = feedbackId,
    available = favouriteProduct.available,
    description = favouriteProduct.description,
    ingredients = favouriteProduct.ingredients
)
