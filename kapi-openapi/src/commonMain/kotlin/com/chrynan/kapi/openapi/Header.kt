package com.chrynan.kapi.openapi

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonElement

/**
 * The Header Object follows the structure of the [Parameter] Object with the following changes:
 *
 * - name MUST NOT be specified, it is given in the corresponding headers map.
 * - in MUST NOT be specified, it is implicitly in header.
 * - All traits that are affected by the location MUST be applicable to a location of header (for example, style).
 *
 * @property [description] A brief description of the parameter. This could contain examples of use.
 * [CommonMark syntax](https://spec.commonmark.org/) MAY be used for rich text representation.
 * @property [required] Determines whether this parameter is mandatory. If the
 * [parameter location](https://spec.openapis.org/oas/v3.1.0#parameterIn) is "path", this property is REQUIRED and its
 * value MUST be true. Otherwise, the property MAY be included and its default value is false.
 * @property [deprecated] Specifies that a parameter is deprecated and SHOULD be transitioned out of usage. Default
 * value is false.
 * @property [allowEmptyValue] Sets the ability to pass empty-valued parameters. This is valid only for query
 * parameters and allows sending a parameter with an empty value. Default value is false. If style is used, and if
 * behavior is n/a (cannot be serialized), the value of allowEmptyValue SHALL be ignored. Use of this property is NOT
 * RECOMMENDED, as it is likely to be removed in a later revision.
 * @property [style] Describes how the parameter value will be serialized depending on the type of the parameter value.
 * Default values (based on value of in): for query - form; for path - simple; for header - simple; for cookie - form.
 * @property [explode] When this is true, parameter values of type array or object generate separate parameters for
 * each value of the array or key-value pair of the map. For other types of parameters this property has no effect.
 * When style is form, the default value is true. For all other styles, the default value is false.
 * @property [allowReserved] Determines whether the parameter value SHOULD allow reserved characters, as defined by
 * [RFC3986](https://spec.openapis.org/oas/v3.1.0#bib-RFC3986) :/?#[]@!$&'()*+,;= to be included without
 * percent-encoding. This property only applies to parameters with an in value of query. The default value is false.
 * @property [schema] The schema defining the type used for the parameter.
 * @property [example] Example of the parameter’s potential value. The example SHOULD match the specified schema and
 * encoding properties if present. The example field is mutually exclusive of the examples field. Furthermore, if
 * referencing a schema that contains an example, the example value SHALL override the example provided by the schema.
 * To represent examples of media types that cannot naturally be represented in JSON or YAML, a string value can
 * contain the example with escaping where necessary.
 * @property [examples] Examples of the parameter’s potential value. Each example SHOULD contain a value in the correct
 * format as specified in the parameter encoding. The examples field is mutually exclusive of the example field.
 * Furthermore, if referencing a schema that contains an example, the examples value SHALL override the example
 * provided by the schema.
 * @property [content] A map containing the representations for the parameter. The key is the media type and the value
 * describes it. The map MUST only contain one entry.
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#header-object)
 */
@Serializable
data class Header(
    @SerialName(value = "description") val description: String? = null,
    @SerialName(value = "required") val required: Boolean = false,
    @SerialName(value = "deprecated") val deprecated: Boolean = false,
    @SerialName(value = "allowEmptyValue") val allowEmptyValue: Boolean = false,
    @SerialName(value = "style") val style: String? = null,
    @SerialName(value = "explode") val explode: Boolean = false,
    @SerialName(value = "allowReserved") val allowReserved: Boolean = false,
    @SerialName(value = "schema") val schema: Schema? = null,
    @SerialName(value = "example") val example: JsonElement? = null,
    @SerialName(value = "examples") val examples: Map<String, JsonElement>? = null,
    @SerialName(value = "content") val content: Map<String, MediaType>? = null
)
