package com.chrynan.kapi.server.core.annotation

/**
 * This annotation is provided on a function that represents an API function that responds to HTTP DELETE requests. An
 * API function can only be annotated with one HTTP method annotation.
 *
 * ## Example
 *
 * ```kotlin
 * @DELETE("/user/{id}")
 * suspend fun deleteUser(@Path("id") id: String)
 * ```
 *
 * @property [path] The relative or absolute URL path of the API endpoint that the annotated function responds to. The
 * [Api.basePath] value from the containing component with the [Api] annotation will be prepended to this value to make
 * the full path, if it is not blank. A value for this property must be provided, but it may be blank, which indicates
 * that the path is the [Api.basePath] value or the root path of the server. The code generation tools should
 * appropriately handle leading and trailing forward slash characters ("/") when creating the endpoints, so a preceding
 * "/" character in the [path] value should be equivalent to no preceding slash character, and likewise for trailing
 * slash characters. A blank value and a value containing only a forward slash character are considered equivalent.
 * Path values may contain variables which can be extracted and provided to the API function as a parameter using the
 * [Path] annotation. A path variable is surrounded by a leading "{" character and a trailing "}" character. For
 * example, the following path value contains a path parameter with a name of "id": "/user/{id}".
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ExperimentalServerApi
annotation class DELETE(val path: String)

/**
 * This annotation is provided on a function that represents an API function that responds to HTTP GET requests. An
 * API function can only be annotated with one HTTP method annotation.
 *
 * ## Example
 *
 * ```kotlin
 * @GET("/user/{id}")
 * suspend fun getUser(@Path("id") id: String)
 * ```
 *
 * @property [path] The relative or absolute URL path of the API endpoint that the annotated function responds to. The
 * [Api.basePath] value from the containing component with the [Api] annotation will be prepended to this value to make
 * the full path, if it is not blank. A value for this property must be provided, but it may be blank, which indicates
 * that the path is the [Api.basePath] value or the root path of the server. The code generation tools should
 * appropriately handle leading and trailing forward slash characters ("/") when creating the endpoints, so a preceding
 * "/" character in the [path] value should be equivalent to no preceding slash character, and likewise for trailing
 * slash characters. A blank value and a value containing only a forward slash character are considered equivalent.
 * Path values may contain variables which can be extracted and provided to the API function as a parameter using the
 * [Path] annotation. A path variable is surrounded by a leading "{" character and a trailing "}" character. For
 * example, the following path value contains a path parameter with a name of "id": "/user/{id}".
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ExperimentalServerApi
annotation class GET(val path: String)

/**
 * This annotation is provided on a function that represents an API function that responds to HTTP HEAD requests. An
 * API function can only be annotated with one HTTP method annotation.
 *
 * ## Example
 *
 * ```kotlin
 * @HEAD("/file/{id}")
 * suspend fun getFileHeaders(@Path("id") id: String)
 * ```
 *
 * @property [path] The relative or absolute URL path of the API endpoint that the annotated function responds to. The
 * [Api.basePath] value from the containing component with the [Api] annotation will be prepended to this value to make
 * the full path, if it is not blank. A value for this property must be provided, but it may be blank, which indicates
 * that the path is the [Api.basePath] value or the root path of the server. The code generation tools should
 * appropriately handle leading and trailing forward slash characters ("/") when creating the endpoints, so a preceding
 * "/" character in the [path] value should be equivalent to no preceding slash character, and likewise for trailing
 * slash characters. A blank value and a value containing only a forward slash character are considered equivalent.
 * Path values may contain variables which can be extracted and provided to the API function as a parameter using the
 * [Path] annotation. A path variable is surrounded by a leading "{" character and a trailing "}" character. For
 * example, the following path value contains a path parameter with a name of "id": "/user/{id}".
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ExperimentalServerApi
annotation class HEAD(val path: String)

/**
 * This annotation is provided on a function that represents an API function that responds to HTTP OPTIONS requests. An
 * API function can only be annotated with one HTTP method annotation.
 *
 * ## Example
 *
 * ```kotlin
 * @OPTIONS("/")
 * suspend fun getServerOptions()
 * ```
 *
 * @property [path] The relative or absolute URL path of the API endpoint that the annotated function responds to. The
 * [Api.basePath] value from the containing component with the [Api] annotation will be prepended to this value to make
 * the full path, if it is not blank. A value for this property must be provided, but it may be blank, which indicates
 * that the path is the [Api.basePath] value or the root path of the server. The code generation tools should
 * appropriately handle leading and trailing forward slash characters ("/") when creating the endpoints, so a preceding
 * "/" character in the [path] value should be equivalent to no preceding slash character, and likewise for trailing
 * slash characters. A blank value and a value containing only a forward slash character are considered equivalent.
 * Path values may contain variables which can be extracted and provided to the API function as a parameter using the
 * [Path] annotation. A path variable is surrounded by a leading "{" character and a trailing "}" character. For
 * example, the following path value contains a path parameter with a name of "id": "/user/{id}".
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ExperimentalServerApi
annotation class OPTIONS(val path: String)

/**
 * This annotation is provided on a function that represents an API function that responds to HTTP PATCH requests. An
 * API function can only be annotated with one HTTP method annotation.
 *
 * ## Example
 *
 * ```kotlin
 * @PATCH("/user")
 * suspend fun updateUser(@Body user: User)
 * ```
 *
 * @property [path] The relative or absolute URL path of the API endpoint that the annotated function responds to. The
 * [Api.basePath] value from the containing component with the [Api] annotation will be prepended to this value to make
 * the full path, if it is not blank. A value for this property must be provided, but it may be blank, which indicates
 * that the path is the [Api.basePath] value or the root path of the server. The code generation tools should
 * appropriately handle leading and trailing forward slash characters ("/") when creating the endpoints, so a preceding
 * "/" character in the [path] value should be equivalent to no preceding slash character, and likewise for trailing
 * slash characters. A blank value and a value containing only a forward slash character are considered equivalent.
 * Path values may contain variables which can be extracted and provided to the API function as a parameter using the
 * [Path] annotation. A path variable is surrounded by a leading "{" character and a trailing "}" character. For
 * example, the following path value contains a path parameter with a name of "id": "/user/{id}".
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ExperimentalServerApi
annotation class PATCH(val path: String)

/**
 * This annotation is provided on a function that represents an API function that responds to HTTP POST requests. An
 * API function can only be annotated with one HTTP method annotation.
 *
 * ## Example
 *
 * ```kotlin
 * @POST("/user")
 * suspend fun updateUser(@Body user: User)
 * ```
 *
 * @property [path] The relative or absolute URL path of the API endpoint that the annotated function responds to. The
 * [Api.basePath] value from the containing component with the [Api] annotation will be prepended to this value to make
 * the full path, if it is not blank. A value for this property must be provided, but it may be blank, which indicates
 * that the path is the [Api.basePath] value or the root path of the server. The code generation tools should
 * appropriately handle leading and trailing forward slash characters ("/") when creating the endpoints, so a preceding
 * "/" character in the [path] value should be equivalent to no preceding slash character, and likewise for trailing
 * slash characters. A blank value and a value containing only a forward slash character are considered equivalent.
 * Path values may contain variables which can be extracted and provided to the API function as a parameter using the
 * [Path] annotation. A path variable is surrounded by a leading "{" character and a trailing "}" character. For
 * example, the following path value contains a path parameter with a name of "id": "/user/{id}".
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ExperimentalServerApi
annotation class POST(val path: String)

/**
 * This annotation is provided on a function that represents an API function that responds to HTTP PUT requests. An
 * API function can only be annotated with one HTTP method annotation.
 *
 * ## Example
 *
 * ```kotlin
 * @PUT("/user")
 * suspend fun updateUser(@Body user: User)
 * ```
 *
 * @property [path] The relative or absolute URL path of the API endpoint that the annotated function responds to. The
 * [Api.basePath] value from the containing component with the [Api] annotation will be prepended to this value to make
 * the full path, if it is not blank. A value for this property must be provided, but it may be blank, which indicates
 * that the path is the [Api.basePath] value or the root path of the server. The code generation tools should
 * appropriately handle leading and trailing forward slash characters ("/") when creating the endpoints, so a preceding
 * "/" character in the [path] value should be equivalent to no preceding slash character, and likewise for trailing
 * slash characters. A blank value and a value containing only a forward slash character are considered equivalent.
 * Path values may contain variables which can be extracted and provided to the API function as a parameter using the
 * [Path] annotation. A path variable is surrounded by a leading "{" character and a trailing "}" character. For
 * example, the following path value contains a path parameter with a name of "id": "/user/{id}".
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ExperimentalServerApi
annotation class PUT(val path: String)
