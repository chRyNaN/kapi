package com.chrynan.kapi.server.graphql.core

import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject

internal fun <T> Json.encodeToGraphQLJson(
    serializer: SerializationStrategy<T>,
    value: T,
    fieldNames: Set<String> = emptySet()
): JsonElement {
    val json = this.encodeToJsonElement(serializer = serializer, value = value)

    return if (fieldNames.isNotEmpty() && json is JsonObject) {
        buildJsonObject {
            json.entries.filter { it.key in fieldNames }
                .forEach {
                    put(key = it.key, element = it.value)
                }
        }
    } else {
        json
    }
}

fun <T> Json.encodeToJsonElement(value: ResolvedType<T>): JsonElement =
    Json.encodeToJsonElement(serializer = value.serializer, value = value.value)
