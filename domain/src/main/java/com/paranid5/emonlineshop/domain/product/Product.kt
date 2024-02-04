package com.paranid5.emonlineshop.domain.product

import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Product(
    override val id: String,
    override val title: String,
    override val subtitle: String,
    override val price: Price,
    override val feedback: Feedback,
    override val tags: List<String>,
    override val available: Long,
    override val description: String,
    override val info: List<Info>,
    override val ingredients: String,
    override val coversUrls: List<String> = listOf()
) : IProduct