@file:Suppress("unused")

package com.chrynan.kapi.server.graphql.core.i18n

import com.chrynan.locale.core.Locale
import com.chrynan.locale.core.getDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A component that handles formatting messages in a locale-agnostic manner. Use the [I18n] constructor function to
 * create an instance of this class.
 */
interface I18n {

    val locale: Locale
    val bundleType: BundleType

    /**
     * Creates an I18N message using the key and arguments
     *
     * @param key  the key in the underlying message bundle
     * @param args the message arguments
     *
     * @return the formatted I18N message
     */
    fun msg(key: String, vararg args: Any): String =
        msg(key, args.toList())

    /**
     * Creates an I18N message using the key and arguments
     *
     * @param key  the key in the underlying message bundle
     * @param args the message arguments
     *
     * @return the formatted I18N message
     */
    fun msg(key: String, args: List<Any?>): String

    /**
     * This enum is a type safe way to control what resource bundle to load from
     */
    @Serializable
    enum class BundleType(val serialName: String) {

        @SerialName(value = "Parsing")
        Parsing(serialName = "Parsing"),

        @SerialName(value = "Scalars")
        Scalars(serialName = "Scalars"),

        @SerialName(value = "Validation")
        Validation(serialName = "Validation"),

        @SerialName(value = "Execution")
        Execution(serialName = "Execution"),

        @SerialName(value = "General")
        General(serialName = "General");

        val baseName: String = "i18n.$name"

        companion object {

            fun getBySerialName(name: String, ignoreCase: Boolean = true): BundleType? =
                values().firstOrNull { it.name.equals(name, ignoreCase) }
        }
    }

    companion object
}

/**
 * Creates an instance of the [I18n] class that can be used to create messages in a locale-agnostic manner.
 */
expect fun I18n(
    bundleType: I18n.BundleType,
    locale: Locale = Locale.getDefault()
): I18n
