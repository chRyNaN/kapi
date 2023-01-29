package com.chrynan.kapi.core.annotation

import kotlinx.serialization.json.Json
import kotlin.reflect.KClass

/**
 * Defines a Content Type [String] value for the annotated class so that it can be used with the [RequestBody] and
 * [ResponseBody] annotations so that the correct content type value can be used for serialization/de-serialization.
 * The [RequestBody] and [ResponseBody] annotations require a [KClass] parameter value, where that class must be
 * annotated with this [ContentType] annotation so that the content type value can be resolved. One exception is that
 * the [Json] object from the kotlinx.serialization library is considered implicitly annotated with
 * `@ContentType("application/json")`. This makes it simply to use the [Json] and not have to introduce a new model
 * just for the annotation purposes, which may lead to confusion when importing the different types.
 *
 * **Example Usage:**
 * ```kotlin
 * @ContentType("application/x-www-form-urlencoded")
 * object FormUrlEncoded
 *
 * @RequestBody(FormUrlEncoded::class)
 * ```
 */
@MustBeDocumented
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class ContentType(val value: String)
