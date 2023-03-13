package com.chrynan.kapi.openapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Describes the operations available on a single path. A Path Item MAY be empty, due to ACL constraints. The path
 * itself is still exposed to the documentation viewer but they will not know which operations and parameters are
 * available.
 *
 * This object MAY be extended with
 * [Specification Extensions](https://spec.openapis.org/oas/v3.1.0#specificationExtensions).
 *
 * @property [ref] Allows for a referenced definition of this path item. The referenced structure MUST be in the form
 * of a Path Item Object. In case a Path Item Object field appears both in the defined object and the referenced
 * object, the behavior is undefined. See the rules for resolving Relative References.
 * @property [summary] An optional, string summary, intended to apply to all operations in this path.
 * @property [description] An optional, string description, intended to apply to all operations in this path.
 * [CommonMark syntax](https://spec.commonmark.org/) MAY be used for rich text representation.
 * @property [get] A definition of a GET operation on this path.
 * @property [put] A definition of a PUT operation on this path.
 * @property [post] A definition of a POST operation on this path.
 * @property [delete] A definition of a DELETE operation on this path.
 * @property [options] A definition of a OPTIONS operation on this path.
 * @property [head] A definition of a HEAD operation on this path.
 * @property [patch] A definition of a PATCH operation on this path.
 * @property [trace] A definition of a TRACE operation on this path.
 * @property [servers] An alternative server array to service all operations in this path.
 * @property [parameters] A list of parameters that are applicable for all the operations described under this path.
 * These parameters can be overridden at the operation level, but cannot be removed there. The list MUST NOT include
 * duplicated parameters. A unique parameter is defined by a combination of a name and location. The list can use the
 * Reference Object to link to parameters that are defined at the OpenAPI Object’s components/parameters.
 *
 * @see [OpenApi Specification](https://spec.openapis.org/oas/v3.1.0#path-item-object)
 */
@Serializable
data class PathItem(
    @SerialName(value = "\$ref") val ref: String? = null,
    @SerialName(value = "summary") val summary: String? = null,
    @SerialName(value = "description") val description: String? = null,
    @SerialName(value = "get") val get: Operation? = null,
    @SerialName(value = "put") val put: Operation? = null,
    @SerialName(value = "post") val post: Operation? = null,
    @SerialName(value = "delete") val delete: Operation? = null,
    @SerialName(value = "options") val options: Operation? = null,
    @SerialName(value = "head") val head: Operation? = null,
    @SerialName(value = "patch") val patch: Operation? = null,
    @SerialName(value = "trace") val trace: Operation? = null,
    @SerialName(value = "servers") val servers: List<Server>? = null,
    @SerialName(value = "parameters") val parameters: List<ReferenceOrType<Parameter>>? = null
)
