package com.chrynan.kapi.openapi

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonElement

/**
 * The Schema Object allows the definition of input and output data types. These types can be objects, but also
 * primitives and arrays. This object is a superset of the
 * [JSON Schema Specification Draft 2020-12](https://tools.ietf.org/html/draft-bhutton-json-schema-00).
 *
 * For more information about the properties, see
 * [JSON Schema Core](https://tools.ietf.org/html/draft-bhutton-json-schema-00) and
 * [JSON Schema Validation](https://tools.ietf.org/html/draft-bhutton-json-schema-validation-00).
 *
 * Unless stated otherwise, the property definitions follow those of JSON Schema and do not add any additional
 * semantics. Where JSON Schema indicates that behavior is defined by the application (e.g. for annotations), OAS also
 * defers the definition of semantics to the application consuming the OpenAPI document.
 *
 * This object MAY be extended with
 * [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions), though as noted,
 * additional properties MAY omit the x- prefix within this object.
 *
 * @property [discriminator] Adds support for polymorphism. The discriminator is an object name that is used to
 * differentiate between other schemas which may satisfy the payload description. See
 * [Composition and Inheritance](https://spec.openapis.org/oas/v3.1.0#schemaComposition) for more details.
 * @property [xml] This MAY be used only on properties schemas. It has no effect on root schemas. Adds additional
 * metadata to describe the XML representation of this property.
 * @property [externalDocs] Additional external documentation for this schema.
 * @property [example] A free-form property to include an example of an instance for this schema. To represent examples
 * that cannot be naturally represented in JSON or YAML, a string value can be used to contain the example with
 * escaping where necessary. **Deprecated**: The example property has been deprecated in favor of the JSON Schema
 * examples keyword. Use of example is discouraged, and later versions of this specification may remove it.
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#schema-object)
 */
@Serializable
data class Schema(
    @SerialName(value = "discriminator") val discriminator: Discriminator? = null,
    @SerialName(value = "xml") val xml: Xml? = null,
    @SerialName(value = "externalDocs") val externalDocs: ExternalDocumentation? = null,
    @SerialName(value = "example") val example: JsonElement? = null
)
