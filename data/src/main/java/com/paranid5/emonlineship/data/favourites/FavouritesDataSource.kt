package com.paranid5.emonlineship.data.favourites

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.paranid5.emonlineshop.data.Favourites
import com.paranid5.emonlineshop.data.FavouritesQueries
import com.paranid5.emonlineshop.domain.favourites.Favourite
import com.paranid5.emonlineshop.domain.favourites.Feedback
import com.paranid5.emonlineshop.domain.favourites.Info
import com.paranid5.emonlineshop.domain.favourites.Price
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
                    .groupBy(Favourite::id)
                    .mapNotNull { (_, same) ->
                        same.firstOrNull()?.copy(
                            tags = same.flatMap(Favourite::tags),
                            info = same.flatMap(Favourite::info)
                        )
                    }
            }

    fun addFavouriteAsync(favourite: Favourite): Job =
        launch(Dispatchers.IO) { addFavourite(favourite) }

    private fun addFavourite(favourite: Favourite) = queries.transaction {
        val priceId = queries.insertPriceIfNotExists(favourite.price)
        val feedbackId = queries.insertFeedbackIfNotExists(favourite.feedback)
        val infoIds = queries.insertInfo(favourite.info)
        queries.insertTags(favourite.tags)

        queries.insertFavourite(favourite, priceId, feedbackId)
        infoIds.forEach { queries.insertFavouriteInfo(favourite.id, it) }
        favourite.tags.forEach { queries.insertFavouriteTag(favourite.id, it) }
    }

    fun removeFavouriteAsync(favourite: Favourite): Job =
        launch(Dispatchers.IO) { removeFavourite(favourite) }

    private fun removeFavourite(favourite: Favourite) =
        queries.removeFavourite(favourite.id)
}

internal fun FavouritesQueries.selectAndMapToFavourite() =
    selectFavourites { id, title, subtitle, price, discount, priceWithDiscount, unit, count, rating, tag, available, description, infoTitle, infoValue, ingredients ->
        Favourite(
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
    favourite: Favourite,
    priceId: Long,
    feedbackId: Long,
) = insertFavourite(
    title = favourite.title,
    favourite.subtitle,
    priceId = priceId,
    feedbackId = feedbackId,
    available = favourite.available,
    description = favourite.description,
    ingredients = favourite.ingredients
)
