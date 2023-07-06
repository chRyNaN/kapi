package com.chrynan.kapi.server.graphql.core.schema

import com.chrynan.kapi.server.graphql.core.model.GraphQLEnvironment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.toList

fun interface BatchLoader<Key, Value> {

    suspend fun load(keys: Set<Key>): Map<Key, Value>

    annotation class Key(val name: String = "")

    fun interface KeyResolver<Key> {

        suspend fun resolve(arguments: Map<String, Any>): Key
    }
}

class BatchDataFetcher<Key, Value>(
    private val batchLoader: BatchLoader<Key, Value>,
    private val keyResolver: BatchLoader.KeyResolver<Key>
) : DataFetcher<Value> {

    private val keys = mutableSetOf<Key>()
    private val result = MutableStateFlow<Map<Key, Value>?>(null)

    override suspend fun fetch(environment: GraphQLEnvironment): Value {
        val key = keyResolver.invoke(arguments = environment.arguments)

        keys.add(key)

        return result.filterNotNull()
            .toList()
            .first()
            .getValue(key = key)
    }

    suspend fun load() {
        result.value = batchLoader.invoke(keys = keys)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BatchDataFetcher<*, *>) return false

        if (batchLoader != other.batchLoader) return false
        if (keyResolver != other.keyResolver) return false
        if (keys != other.keys) return false

        return result == other.result
    }

    override fun hashCode(): Int {
        var result1 = batchLoader.hashCode()
        result1 = 31 * result1 + keyResolver.hashCode()
        result1 = 31 * result1 + keys.hashCode()
        result1 = 31 * result1 + result.hashCode()
        return result1
    }

    override fun toString(): String =
        "BatchDataFetcher(batchLoader=$batchLoader, keyResolver=$keyResolver, keys=$keys, result=$result)"
}

suspend operator fun <Key, Value> BatchLoader<Key, Value>.invoke(keys: Set<Key>): Map<Key, Value> =
    load(keys = keys)

suspend operator fun <Key> BatchLoader.KeyResolver<Key>.invoke(arguments: Map<String, Any>): Key =
    resolve(arguments = arguments)

suspend operator fun <Key, Value> BatchDataFetcher<Key, Value>.invoke() =
    load()
