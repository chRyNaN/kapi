package com.chrynan.kapi.openapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * This is the root object of the OpenAPI document.
 *
 * This object MAY be extended with
 * [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).
 *
 * @property [openApiVersion] REQUIRED. This string MUST be the version number of the OpenAPI Specification that the
 * OpenAPI document uses. The openapi field SHOULD be used by tooling to interpret the OpenAPI document. This is not
 * related to the API info.version string.
 * @property [info] REQUIRED. Provides metadata about the API. The metadata MAY be used by tooling as required.
 * @property [jsonSchemaDialect] The default value for the $schema keyword within Schema Objects contained within this
 * OAS document. This MUST be in the form of a URI.
 * @property [servers] An array of Server Objects, which provide connectivity information to a target server. If the
 * servers property is not provided, or is an empty array, the default value would be a Server Object with a url value
 * of /.
 * @property [paths] The available paths and operations for the API.
 * @property [webhooks] The incoming webhooks that MAY be received as part of this API and that the API consumer MAY
 * choose to implement. Closely related to the callbacks feature, this section describes requests initiated other than
 * by an API call, for example by an out of band registration. The key name is a unique string to refer to each
 * webhook, while the (optionally referenced) Path Item Object describes a request that may be initiated by the API
 * provider and the expected responses. An example is available.
 * @property [components] An element to hold various schemas for the document.
 * @property [security] A declaration of which security mechanisms can be used across the API. The list of values
 * includes alternative security requirement objects that can be used. Only one of the security requirement objects
 * need to be satisfied to authorize a request. Individual operations can override this definition. To make security
 * optional, an empty security requirement ({}) can be included in the array.
 * @property [tags] A list of tags used by the document with additional metadata. The order of the tags can be used to
 * reflect on their order by the parsing tools. Not all tags that are used by the Operation Object must be declared.
 * The tags that are not declared MAY be organized randomly or based on the tools’ logic. Each tag name in the list
 * MUST be unique.
 * @property [externalDocs] Additional external documentation.
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#openapi-object)
 */
@Serializable
data class OpenApi(
    @SerialName(value = "openapi") val openApiVersion: String,
    @SerialName(value = "info") val info: Info,
    @SerialName(value = "jsonSchemaDialect") val jsonSchemaDialect: String? = null,
    @SerialName(value = "servers") val servers: List<Server>? = null,
    @SerialName(value = "paths") val paths: Paths? = null,
    @SerialName(value = "webhooks") val webhooks: Map<String, ReferenceOrType<PathItem>>? = null,
    @SerialName(value = "components") val components: Components? = null,
    @SerialName(value = "security") val security: List<JsonElement>? = null,
    @SerialName(value = "tags") val tags: List<Tag>? = null,
    @SerialName(value = "externalDocs") val externalDocs: ExternalDocumentation? = null
)
