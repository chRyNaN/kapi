@file:Suppress("unused")

package com.chrynan.kapi.server.graphql.core.language

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * A wrapper class around the [Argument]s provided in a GraphQL query for accessing a field.
 *
 * @see [Argument] for more information about GraphQL arguments.
 */
@Serializable
class Arguments : Collection<Argument> {

    @SerialName(value = "size")
    override val size: Int
        get() = values.size

    /**
     * The underlying storage of the arguments. Arguments are stored as a [Map] of [String] names to their [Argument]
     * instances. This allows for quick access to the [Argument] associated with a name. Defaults to an empty [Map].
     */
    @SerialName(value = "values")
    val values: Map<String, Argument>

    /**
     * Constructs an instance of the [Arguments] class using the provided values.
     *
     * @param [arguments] A [Map] of [Argument]s associated by their [Argument.name]. Defaults to an empty [Map]. Note
     * that a copy of this map will be made, so changes to the original map will not be reflected within the stored
     * map. This is done so that the [values] is considered immutable.
     */
    constructor(arguments: Map<String, Argument> = emptyMap()) {
        this.values = arguments.toMap()
    }

    /**
     * Constructs an instance of the [Arguments] class using the provided values.
     *
     * @param [arguments] A [Set] of [Argument]s. Note that a copy of this set will be made, so changes to the original
     * set will not be reflected within the stored set. This is done so that the [values] is considered immutable. Note
     * that [Argument]s containing the same [Argument.name] will be overridden with the last one when converting this
     * [Set] to the [values] [Map].
     */
    constructor(arguments: Set<Argument>) {
        this.values = arguments.associateBy { it.name }
    }

    /**
     * Constructs an instance of the [Arguments] class using the provided values.
     *
     * @param [arguments] A [Set] of [Argument]s. Note that a copy of this set will be made, so changes to the original
     * set will not be reflected within the stored set. This is done so that the [values] is considered immutable. Note
     * that [Argument]s containing the same [Argument.name] will be overridden with the last one when converting this
     * [Set] to the [values] [Map].
     */
    constructor(vararg arguments: Argument) {
        this.values = arguments.associateBy { it.name }
    }

    /**
     * Constructs an instance of the [Arguments] class using the provided values.
     *
     * @param [arguments] An [Array] of unique [Argument]s. Note that a copy of this set will be made, so changes to
     * the original set will not be reflected within the stored set. This is done so that the [values] is considered
     * immutable. Note that [Argument]s containing the same [Argument.name] will be overridden with the last one when
     * converting this [Set] to the [values] [Map].
     */
    constructor(arguments: Array<Argument>) {
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

        return values == other.values
    }

    override fun hashCode(): Int =
        values.hashCode()

    override fun toString(): String =
        "Arguments(size=$size, values=${values.values})"

    /**
     * Retrieves the value for the argument with the provided name, or `null` if there is no argument with the provided
     * name.
     *
     * @param [name] The name of the [Argument] whose value is to be returned.
     * @param [json] The [Json] object that is to convert an argument's [Argument.element] field into the expected
     * value. Defaults to [Json.Default].
     *
     * @see [getValue] for a variation of this function that throws a [NoSuchElementException] instead of returning
     * `null` when an argument with the provided name doesn't exist.
     * @see [Argument.value] for the conversion of the [Argument.element] to the expected value.
     */
    inline operator fun <reified T> get(name: String, json: Json = Json.Default): T? {
        val argument = values[name] ?: return null

        return argument.value(json = json)
    }

    /**
     * Retrieves the value for the argument with the provided name, or throws a [NoSuchElementException] if there is no
     * argument with the provided name.
     *
     * @param [name] The name of the [Argument] whose value is to be returned.
     * @param [json] The [Json] object that is to convert an argument's [Argument.element] field into the expected
     * value. Defaults to [Json.Default].
     *
     * @see [get] for a variation of this function that returns `null` instead of throwing a [NoSuchElementException]
     * when an argument with the provided name doesn't exist.
     * @see [Argument.value] for the conversion of the [Argument.element] to the expected value.
     */
    inline fun <reified T> getValue(name: String, json: Json = Json.Default): T =
        get(name = name, json = json) ?: throw NoSuchElementException("There was no argument with name '$name'.")

    companion object
}

/**
 * Converts this [Collection] of [Argument] values into an [Arguments] instance.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Collection<Argument>.toArguments(): Arguments =
    Arguments(arguments = this.toSet())

/**
 * Converts this [Map] of [String] [Argument.name] to [Argument] values into an [Arguments] instance.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Map<String, Argument>.toArguments(): Arguments =
    Arguments(arguments = this)

/**
 * Creates an empty [Arguments] instance.
 */
fun emptyArguments(): Arguments = EMPTY_ARGUMENTS

/**
 * Creates an [Arguments] instance with the provided [arguments] values.
 */
fun argumentsOf(vararg arguments: Argument): Arguments =
    if (arguments.isNotEmpty()) Arguments(*arguments) else emptyArguments()

/**
 * The constant empty arguments instance. This avoids having to instantiate a new [Arguments] instance everytime the
 * [emptyArguments] function is invoked.
 */
private val EMPTY_ARGUMENTS = Arguments()
