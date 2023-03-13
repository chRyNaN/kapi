package com.chrynan.kapi.openapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A metadata object that allows for more fine-tuned XML model definitions.
 *
 * When using arrays, XML element names are not inferred (for singular/plural forms) and the name property SHOULD be
 * used to add that information. See examples for expected behavior.
 *
 * This object MAY be extended with
 * [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).
 *
 * @property [name] Replaces the name of the element/attribute used for the described schema property. When defined
 * within items, it will affect the name of the individual XML elements within the list. When defined alongside type
 * being array (outside the items), it will affect the wrapping element and only if wrapped is true. If wrapped is
 * false, it will be ignored.
 * @property [namespace] The URI of the namespace definition. This MUST be in the form of an absolute URI.
 * @property [prefix] The prefix to be used for the name.
 * @property [attribute] Declares whether the property definition translates to an attribute instead of an element.
 * Default value is false.
 * @property [wrapped] MAY be used only for an array definition. Signifies whether the array is wrapped (for example,
 * <books><book/><book/></books>) or unwrapped (<book/><book/>). Default value is false. The definition takes effect
 * only when defined alongside type being array (outside the items).
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#xml-object)
 */
@Serializable
data class Xml(
    @SerialName(value = "name") val name: String? = null,
    @SerialName(value = "namespace") val namespace: String? = null,
    @SerialName(value = "prefix") val prefix: String? = null,
    @SerialName(value = "attribute") val attribute: Boolean = false,
    @SerialName(value = "wrapped") val wrapped: Boolean = false
)
