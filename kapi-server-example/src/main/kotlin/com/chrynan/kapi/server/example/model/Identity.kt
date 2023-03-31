@file:Suppress("unused")

package com.chrynan.kapi.server.example.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class Identity(
    @SerialName(value = "id") val id: String,
    @SerialName(value = "created") val created: Instant,
    @SerialName(value = "updated") val updated: Instant,
    @SerialName(value = "display_name") val displayName: String? = null,
    @SerialName(value = "handle") val handle: String? = null,
    @SerialName(value = "anonymous") val isAnonymous: Boolean = false,
    @SerialName(value = "images") val images: List<Image> = emptyList(),
    @SerialName(value = "traits") val traits: JsonObject? = null
)
