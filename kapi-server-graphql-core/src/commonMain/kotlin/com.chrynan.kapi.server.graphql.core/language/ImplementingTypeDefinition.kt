package com.chrynan.kapi.server.graphql.core.language

/**
 * A [TypeDefinition] that might implement interfaces.
 */
sealed interface ImplementingTypeDefinition : TypeDefinition {

    val implements: List<TypeName>

    val fieldDefinitions: List<FieldDefinition>
}
