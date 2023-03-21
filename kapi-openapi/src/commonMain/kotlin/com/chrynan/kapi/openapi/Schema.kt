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
 * @property [description] [CommonMark syntax](https://spec.commonmark.org/) MAY be used for rich text representation.
 * @property [format] See [Data Type Formats](https://spec.openapis.org/oas/v3.1.0#dataTypeFormat) for further details.
 * While relying on JSON Schema’s defined formats, the OAS offers a few additional predefined formats.
 * @property [type] The value of this keyword MUST be either a string or an array. If it is an array, elements of the
 * array MUST be strings and MUST be unique. String values MUST be one of the six primitive types ("null", "boolean",
 * "object", "array", "number", or "string"), or "integer" which matches any number with a zero fractional part. An
 * instance validates if and only if the instance is in any of the sets listed for this keyword.
 * @property [enum] The value of this keyword MUST be an array. This array SHOULD have at least one element. Elements
 * in the array SHOULD be unique. An instance validates successfully against this keyword if its value is equal to one
 * of the elements in this keyword's array value. Elements in the array might be of any type, including null.
 * @property [const] The value of this keyword MAY be of any type, including null. Use of this keyword is functionally
 * equivalent to an "enum" (Section 6.1.2) with a single value. An instance validates successfully against this keyword
 * if its value is equal to the value of the keyword.
 * @property [multipleOf] The value of "multipleOf" MUST be a number, strictly greater than 0. A numeric instance is
 * valid only if division by this keyword's value results in an integer.
 * @property [maximum] The value of "maximum" MUST be a number, representing an inclusive upper limit for a numeric
 * instance. If the instance is a number, then this keyword validates only if the instance is less than or exactly
 * equal to "maximum".
 * @property [exclusiveMaximum] The value of "exclusiveMaximum" MUST be a number, representing an exclusive upper limit
 * for a numeric instance. If the instance is a number, then the instance is valid only if it has a value strictly less
 * than (not equal to) "exclusiveMaximum".
 * @property [minimum] The value of "minimum" MUST be a number, representing an inclusive lower limit for a numeric
 * instance. If the instance is a number, then this keyword validates only if the instance is greater than or exactly
 * equal to "minimum".
 * @property [exclusiveMinimum] The value of "exclusiveMinimum" MUST be a number, representing an exclusive lower limit
 * for a numeric instance. If the instance is a number, then the instance is valid only if it has a value strictly
 * greater than (not equal to) "exclusiveMinimum".
 * @property [maxLength] The value of this keyword MUST be a non-negative integer. A string instance is valid against
 * this keyword if its length is less than, or equal to, the value of this keyword. The length of a string instance is
 * defined as the number of its characters as defined by RFC 8259
 * [RFC8259](https://datatracker.ietf.org/doc/html/rfc8259).
 * @property [minLength] The value of this keyword MUST be a non-negative integer. A string instance is valid against
 * this keyword if its length is greater than, or equal to, the value of this keyword. The length of a string instance
 * is defined as the number of its characters as defined by RFC 8259
 * [RFC8259](https://datatracker.ietf.org/doc/html/rfc8259). Omitting this keyword has the same behavior as a value of
 * 0.
 * @property [pattern] The value of this keyword MUST be a string. This string SHOULD be a valid regular expression,
 * according to the ECMA-262 regular expression dialect. A string instance is considered valid if the regular
 * expression matches the instance successfully. Recall: regular expressions are not implicitly anchored.
 * @property [maxItems] The value of this keyword MUST be a non-negative integer. An array instance is valid against
 * "maxItems" if its size is less than, or equal to, the value of this keyword.
 * @property [minItems] The value of this keyword MUST be a non-negative integer. An array instance is valid against
 * "minItems" if its size is greater than, or equal to, the value of this keyword. Omitting this keyword has the same
 * behavior as a value of 0.
 * @property [uniqueItems] The value of this keyword MUST be a boolean. If this keyword has boolean value false, the
 * instance validates successfully. If it has boolean value true, the instance validates successfully if all of its
 * elements are unique. Omitting this keyword has the same behavior as a value of false.
 * @property [maxContains] The value of this keyword MUST be a non-negative integer. If "contains" is not present
 * within the same schema object, then this keyword has no effect. An instance array is valid against "maxContains" in
 * two ways, depending on the form of the annotation result of an adjacent "contains" [json-schema] keyword. The first
 * way is if the annotation result is an array and the length of that array is less than or equal to the "maxContains"
 * value. The second way is if the annotation result is a boolean "true" and the instance array length is less than or
 * equal to the "maxContains" value.
 * @property [minContains] The value of this keyword MUST be a non-negative integer. If "contains" is not present
 * within the same schema object, then this keyword has no effect. An instance array is valid against "minContains" in
 * two ways, depending on the form of the annotation result of an adjacent "contains" [json-schema] keyword. The first
 * way is if the annotation result is an array and the length of that array is greater than or equal to the
 * "minContains" value.  The second way is if the annotation result is a boolean "true" and the instance array length
 * is greater than or equal to the "minContains" value. A value of 0 is allowed, but is only useful for setting a range
 * of occurrences from 0 to the value of "maxContains". A value of 0 with no "maxContains" causes "contains" to always
 * pass validation. Omitting this keyword has the same behavior as a value of 1.
 * @property [maxProperties] The value of this keyword MUST be a non-negative integer. An object instance is valid
 * against "maxProperties" if its number of properties is less than, or equal to, the value of this keyword.
 * @property [minProperties] The value of this keyword MUST be a non-negative integer. An object instance is valid
 * against "minProperties" if its number of properties is greater than, or equal to, the value of this keyword.
 * Omitting this keyword has the same behavior as a value of 0.
 * @property [required] The value of this keyword MUST be an array. Elements of this array, if any, MUST be strings,
 * and MUST be unique. An object instance is valid against this keyword if every item in the array is the name of a
 * property in the instance. Omitting this keyword has the same behavior as an empty array.
 * @property [dependentRequired] The value of this keyword MUST be an object. Properties in this object, if any, MUST
 * be arrays. Elements in each array, if any, MUST be strings, and MUST be unique. This keyword specifies properties
 * that are required if a specific other property is present. Their requirement is dependent on the presence of the
 * other property. Validation succeeds if, for each name that appears in both the instance and as a name within this
 * keyword's value, every item in the corresponding array is also the name of a property in the instance. Omitting this
 * keyword has the same behavior as an empty object.
 * @property [contentEncoding] If the instance value is a string, this property defines that the string SHOULD be
 * interpreted as binary data and decoded using the encoding named by this property. Possible values indicating base
 * 16, 32, and 64 encodings with several variations are listed in RFC 4648 [RFC4648]. Additionally, sections 6.7 and
 * 6.8 of RFC 2045 [RFC2045] provide encodings used in MIME. As "base64" is defined in both RFCs, the definition from
 * RFC 4648 SHOULD be assumed unless the string is specifically intended for use in a MIME context. Note that all of
 * these encodings result in strings consisting only of 7-bit ASCII characters. Therefore, this keyword has no meaning
 * for strings containing characters outside of that range. If this keyword is absent, but "contentMediaType" is
 * present, this indicates that the encoding is the identity encoding, meaning that no transformation was needed in
 * order to represent the content in a UTF-8 string. The value of this property MUST be a string.
 * @property [contentMediaType] If the instance is a string, this property indicates the media type of the contents of
 * the string.  If "contentEncoding" is present, this property describes the decoded string. The value of this property
 * MUST be a string, which MUST be a media type, as defined by RFC 2046 [RFC2046].
 * @property [contentSchema] If the instance is a string, and if "contentMediaType" is present, this property contains
 * a schema which describes the structure of the string. This keyword MAY be used with any media type that can be
 * mapped into JSON Schema's data model. The value of this property MUST be a valid JSON schema. It SHOULD be ignored
 * if "contentMediaType" is not present.
 * @property [default] There are no restrictions placed on the value of this keyword. When multiple occurrences of this
 * keyword are applicable to a single sub-instance, implementations SHOULD remove duplicates. This keyword can be used
 * to supply a default JSON value associated with a particular schema. It is RECOMMENDED that a default value be valid
 * against the associated schema.
 * @property [deprecated] The value of this keyword MUST be a boolean. When multiple occurrences of this keyword are
 * applicable to a single sub-instance, applications SHOULD consider the instance location to be deprecated if any
 * occurrence specifies a true value. If "deprecated" has a value of boolean true, it indicates that applications
 * SHOULD refrain from usage of the declared property. It MAY mean the property is going to be removed in the future. A
 * root schema containing "deprecated" with a value of true indicates that the entire resource being described MAY be
 * removed in the future. The "deprecated" keyword applies to each instance location to which the schema object
 * containing the keyword successfully applies. This can result in scenarios where every array item or object property
 * is deprecated even though the containing array or object is not. Omitting this keyword has the same behavior as a
 * value of false.
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#schema-object)
 * @see [JSON Schema Core Specification](https://datatracker.ietf.org/doc/html/draft-bhutton-json-schema-00)
 * @see [JSON Schema Validation Specification](https://datatracker.ietf.org/doc/html/draft-bhutton-json-schema-validation-00#section-6)
 */
@Serializable
data class Schema(
    @SerialName(value = "\$schema") val schema: String? = null,
    @SerialName(value = "\$vocabulary") val vocabulary: JsonElement? = null,
    @SerialName(value = "\$id") val id: String? = null,
    @SerialName(value = "\$ref") val reference: String? = null,
    @SerialName(value = "\$dynamicRef") val dynamicRef: String? = null,
    @SerialName(value = "\$defs") val defs: JsonElement? = null,
    @SerialName(value = "\$comment") val comment: String? = null,
    @SerialName(value = "discriminator") val discriminator: Discriminator? = null,
    @SerialName(value = "xml") val xml: Xml? = null,
    @SerialName(value = "externalDocs") val externalDocs: ExternalDocumentation? = null,
    @SerialName(value = "example") val example: JsonElement? = null,
    @SerialName(value = "examples") val examples: JsonElement? = null,
    @SerialName(value = "description") val description: String? = null,
    @SerialName(value = "format") val format: String? = null,
    @SerialName(value = "type") val type: DataType? = null,
    @SerialName(value = "enum") val enum: List<JsonElement>? = null,
    @SerialName(value = "const") val const: JsonElement? = null,
    @SerialName(value = "multipleOf") val multipleOf: Long? = null,
    @SerialName(value = "maximum") val maximum: Long? = null,
    @SerialName(value = "exclusiveMaximum") val exclusiveMaximum: Long? = null,
    @SerialName(value = "minimum") val minimum: Long? = null,
    @SerialName(value = "exclusiveMinimum") val exclusiveMinimum: Long? = null,
    @SerialName(value = "maxLength") val maxLength: ULong? = null,
    @SerialName(value = "minLength") val minLength: ULong? = null,
    @SerialName(value = "pattern") val pattern: String? = null,
    @SerialName(value = "maxItems") val maxItems: ULong? = null,
    @SerialName(value = "minItems") val minItems: ULong? = null,
    @SerialName(value = "uniqueItems") val uniqueItems: Boolean = false,
    @SerialName(value = "maxContains") val maxContains: ULong? = null,
    @SerialName(value = "minContains") val minContains: ULong? = null,
    @SerialName(value = "maxProperties") val maxProperties: ULong? = null,
    @SerialName(value = "minProperties") val minProperties: ULong? = null,
    @SerialName(value = "required") val required: List<String>? = null,
    @SerialName(value = "dependentRequired") val dependentRequired: JsonElement? = null,
    @SerialName(value = "contentEncoding") val contentEncoding: String? = null,
    @SerialName(value = "contentMediaType") val contentMediaType: String? = null,
    @SerialName(value = "contentSchema") val contentSchema: JsonElement? = null,
    @SerialName(value = "default") val default: JsonElement? = null,
    @SerialName(value = "deprecated") val deprecated: Boolean = false,
    @SerialName(value = "readOnly") val readOnly: Boolean = false,
    @SerialName(value = "writeOnly") val writeOnly: Boolean = false,
    @SerialName(value = "allOf") val allOf: List<Schema>? = null,
    @SerialName(value = "anyOf") val anyOf: List<Schema>? = null,
    @SerialName(value = "oneOf") val oneOf: List<Schema>? = null,
    @SerialName(value = "not") val not: Schema? = null,
    @SerialName(value = "if") val `if`: Schema? = null,
    @SerialName(value = "then") val then: Schema? = null,
    @SerialName(value = "else") val `else`: Schema? = null,
    @SerialName(value = "dependentSchemas") val dependentSchemas: Map<String, Schema>? = null,
    @SerialName(value = "prefixItems") val prefixItems: List<Schema>? = null,
    @SerialName(value = "items") val items: Schema? = null,
    @SerialName(value = "contains") val contains: Schema? = null,
    @SerialName(value = "properties") val properties: Map<String, Schema>? = null,
    @SerialName(value = "patternProperties") val patternProperties: Map<String, Schema>? = null,
    @SerialName(value = "additionalProperties") val additionalProperties: JsonElement? = null,
    @SerialName(value = "propertyNames") val propertyNames: Schema? = null,
    @SerialName(value = "unevaluatedItems") val unevaluatedItems: Schema? = null,
    @SerialName(value = "unevaluatedProperties") val unevaluatedProperties: Schema? = null,
    @SerialName(value = "nullable") val nullable: Boolean = false
)
