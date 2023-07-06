package com.chrynan.kapi.server.graphql.core.schema.dsl

abstract class SchemaBuilder internal constructor() {

    abstract fun query()

    abstract fun mutation()

    abstract fun subscription()

    abstract fun types()

    abstract fun directives()
}
