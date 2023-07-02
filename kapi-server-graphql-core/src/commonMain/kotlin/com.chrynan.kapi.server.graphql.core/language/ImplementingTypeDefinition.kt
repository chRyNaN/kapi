package com.chrynan.kapi.server.graphql.core.language

/**
 * A [TypeDefinition] that might implement interfaces.
 */
sealed interface ImplementingTypeDefinition : TypeDefinition {

    val implements: List<Type>

    val fieldDefinitions: List<FieldDefinition>
}
