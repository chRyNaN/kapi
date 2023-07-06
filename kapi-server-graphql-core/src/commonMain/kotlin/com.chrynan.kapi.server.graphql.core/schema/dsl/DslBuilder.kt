package com.chrynan.kapi.server.graphql.core.schema.dsl

/**
 * Represents the base of all GraphQL Schema Builders, defining common functions and properties. This is a base class
 * that can be used to build an instance of a class of type [T].
 */
abstract class DslBuilder<T> {

    /**
     * Constructs an instance of type [T].
     */
    internal abstract fun build(): T
}
