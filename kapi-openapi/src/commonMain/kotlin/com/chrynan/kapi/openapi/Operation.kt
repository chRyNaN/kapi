package com.chrynan.kapi.openapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Describes a single API operation on a path.
 *
 * This object MAY be extended with
 * [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).
 *
 * ## Operation Object Example
 *
 * ```json
 * {
 *   "tags": [
 *     "pet"
 *   ],
 *   "summary": "Updates a pet in the store with form data",
 *   "operationId": "updatePetWithForm",
 *   "parameters": [
 *     {
 *       "name": "petId",
 *       "in": "path",
 *       "description": "ID of pet that needs to be updated",
 *       "required": true,
 *       "schema": {
 *         "type": "string"
 *       }
 *     }
 *   ],
 *   "requestBody": {
 *     "content": {
 *       "application/x-www-form-urlencoded": {
 *         "schema": {
 *           "type": "object",
 *           "properties": {
 *             "name": {
 *               "description": "Updated name of the pet",
 *               "type": "string"
 *             },
 *             "status": {
 *               "description": "Updated status of the pet",
 *               "type": "string"
 *             }
 *           },
 *           "required": ["status"]
 *         }
 *       }
 *     }
 *   },
 *   "responses": {
 *     "200": {
 *       "description": "Pet updated.",
 *       "content": {
 *         "application/json": {},
 *         "application/xml": {}
 *       }
 *     },
 *     "405": {
 *       "description": "Method Not Allowed",
 *       "content": {
 *         "application/json": {},
 *         "application/xml": {}
 *       }
 *     }
 *   },
 *   "security": [
 *     {
 *       "petstore_auth": [
 *         "write:pets",
 *         "read:pets"
 *       ]
 *     }
 *   ]
 * }
 * ```
 *
 * @property [tags] A list of tags for API documentation control. Tags can be used for logical grouping of operations
 * by resources or any other qualifier.
 * @property [summary] A short summary of what the operation does.
 * @property [description] A verbose explanation of the operation behavior.
 * [CommonMark syntax](https://spec.commonmark.org/) MAY be used for rich text representation.
 * @property [externalDocs] Additional external documentation for this operation.
 * @property [operationId] Unique string used to identify the operation. The id MUST be unique among all operations
 * described in the API. The operationId value is case-sensitive. Tools and libraries MAY use the operationId to
 * uniquely identify an operation, therefore, it is RECOMMENDED to follow common programming naming conventions.
 * @property [parameters] A list of parameters that are applicable for this operation. If a parameter is already
 * defined at the Path Item, the new definition will override it but can never remove it. The list MUST NOT include
 * duplicated parameters. A unique parameter is defined by a combination of a name and location. The list can use the
 * Reference Object to link to parameters that are defined at the OpenAPI Object’s components/parameters.
 * @property [requestBody] The request body applicable for this operation. The requestBody is fully supported in HTTP
 * methods where the HTTP 1.1 specification [RFC7231] has explicitly defined semantics for request bodies. In other
 * cases where the HTTP spec is vague (such as GET, HEAD and DELETE), requestBody is permitted but does not have
 * well-defined semantics and SHOULD be avoided if possible.
 * @property [responses] The list of possible responses as they are returned from executing this operation.
 * @property [callbacks] A map of possible out-of band callbacks related to the parent operation. The key is a unique
 * identifier for the Callback Object. Each value in the map is a Callback Object that describes a request that may be
 * initiated by the API provider and the expected responses.
 * @property [deprecated] Declares this operation to be deprecated. Consumers SHOULD refrain from usage of the declared
 * operation. Default value is false.
 * @property [security] A declaration of which security mechanisms can be used for this operation. The list of values
 * includes alternative security requirement objects that can be used. Only one of the security requirement objects
 * need to be satisfied to authorize a request. To make security optional, an empty security requirement ({}) can be
 * included in the array. This definition overrides any declared top-level security. To remove a top-level security
 * declaration, an empty array can be used.
 * @property [servers] An alternative server array to service this operation. If an alternative server object is
 * specified at the Path Item Object or Root level, it will be overridden by this value.
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#operation-object)
 */
@Serializable
data class Operation(
    @SerialName(value = "tags") val tags: List<String>? = null,
    @SerialName(value = "summary") val summary: String? = null,
    @SerialName(value = "description") val description: String? = null,
    @SerialName(value = "externalDocs") val externalDocs: ExternalDocumentation? = null,
    @SerialName(value = "operationId") val operationId: String? = null,
    @SerialName(value = "parameters") val parameters: List<ReferenceOrType<Parameter>>? = null,
    @SerialName(value = "requestBody") val requestBody: ReferenceOrType<RequestBody>? = null,
    @SerialName(value = "responses") val responses: Responses? = null,
    @SerialName(value = "callbacks") val callbacks: Map<String, ReferenceOrType<Callback>>? = null,
    @SerialName(value = "deprecated") val deprecated: Boolean = false,
    @SerialName(value = "security") val security: List<JsonElement>? = null,
    @SerialName(value = "servers") val servers: List<Server>? = null
)
