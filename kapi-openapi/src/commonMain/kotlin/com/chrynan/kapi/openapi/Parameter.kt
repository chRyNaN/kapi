package com.chrynan.kapi.openapi

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Describes a single operation parameter.
 *
 * A unique parameter is defined by a combination of a name and location.
 *
 * ## Parameter Locations
 *
 * There are four possible parameter locations specified by the in field:
 *
 * - path - Used together with [Path Templating](https://spec.openapis.org/oas/v3.1.0#pathTemplating), where the
 * parameter value is actually part of the operation’s URL. This does not include the host or base path of the API. For
 * example, in /items/{itemId}, the path parameter is itemId.
 * - query - Parameters that are appended to the URL. For example, in /items?id=###, the query parameter is id.
 * - header - Custom headers that are expected as part of the request. Note that
 * [RFC7230](https://spec.openapis.org/oas/v3.1.0#bib-RFC7230) states header names are case-insensitive.
 * - cookie - Used to pass a specific cookie value to the API.
 *
 * @property [name] **REQUIRED**. The name of the parameter. Parameter names are case-sensitive.
 * - If in is "path", the name field MUST correspond to a template expression occurring within the path field in the
 * Paths Object. See Path Templating for further information.
 * - If in is "header" and the name field is "Accept", "Content-Type" or "Authorization", the parameter definition
 * SHALL be ignored.
 * - For all other cases, the name corresponds to the parameter name used by the in property.
 * @property [inValue] **REQUIRED**. The location of the parameter. Possible values are "query", "header", "path" or
 * "cookie".
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
 * // TODO more properties
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#parameter-object)
 */
@Serializable
data class Parameter(
    @SerialName(value = "name") val name: String,
    @SerialName(value = "in") val inValue: String,
    @SerialName(value = "description") val description: String? = null,
    @SerialName(value = "required") val required: Boolean = false,
    @SerialName(value = "deprecated") val deprecated: Boolean = false,
    @SerialName(value = "allowEmptyValue") val allowEmptyValue: Boolean = false
)
