package com.paranid5.emonlineship.data.ktor

import com.paranid5.data.R
import com.paranid5.emonlineshop.domain.product.ProductList
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

suspend fun HttpClient.fetchProducts() =
    get("https://run.mocky.io/v3/97e721a7-0a66-4cae-b445-83cc0bcf9010")
        .body<ProductList>()
        .items
        .map { it.copy(coversRes = mockCovers(it.id)) }

private fun mockCovers(id: String): List<Int> =
    when (id) {
        "cbf0c984-7c6c-4ada-82da-e29dc698bb50" ->
            listOf(R.drawable.vox, R.drawable.eveline, R.drawable.pieu, R.drawable.esfolio)

        "54a876a5-2205-48ba-9498-cfecff4baa6e" ->
            listOf(R.drawable.pieu, R.drawable.esfolio, R.drawable.vox, R.drawable.eveline)

        "75c84407-52e1-4cce-a73a-ff2d3ac031b3" ->
            listOf(R.drawable.eveline, R.drawable.vox, R.drawable.esfolio, R.drawable.shit)

        "16f88865-ae74-4b7c-9d85-b68334bb97db" ->
            listOf(R.drawable.deco, R.drawable.shit, R.drawable.eveline, R.drawable.pieu)

        "26f88856-ae74-4b7c-9d85-b68334bb97db" ->
            listOf(R.drawable.esfolio, R.drawable.deco, R.drawable.shit, R.drawable.pieu)

        "15f88865-ae74-4b7c-9d81-b78334bb97db" ->
            listOf(R.drawable.vox, R.drawable.pieu, R.drawable.esfolio, R.drawable.eveline)

        "88f88865-ae74-4b7c-9d81-b78334bb97db" ->
            listOf(R.drawable.shit, R.drawable.deco, R.drawable.eveline, R.drawable.vox)

        "55f58865-ae74-4b7c-9d81-b78334bb97db" ->
            listOf(R.drawable.pieu, R.drawable.eveline, R.drawable.deco, R.drawable.shit)

        else -> listOf()
    }