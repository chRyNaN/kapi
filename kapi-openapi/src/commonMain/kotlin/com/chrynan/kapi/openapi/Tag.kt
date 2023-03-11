package com.chrynan.kapi.openapi

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Adds metadata to a single tag that is used by the Operation Object. It is not mandatory to have a Tag Object per tag
 * defined in the Operation Object instances.
 *
 * ## Tag Object Example
 *
 * ```json
 * {
 * 	"name": "pet",
 * 	"description": "Pets operations"
 * }
 * ```
 *
 * @property [name] **REQUIRED**. The name of the tag.
 * @property [description] A description for the tag. [CommonMark syntax](https://spec.commonmark.org/) MAY be used for
 * rich text representation.
 * @property [externalDocs] Additional external documentation for this tag.
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#tag-object)
 */
@Serializable
data class Tag(
    @SerialName(value = "name") val name: String,
    @SerialName(value = "description") val description: String? = null,
    @SerialName(value = "externalDocs") val externalDocs: ExternalDocumentation? = null
)
