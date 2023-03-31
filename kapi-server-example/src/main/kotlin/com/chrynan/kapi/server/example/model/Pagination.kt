@file:Suppress("unused")

package com.chrynan.kapi.server.example.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PageDirection {

    @SerialName(value = "before")
    BEFORE,

    @SerialName(value = "after")
    AFTER
}

@Serializable
data class PageInfo(
    @SerialName(value = "index") val index: UInt,
    @SerialName(value = "count") val count: UInt,
    @SerialName(value = "previous") val hasPrevious: Boolean = false,
    @SerialName(value = "next") val hasNext: Boolean = false,
    @SerialName(value = "first_id") val firstId: String? = null,
    @SerialName(value = "last_id") val lastId: String? = null
)

@Serializable
data class Page<T : Any>(
    @SerialName(value = "items") val items: List<T> = emptyList(),
    @SerialName(value = "info") val info: PageInfo
)
