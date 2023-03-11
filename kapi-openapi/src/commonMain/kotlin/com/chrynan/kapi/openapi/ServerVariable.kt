package com.chrynan.kapi.openapi

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * An object representing a Server Variable for server URL template substitution.
 *
 * This object MAY be extended with
 * [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).
 *
 * @property [enum] An enumeration of string values to be used if the substitution options are from a limited set. The
 * array MUST NOT be empty.
 * @property [default] **REQUIRED**. The default value to use for substitution, which SHALL be sent if an alternate
 * value is not supplied. Note this behavior is different than the Schema Object’s treatment of default values,
 * because in those cases parameter values are optional. If the enum is defined, the value MUST exist in the enum’s
 * values.
 * @property [description] An optional description for the server variable.
 * [CommonMark syntax](https://spec.commonmark.org/) MAY be used for rich text representation.
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#server-variable-object)
 */
@Serializable
data class ServerVariable(
    @SerialName(value = "enum") val enum: List<String>? = null,
    @SerialName(value = "default") val default: String,
    @SerialName(value = "description") val description: String? = null
)
