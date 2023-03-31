package com.chrynan.kapi.server.core.annotation

/**
 * Represents an API component. A component annotated with [Api] can have code auto-generated depending on the
 * components structure, such as Ktor bindings and OpenAPI Specification files. A component annotated with this [Api]
 * annotation can be a Kotlin interface, class, abstract class, or an object. Since the implementation of the component
 * is defined by the developer, it doesn't matter from the perspective of the auto-generation tools whether the
 * component is a class or an interface for example.
 *
 * A component annotated with [Api] contains annotated functions which define the various HTTP methods of the API. This
 * allows the API to be written idiomatically in Kotlin without conforming to particularly framework structures. For
 * instance, the return type, of an API function, is the HTTP response body, instead of having to explicitly respond
 * with an HTTP body using framework or library specific concepts within the function. This allows the developer to
 * write familiar Kotlin code for their APIs.
 *
 * Note that blank [String] properties are equivalent to `null`. This is due to Kotlin annotations not allowing
 * nullable properties, so blank strings are defaults.
 *
 * @property [name] The name of this API. This will be used in the generation of code and should be a value that can be
 * used as a valid Kotlin class name. If this value is blank, it is considered `null` and the name of the annotated
 * component will be used instead.
 * @property [basePath] A base path or route applied to each of this API component's functions before their paths are
 * applied. For instance, if this value is equal to "example", and this API component has an API function with a path
 * value of "test", then that API function's full path will be equal to "example/test". If this value is blank, it is
 * considered `null` and there will be no base path prepended to the API functions path values.
 * @property [info] Information about this API. If all the fields in the provided [Info] object are blank or considered
 * `null`, then this value will be equivalent to `null`.
 * @property [servers] An array of known servers that implement this API.
 * @property [tags] An array of tags applied to this API.
 * @property [securitySchemes] Supported [SecurityScheme]s for this API.
 */
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
@ExperimentalServerApi
annotation class Api(
    val name: String = "",
    val basePath: String = "",
    val info: Info = Info(),
    val servers: Array<Server> = [],
    val tags: Array<Tag> = [],
    val securitySchemes: Array<SecurityScheme> = []
)

/**
 * Represents information about an API. This class mirrors the Info model from the OpenAPI Specification (with some
 * subtle differences) and can be used in the generation of an OpenAPI Specification model.
 *
 * Note that blank fields are equivalent to `null` and an instance with all blank fields can be considered equivalent
 * to a `null` instance. This is due to Kotlin annotations not allowing nullable fields, so blank strings are defaults.
 *
 * @property [title] The title of the API. If this value is blank, it is considered `null` and the [Api.name] or name
 * of the annotated component will be used as a default.
 * @property [summary] A short summary of the API.
 * @property [termsOfService] A URL to the Terms of Service for the API. This MUST be in the form of a URL.
 * @property [contact] The contact information for the exposed API.
 * @property [license] The license information for the exposed API.
 * @property [version] The version of this API.
 */
@Target()
@MustBeDocumented
@ExperimentalServerApi
annotation class Info(
    val title: String = "",
    val summary: String = "",
    val termsOfService: String = "",
    val contact: Contact = Contact(),
    val license: License = License(),
    val version: String = ""
)

/**
 * Represents contact information for an API. This class mirrors the Contact model from the OpenAPI Specification and
 * can be used in the generation of an OpenAPI Specification model.
 *
 * Note that blank [String] fields are equivalent to `null` and an instance with all blank fields can be considered
 * equivalent to a `null` instance. This is due to Kotlin annotations not allowing nullable fields, so blank strings
 * are defaults.
 *
 * ## Example
 *
 * ```kotlin
 * Contact(
 *     name = "API Support",
 *     url = "https://www.example.com/support",
 *     email = "support@example.com")
 * ```
 *
 * @property [name] The identifying name of the contact person/organization.
 * @property [url] The URL pointing to the contact information. This MUST be in the form of a URL.
 * @property [email] The email address of the contact person/organization. This MUST be in the form of an email address.
 */
@Target()
@MustBeDocumented
@ExperimentalServerApi
annotation class Contact(
    val name: String = "",
    val url: String = "",
    val email: String = ""
)

/**
 * Represents license information for an API. This class mirrors the License model from the OpenAPI Specification and
 * can be used in the generation of an OpenAPI Specification model.
 *
 * Note that blank [String] fields are equivalent to `null` and an instance with all blank fields can be considered
 * equivalent to a `null` instance. This is due to Kotlin annotations not allowing nullable fields, so blank strings
 * can be defaults.
 *
 * ## Example
 *
 * ```kotlin
 * License(
 *     name = "Apache 2.0",
 *     identifier = "Apache-2.0")
 * ```
 *
 * @property [name] **REQUIRED**. The license name used for the API.
 * @property [identifier] An [SPDX](https://spdx.dev/spdx-specification-21-web-version/#h.jxpfx0ykyb60) license
 * expression for the API. The identifier field is mutually exclusive of the url field.
 * @property [url] A URL to the license used for the API. This MUST be in the form of a URL. The url field is mutually
 * exclusive of the identifier field.
 */
@Target()
@MustBeDocumented
@ExperimentalServerApi
annotation class License(
    val name: String = "",
    val identifier: String = "",
    val url: String = ""
)

/**
 * Represents a server endpoint hosting an instance of an API. This corresponds to the Server model from the OpenAPI
 * Specification.
 *
 * Note that blank [String] properties are equivalent to `null`. This is due to Kotlin annotations not allowing
 * nullable properties, so blank strings can be defaults. Therefore, required [String] fields must not be blank.
 *
 * @property [url] A URL to the target host. This URL supports Server Variables. Variable substitutions will be made
 * when a variable is named in {brackets}.
 * @property [description] An optional string describing the host designated by the URL. CommonMark syntax MAY be used
 * for rich text representation. If this value is blank, it is considered `null`.
 * @property [variables] An array of variables used for substitution in the server's URL template.
 */
@Target()
@MustBeDocumented
@ExperimentalServerApi
annotation class Server(
    val url: String,
    val description: String = "",
    val variables: Array<ServerVariable> = []
)

/**
 * Represents a server variable for server URL template substitution. This corresponds to the ServerVariable model from
 * the OpenAPI Specification.
 *
 * Note that blank [String] properties are equivalent to `null`. This is due to Kotlin annotations not allowing
 * nullable properties, so blank strings can be defaults. Therefore, required [String] fields must not be blank.
 *
 * @property [name] The name of this variable.
 * @property [defaultValue] The default value of this variable.
 * @property [description] An optional description for the server variable. If this value is blank, it is considered
 * `null`.
 * @property [allowableValues] An array of allowable values for this variable.
 */
@Target()
@MustBeDocumented
@ExperimentalServerApi
annotation class ServerVariable(
    val name: String,
    val defaultValue: String,
    val description: String = "",
    val allowableValues: Array<String> = []
)

/**
 * Represents a tag, or category, that describes the annotated component. This could be used to group or associate
 * related API components within a UI, for instance. Tags are unique within an API, so there cannot be more than one
 * Tag with the same [Tag.name] value.
 *
 * Note that blank [String] properties are equivalent to `null`. This is due to Kotlin annotations not allowing
 * nullable properties, so blank strings can be defaults. Therefore, required [String] fields must not be blank.
 *
 * @property [name] The name of this tag.
 * @property [description] A short description for this tag. If this value is blank, it is considered `null`.
 */
@Target()
@MustBeDocumented
@ExperimentalServerApi
annotation class Tag(
    val name: String,
    val description: String = ""
)
