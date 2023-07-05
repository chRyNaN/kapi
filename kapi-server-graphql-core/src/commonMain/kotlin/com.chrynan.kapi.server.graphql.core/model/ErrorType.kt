@file:Suppress("unused")

package com.chrynan.kapi.server.graphql.core.model

import graphql.ErrorClassification
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * All the errors in graphql belong to one of these categories.
 */
@Serializable
enum class ErrorType(val serialName: String) : ErrorClassification {

    @SerialName(value = "InvalidSyntax")
    InvalidSyntax(serialName = "InvalidSyntax"),

    @SerialName(value = "ValidationError")
    ValidationError(serialName = "ValidationError"),

    @SerialName(value = "DataFetchingException")
    DataFetchingException(serialName = "DataFetchingException"),

    @SerialName(value = "NullValueInNonNullableField")
    NullValueInNonNullableField(serialName = "NullValueInNonNullableField"),

    @SerialName(value = "OperationNotSupported")
    OperationNotSupported(serialName = "OperationNotSupported"),

    @SerialName(value = "ExecutionAborted")
    ExecutionAborted(serialName = "ExecutionAborted");

    companion object {

        fun getBySerialName(name: String, ignoreCase: Boolean = true): ErrorType? =
            values().firstOrNull { it.serialName.equals(name, ignoreCase) }
    }
}
