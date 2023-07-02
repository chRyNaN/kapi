@file:Suppress("unused")

package com.chrynan.kapi.server.graphql.core.i18n

/**
 * A class that represents the intention to create an I18n message
 */
class I18nMsg {

    val key: String
    val args: List<Any?>

    constructor(key: String, args: List<Any?>) {
        this.key = key
        this.args = args
    }

    constructor(key: String, vararg args: Any?) {
        this.key = key
        this.args = args.toList()
    }

    fun copy(
        key: String = this.key,
        args: List<Any?> = this.args
    ): I18nMsg = I18nMsg(
        key = key,
        args = args
    )

    fun toI18n(i18n: I18n): String =
        i18n.msg(key, args)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is I18nMsg) return false

        if (key != other.key) return false

        return args == other.args
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + args.hashCode()
        return result
    }

    override fun toString(): String =
        "I18nMsg(key='$key', args=$args)"
}
