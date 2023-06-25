@file:Suppress("unused")

package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.json.Json

/**
 * A wrapper class around the [Argument]s provided in a GraphQL query for accessing a field.
 *
 * @see [Argument] for more information about GraphQL arguments.
 */
class Arguments : Collection<Argument> {

    override val size: Int
        get() = values.size

    /**
     * The [Json] object that is to convert an argument's [Argument.element] field into the expected value. Defaults to
     * [Json.Default].
     */
    val json: Json

    /**
     * The underlying storage of the arguments. Arguments are stored as a [Map] of [String] names to their [Argument]
     * instances. This allows for quick access to the [Argument] associated with a name. Defaults to an empty [Map].
     */
    val values: Map<String, Argument>

    /**
     * Constructs an instance of the [Arguments] class using the provided values.
     *
     * @param [json] The [Json] object that is to convert an argument's [Argument.element] field into the expected
     * value. Defaults to [Json.Default].
     * @param [arguments] A [Map] of [Argument]s associated by their [Argument.name]. Defaults to an empty [Map]. Note
     * that a copy of this map will be made, so changes to the original map will not be reflected within the stored
     * map. This is done so that the [values] is considered immutable.
     */
    constructor(
        json: Json = Json.Default,
        arguments: Map<String, Argument> = emptyMap()
    ) {
        this.json = json
        this.values = arguments.toMap()
    }

    /**
     * Constructs an instance of the [Arguments] class using the provided values.
     *
     * @param [json] The [Json] object that is to convert an argument's [Argument.element] field into the expected
     * value. Defaults to [Json.Default].
     * @param [arguments] A [Set] of [Argument]s. Note that a copy of this set will be made, so changes to the original
     * set will not be reflected within the stored set. This is done so that the [values] is considered immutable. Note
     * that [Argument]s containing the same [Argument.name] will be overridden with the last one when converting this
     * [Set] to the [values] [Map].
     */
    constructor(
        json: Json = Json.Default,
        arguments: Set<Argument>
    ) {
        this.json = json
        this.values = arguments.associateBy { it.name }
    }

    override fun isEmpty(): Boolean =
        values.isEmpty()

    override fun iterator(): Iterator<Argument> =
        values.values.iterator()

    override fun containsAll(elements: Collection<Argument>): Boolean =
        values.values.containsAll(elements)

    override fun contains(element: Argument): Boolean =
        values.containsValue(element)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Arguments) return false

        if (json != other.json) return false

        return values == other.values
    }

    override fun hashCode(): Int {
        var result = json.hashCode()
        result = 31 * result + values.hashCode()
        return result
    }

    override fun toString(): String =
        "Arguments(json=$json, size=$size, values=${values.values})"

    /**
     * Retrieves the value for the argument with the provided name, or `null` if there is no argument with the provided
     * name.
     *
     * @param [name] The name of the [Argument] whose value is to be returned.
     *
     * @see [getValue] for a variation of this function that throws a [NoSuchElementException] instead of returning
     * `null` when an argument with the provided name doesn't exist.
     * @see [Argument.value] for the conversion of the [Argument.element] to the expected value.
     */
    inline operator fun <reified T> get(name: String): T? {
        val argument = values[name] ?: return null

        return argument.value(json = json)
    }

    /**
     * Retrieves the value for the argument with the provided name, or throws a [NoSuchElementException] if there is no
     * argument with the provided name.
     *
     * @param [name] The name of the [Argument] whose value is to be returned.
     *
     * @see [get] for a variation of this function that returns `null` instead of throwing a [NoSuchElementException]
     * when an argument with the provided name doesn't exist.
     * @see [Argument.value] for the conversion of the [Argument.element] to the expected value.
     */
    inline fun <reified T> getValue(name: String): T =
        get(name = name) ?: throw NoSuchElementException("There was no argument with name '$name'.")
}
