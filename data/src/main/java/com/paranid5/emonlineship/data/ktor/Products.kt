package com.paranid5.emonlineship.data.ktor

import com.paranid5.emonlineshop.domain.product.ProductList
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

private const val DECO = "https://i.ibb.co/gSR5PvD/deco.webp"
private const val ESFOLIO = "https://i.ibb.co/cr4V0NC/esfolio.webp"
private const val EVELINE = "https://i.ibb.co/3NPprZ3/eveline.webp"
private const val PIEU = "https://i.ibb.co/4NXYWYh/pieu.webp"
private const val SHIT = "https://i.ibb.co/Cz6gLyj/shit.webp"
private const val VOX = "https://i.ibb.co/0M4BRDx/vox.webp"

suspend fun HttpClient.fetchProducts() =
    get("https://run.mocky.io/v3/97e721a7-0a66-4cae-b445-83cc0bcf9010")
        .body<ProductList>()
        .items
        .map { it.copy(coversUrls = mockCovers(it.id)) }

private fun mockCovers(id: String): List<String> =
    when (id) {
        "cbf0c984-7c6c-4ada-82da-e29dc698bb50" ->
            listOf(VOX, EVELINE, PIEU, ESFOLIO)

        "54a876a5-2205-48ba-9498-cfecff4baa6e" ->
            listOf(PIEU, ESFOLIO, VOX, EVELINE)

        "75c84407-52e1-4cce-a73a-ff2d3ac031b3" ->
            listOf(EVELINE, VOX, ESFOLIO, SHIT)

        "16f88865-ae74-4b7c-9d85-b68334bb97db" ->
            listOf(DECO, SHIT, EVELINE, PIEU)

        "26f88856-ae74-4b7c-9d85-b68334bb97db" ->
            listOf(ESFOLIO, DECO, SHIT, PIEU)

        "15f88865-ae74-4b7c-9d81-b78334bb97db" ->
            listOf(VOX, PIEU, ESFOLIO, EVELINE)

        "88f88865-ae74-4b7c-9d81-b78334bb97db" ->
            listOf(SHIT, DECO, EVELINE, VOX)

        "55f58865-ae74-4b7c-9d81-b78334bb97db" ->
            listOf(PIEU, EVELINE, DECO, SHIT)

        else -> listOf()
    }