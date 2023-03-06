@file:Suppress("unused")

package com.chrynan.kapi.server.processor.core.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Represents a name of a Kotlin Type or component.
 *
 * @property [full] The fully qualified name of the Kotlin Type (ex: kotlin.String, com.chrynan.example.Model, etc). If
 * this represents a "simple name" and not a "qualified name", then this will be equivalent to the [short] value.
 * @property [short] The short part of the name of the Kotlin Type or component. If this represents a "fully qualified"
 * name, then it is the last part (ex: "Model" in "com.chrynan.example.Model"). Otherwise, if this represents a
 * "simple name", then this will be equivalent to the [full] name.
 * @property [qualifier] The [full] value except for the last part used for the [short] property.
 */
@Serializable
data class KotlinName(
    @SerialName(value = "full") val full: String,
    @SerialName(value = "short") val short: String,
    @SerialName(value = "qualifier") val qualifier: String? = null
) {

    /**
     * Determines whether this [KotlinName] is considered a "simple name", that is a name without a qualifier
     * (ex: "SimpleName" instead of "com.chrynan.FullyQualifiedName"). The [full] property represents the full name,
     * whereas the [short] property represents the last part of a fully qualified name. If both the [full] and [short]
     * values are equal, then there is no qualifier and this is considered a "simple name".
     */
    val isSimple: Boolean
        get() = full == short

    /**
     * Attempts to retrieve the package name from this [KotlinName] instance. The package name is considered the
     * [qualifier] value until the first uppercase letter is reached. For instance, consider the [KotlinName] for the
     * [KotlinName.full] value of `com.example.test.OuterType.InnerType`, then the [KotlinName.qualifier] value would
     * be `com.example.test.OuterType`, and the [KotlinName.packageName] would be `com.example.test`.
     *
     * Note that if [KotlinName.isSimple] returns `true`, then this will result in a `null` value.
     *
     * Note that this may not return a correct result in all instances. Use with caution.
     */
    val packageName: String?
        get() = qualifier?.takeWhile { !it.isUpperCase() }
            ?.removeSuffix(".")
            ?.trim()
            ?.takeIf { it.isNotBlank() }
}

/**
 * Constructs a [KotlinName] instance from the provided [full] name of the Kotlin Type or component.
 */
fun KotlinName(
    full: String
): KotlinName {
    val lastPeriodIndex = full.lastIndexOf(".")
    val firstPart = if (lastPeriodIndex >= 0) full.substring(startIndex = 0, endIndex = lastPeriodIndex) else null
    val lastPart =
        if (lastPeriodIndex != -1 && lastPeriodIndex < full.length - 1) full.substring(startIndex = lastPeriodIndex + 1) else null

    val qualifier = firstPart.takeIf { !it.isNullOrBlank() }
    val short = if (lastPart.isNullOrBlank()) full else lastPart

    return KotlinName(
        full = full,
        short = short,
        qualifier = qualifier
    )
}
