package com.paranid5.emonlineship.data.favourites

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.paranid5.emonlineshop.data.Favourites
import com.paranid5.emonlineshop.data.FavouritesQueries
import com.paranid5.emonlineshop.domain.product.FavouriteProduct
import com.paranid5.emonlineshop.domain.product.Feedback
import com.paranid5.emonlineshop.domain.product.IProduct
import com.paranid5.emonlineshop.domain.product.Info
import com.paranid5.emonlineshop.domain.product.Price
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
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

    private val mutex by lazy { Mutex() }

    @OptIn(ExperimentalCoroutinesApi::class)
    val favouritesFlow
        get() = queries
            .selectAndMapToFavourite()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .mapLatest { favourites ->
                withContext(Dispatchers.Default) {
                    favourites
                        .groupBy(IProduct::id)
                        .mapNotNull { (_, same) ->
                            same.firstOrNull()?.copy(
                                tags = same.retrieveBy(IProduct::tags),
                                info = same.retrieveBy(IProduct::info),
                                coversUrls = same.retrieveBy(IProduct::coversUrls)
                            )
                        }
                }
            }

    fun addFavouriteAsync(favouriteProduct: IProduct): Job =
        launch(Dispatchers.IO) {
            mutex.withLock { addFavourite(favouriteProduct) }
        }

    private fun addFavourite(product: IProduct) = runCatching {
        queries.transaction {
            val priceId = queries.insertPriceIfNotExists(product.price)
            val feedbackId = queries.insertFeedbackIfNotExists(product.feedback)
            val infoIds = queries.insertInfo(product.info)
            val tagsIds = queries.insertTags(product.tags)
            val coversIds = queries.insertCovers(product.coversUrls)
            queries.insertFavourite(product, priceId, feedbackId)

            infoIds.forEach { queries.insertFavouriteInfo(product.id, it) }
            tagsIds.forEach { queries.insertFavouriteTag(product.id, it) }
            coversIds.forEach { queries.insertFavouriteCover(product.id, it) }
        }
    }

    fun removeFavouriteAsync(productId: String): Job =
        launch(Dispatchers.IO) {
            mutex.withLock {
                queries.transaction {
                    removeFavourite(productId)
                }
            }
        }

    private fun removeFavourite(productId: String) =
        queries.removeFavourite(productId)
}

internal fun FavouritesQueries.selectAndMapToFavourite() =
    selectFavourites { id, title, subtitle, price, discount, priceWithDiscount, unit, count, rating, tag, available, description, infoTitle, infoValue, ingredients, coverUrl ->
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
            ingredients = ingredients,
            coversUrls = listOf(coverUrl)
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
    tags.map {
        insertTagIfNotExists(it)
        insertedTagId().executeAsOne()
    }

internal fun FavouritesQueries.insertInfo(info: List<Info>) =
    info.map {
        insertInfoIfNotExists(it.title, it.value)
        insertedInfoId().executeAsOne()
    }

internal fun FavouritesQueries.insertCovers(coversUrls: List<String>) =
    coversUrls.map {
        insertCoverIfNotExists(it)
        insertedCoverId().executeAsOne()
    }

internal fun FavouritesQueries.insertFavourite(
    product: IProduct,
    priceId: Long,
    feedbackId: Long,
) = insertFavourite(
    id = product.id,
    title = product.title,
    product.subtitle,
    priceId = priceId,
    feedbackId = feedbackId,
    available = product.available,
    description = product.description,
    ingredients = product.ingredients
)

private fun <T> List<IProduct>.retrieveBy(transform: (IProduct) -> Iterable<T>): List<T> =
    LinkedHashSet(flatMap(transform).toList()).toList()