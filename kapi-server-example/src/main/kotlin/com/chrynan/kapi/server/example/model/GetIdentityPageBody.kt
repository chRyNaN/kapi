package com.chrynan.kapi.server.example.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetIdentityPageBody(
    @SerialName(value = "direction") val direction: PageDirection,
    @SerialName(value = "id") val id: String
)
