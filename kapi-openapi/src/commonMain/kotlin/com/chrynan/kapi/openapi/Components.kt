package com.chrynan.kapi.openapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Holds a set of reusable objects for different aspects of the OAS. All objects defined within the components object
 * will have no effect on the API unless they are explicitly referenced from properties outside the components object.
 *
 * This object MAY be extended with
 * [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).
 *
 * All the fixed fields declared above are objects that MUST use keys that match the regular expression:
 * ^[a-zA-Z0-9\.\-_]+$.
 *
 * @property [schemas] An object to hold reusable Schema Objects.
 * @property [responses] An object to hold reusable Response Objects.
 * @property [parameters] An object to hold reusable Parameter Objects.
 * @property [examples] An object to hold reusable Example Objects.
 * @property [requestBodies] An object to hold reusable Request Body Objects.
 * @property [headers] An object to hold reusable Header Objects.
 * @property [securitySchemes] An object to hold reusable Security Scheme Objects.
 * @property [links] An object to hold reusable Link Objects.
 * @property [callbacks] An object to hold reusable Callback Objects.
 * @property [pathItems] An object to hold reusable Path Item Object.
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#components-object)
 */
@Serializable
data class Components(
    @SerialName(value = "schemas") val schemas: Map<String, Schema>? = null,
    @SerialName(value = "responses") val responses: Map<String, ReferenceOrType<Response>>? = null,
    @SerialName(value = "parameters") val parameters: Map<String, ReferenceOrType<Parameter>>? = null,
    @SerialName(value = "examples") val examples: Map<String, ReferenceOrType<Example>>? = null,
    @SerialName(value = "requestBodies") val requestBodies: Map<String, ReferenceOrType<RequestBody>>? = null,
    @SerialName(value = "headers") val headers: Map<String, ReferenceOrType<Header>>? = null,
    @SerialName(value = "securitySchemes") val securitySchemes: Map<String, ReferenceOrType<SecurityScheme>>? = null,
    @SerialName(value = "links") val links: Map<String, ReferenceOrType<Link>>? = null,
    @SerialName(value = "callbacks") val callbacks: Map<String, ReferenceOrType<Callback>>? = null,
    @SerialName(value = "pathItems") val pathItems: Map<String, ReferenceOrType<PathItem>>? = null
)
