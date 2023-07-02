package com.chrynan.kapi.server.graphql.core.i18n

import com.chrynan.locale.core.Locale
import com.chrynan.locale.core.toJvmLocale
import graphql.Assert
import java.text.MessageFormat
import java.util.*

private class JvmI18n(
    override val locale: Locale,
    override val bundleType: I18n.BundleType
) : I18n {

    // load the resource bundle with this classes class loader - to help avoid confusion in complicated worlds
    // like OSGI
    private val resourceBundle: ResourceBundle = ResourceBundle.getBundle(
        bundleType.baseName,
        locale.toJvmLocale().jvmLocale,
        I18n::class.java.classLoader!!
    )

    override fun msg(key: String, args: List<Any?>): String {
        val argsArray = args.toTypedArray()

        var msgPattern: String? = null

        try {
            msgPattern = resourceBundle.getString(key)
        } catch (e: MissingResourceException) {
            Assert.assertShouldNeverHappen<Any>("There must be a resource bundle key called %s", key)
        }

        return msgPattern?.let { MessageFormat(it).format(argsArray) }
            ?: error("msgPattern was `null`. There must be a resource bundle key called $key")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is JvmI18n) return false

        if (locale != other.locale) return false
        if (bundleType != other.bundleType) return false

        return resourceBundle == other.resourceBundle
    }

    override fun hashCode(): Int {
        var result = locale.hashCode()
        result = 31 * result + bundleType.hashCode()
        result = 31 * result + resourceBundle.hashCode()
        return result
    }

    override fun toString(): String =
        "JvmI18n(locale=$locale, bundleType=$bundleType, resourceBundle=$resourceBundle)"
}

actual fun I18n(
    bundleType: I18n.BundleType,
    locale: Locale,
): I18n = JvmI18n(
    locale = locale,
    bundleType = bundleType
)
