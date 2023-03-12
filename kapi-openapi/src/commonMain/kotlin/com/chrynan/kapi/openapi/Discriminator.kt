package com.chrynan.kapi.openapi

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * When request bodies or response payloads may be one of a number of different schemas, a discriminator object can be
 * used to aid in serialization, deserialization, and validation. The discriminator is a specific object in a schema
 * which is used to inform the consumer of the document of an alternative schema based on the value associated with it.
 *
 * When using the discriminator, inline schemas will not be considered.
 *
 * This object MAY be extended with
 * [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).
 *
 * The discriminator object is legal only when using one of the composite keywords oneOf, anyOf, allOf.
 *
 * In OAS 3.0, a response payload MAY be described to be exactly one of any number of types:
 *
 * ```yaml
 * MyResponseType:
 *   oneOf:
 *   - $ref: '#/components/schemas/Cat'
 *   - $ref: '#/components/schemas/Dog'
 *   - $ref: '#/components/schemas/Lizard'
 * ```
 *
 * which means the payload MUST, by validation, match exactly one of the schemas described by Cat, Dog, or Lizard. In
 * this case, a discriminator MAY act as a “hint” to shortcut validation and selection of the matching schema which may
 * be a costly operation, depending on the complexity of the schema. We can then describe exactly which field tells us
 * which schema to use:
 *
 * ```yaml
 * MyResponseType:
 *   oneOf:
 *   - $ref: '#/components/schemas/Cat'
 *   - $ref: '#/components/schemas/Dog'
 *   - $ref: '#/components/schemas/Lizard'
 *   discriminator:
 *     propertyName: petType
 * ```
 *
 * The expectation now is that a property with name petType MUST be present in the response payload, and the value will
 * correspond to the name of a schema defined in the OAS document. Thus, the response payload:
 *
 * ```json
 * {
 *   "id": 12345,
 *   "petType": "Cat"
 * }
 * ```
 *
 * Will indicate that the Cat schema be used in conjunction with this payload.
 *
 * @property [propertyName] **REQUIRED**. The name of the property in the payload that will hold the discriminator
 * value.
 * @property [mapping] An object to hold mappings between payload values and schema names or references.
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#discriminator-object)
 */
@Serializable
data class Discriminator(
    @SerialName(value = "propertyName") val propertyName: String,
    @SerialName(value = "mapping") val mapping: Map<String, String>? = null
)
