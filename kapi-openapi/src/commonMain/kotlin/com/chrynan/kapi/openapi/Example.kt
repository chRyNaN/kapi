package com.chrynan.kapi.openapi

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonElement

/**
 * This object MAY be extended with
 * [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).
 *
 * In all cases, the example value is expected to be compatible with the type schema of its associated value. Tooling
 * implementations MAY choose to validate compatibility automatically, and reject the example value(s) if incompatible.
 *
 * @property [summary] Short description for the example.
 * @property [description] Long description for the example. [CommonMark syntax](https://spec.commonmark.org/) MAY be
 * used for rich text representation.
 * @property [value] Embedded literal example. The value field and externalValue field are mutually exclusive. To
 * represent examples of media types that cannot naturally represented in JSON or YAML, use a string value to contain
 * the example, escaping where necessary.
 * @property [externalValue] A URI that points to the literal example. This provides the capability to reference
 * examples that cannot easily be included in JSON or YAML documents. The value field and externalValue field are
 * mutually exclusive. See the rules for resolving
 * [Relative References](https://spec.openapis.org/oas/v3.1.0#relativeReferencesURI).
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#example-object)
 */
@Serializable
data class Example(
    @SerialName(value = "summary") val summary: String? = null,
    @SerialName(value = "description") val description: String? = null,
    @SerialName(value = "value") val value: JsonElement? = null,
    @SerialName(value = "externalValue") val externalValue: String? = null
)
