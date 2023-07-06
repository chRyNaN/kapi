package com.chrynan.kapi.server.graphql.core.execution

import com.chrynan.kapi.server.graphql.core.language.Document
import com.chrynan.kapi.server.graphql.core.language.OperationDefinition

/**
 * Retrieves the Operation to execute from a [Document] GraphQL request. This is either the [OperationDefinition] that
 * has the provided [operationName], or the first [OperationDefinition] in the [Document] if no [operationName] was
 * provided.
 *
 * @see [Specification](https://spec.graphql.org/June2018/#GetOperation())
 */
fun getOperation(document: Document, operationName: String? = null): OperationDefinition {
    val operations = document.definitions.filterIsInstance<OperationDefinition>()

    return if (operationName.isNullOrBlank()) {
        operations.first() // TODO: Else throw appropriate error
    } else {
        operations.first { it.name == operationName } // TODO: Else throw appropriate error
    }
}
