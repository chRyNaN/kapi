package com.chrynan.kapi.server.graphql.core.introspection

/**
 * Represents a component that can provide a [__typename] [String] value for an Object, Interface, or Union GraphQL
 * Type. According to the GraphQL specification, every Object, Interface, and Union GraphQL type has an implicit
 * "__typename" field (not defined in the fields list) that can be queried to obtain the name of the Type.
 *
 * @see [Specification](https://spec.graphql.org/June2018/#sec-Type-Name-Introspection)
 */
@Suppress("ClassName")
interface __typenameType {

    @Suppress("PropertyName")
    val __typename: String
}
