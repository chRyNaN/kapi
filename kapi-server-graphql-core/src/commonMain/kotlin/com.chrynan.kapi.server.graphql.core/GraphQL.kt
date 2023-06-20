package com.chrynan.kapi.server.graphql.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.toList

object GraphQL {

    annotation class OutputType(val name: String = "")

    annotation class InputType(val name: String = "")

    annotation class Interface(val name: String = "")

    annotation class Enum(val name: String = "")

    annotation class Scalar(val name: String = "")

    annotation class Union(val name: String = "")

    annotation class Directive(val name: String = "")

    annotation class Schema(val name: String = "")

    annotation class Query(val name: String = "")

    annotation class Mutation(val name: String = "")

    annotation class Subscription(val name: String = "")

    // Maps to DataFetcher
    annotation class Resolver(val name: String = "")

    // Maps to BatchLoader
    annotation class Loader(val name: String = "")

    @JvmInline
    value class Context(val values: Map<String, Any>)

    class Environment(
        val arguments: Map<String, Any> = emptyMap(),
        val context: Context,
        val source: Any? = null
    ) {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Environment) return false

            if (arguments != other.arguments) return false
            if (context != other.context) return false

            return source == other.source
        }

        override fun hashCode(): Int {
            var result = arguments.hashCode()
            result = 31 * result + context.hashCode()
            result = 31 * result + (source?.hashCode() ?: 0)
            return result
        }

        override fun toString(): String =
            "Environment(arguments=$arguments, context=$context, source=$source)"
    }

    fun interface DataFetcher<T> {

        suspend fun fetch(environment: Environment): T
    }

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

        override suspend fun fetch(environment: Environment): Value {
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
}

suspend operator fun <T> GraphQL.DataFetcher<T>.invoke(environment: GraphQL.Environment): T =
    fetch(environment = environment)

suspend operator fun <Key, Value> GraphQL.BatchLoader<Key, Value>.invoke(keys: Set<Key>): Map<Key, Value> =
    load(keys = keys)

suspend operator fun <Key> GraphQL.BatchLoader.KeyResolver<Key>.invoke(arguments: Map<String, Any>): Key =
    resolve(arguments = arguments)

suspend operator fun <Key, Value> GraphQL.BatchDataFetcher<Key, Value>.invoke() =
    load()
