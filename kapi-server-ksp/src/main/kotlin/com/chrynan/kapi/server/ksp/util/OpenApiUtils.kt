@file:Suppress("unused")

package com.chrynan.kapi.server.ksp.util

import com.chrynan.kapi.server.core.annotation.ExperimentalServerApi
import com.chrynan.kapi.server.core.annotation.Query
import com.chrynan.kapi.server.core.util.ParameterAnnotationType
import com.chrynan.kapi.server.processor.core.model.*

/**
 * A component that registers Kotlin types used within an API so that they can be named appropriately in the generated
 * Open API objects. Not all Open API generators support fully qualified Kotlin names
 * (ex: com.chrynan.kapi.server.core.Example) for the name of the schemas within an Open API document. Also, fully
 * qualified Kotlin names might make sense when used in Kotlin, but they don't make sense for other languages
 * (ex: kotlin.String vs String). And the Open API Specification has default types for primitive values. So, this
 * component is used to register all the types encountered within an API, then obtain the recommended names for any of
 * those registered types for use within the generated Open API document.
 */
@ExperimentalServerApi
internal class OpenApiTypeRegistrar {

    private val registeredTypeNamesByShortName = mutableMapOf<String, Set<KotlinName>>()
    private val registeredTypeByShortName = mutableMapOf<String, Set<RegisteredType>>()

    val allRegisteredTypes: Set<RegisteredTypeWithName>
        get() = registeredTypeByShortName.values
            .flatten()
            .map {
                RegisteredTypeWithName(
                    name = getRecommendedName(type = it.type, location = it.location),
                    kotlinName = it.type.name
                )
            }
            .distinctBy { it.name }
            .toSet()

    fun register(
        type: KotlinTypeUsage,
        location: TypeUsageLocation
    ) {
        val typeToRegister = type.toRegisterSafeType()

        if (typeToRegister != null) {
            val registeredType = RegisteredType(type = typeToRegister, location = location)

            val typeNamesByShortName = registeredTypeNamesByShortName[typeToRegister.name.short] ?: emptySet()
            registeredTypeNamesByShortName[typeToRegister.name.short] = typeNamesByShortName + typeToRegister.name

            val typeUsagesByShortName = registeredTypeByShortName[typeToRegister.name.short] ?: emptySet()
            registeredTypeByShortName[typeToRegister.name.short] = typeUsagesByShortName + registeredType
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun getRecommendedName(
        type: KotlinTypeUsage,
        location: TypeUsageLocation
    ): String {
        val safeType = type.toRegisterSafeType() ?: error("Type `${type.name.full}` cannot be registered.")

        val typeNamesByShortName = registeredTypeNamesByShortName[safeType.name.short] ?: emptyList()

        if (typeNamesByShortName.isEmpty()) {
            error("Type `${safeType.name.full}` was not registered.")
        }

        if (typeNamesByShortName.size == 1) {
            return safeType.name.short
        }

        val titleCaseFunctionName = location.functionName.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        }

        return when (location) {
            is TypeUsageLocation.Parameter -> {
                val titleCaseParameterName = location.parameterName.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase() else it.toString()
                }

                "$titleCaseFunctionName$PARAMETER$titleCaseParameterName"
            }

            is TypeUsageLocation.ReturnType -> "$titleCaseFunctionName$SUFFIX_RETURN_TYPE"
        }
    }

    fun clear() {
        registeredTypeByShortName.clear()
        registeredTypeNamesByShortName.clear()
    }

    private fun KotlinTypeUsage.toRegisterSafeType(): KotlinTypeUsage? =
        when {
            this.isUnit -> null
            this.isApplicationCall -> null
            this.isRoute -> null
            this.isResponse -> this.typeArguments.first().type // FIXME: What about subtypes of Response?
            else -> this
        }

    companion object {

        private const val PARAMETER = "Parameter"
        private const val SUFFIX_RETURN_TYPE = "ReturnType"
    }

    sealed class TypeUsageLocation private constructor() {

        abstract val functionName: String

        data class Parameter internal constructor(
            override val functionName: String,
            val parameterName: String,
            val annotationType: ParameterAnnotationType
        ) : TypeUsageLocation()

        data class ReturnType internal constructor(
            override val functionName: String
        ) : TypeUsageLocation()
    }

    data class RegisteredTypeWithName(
        val name: String,
        val kotlinName: KotlinName
    )

    private data class RegisteredType(
        val type: KotlinTypeUsage,
        val location: TypeUsageLocation
    )
}

@ExperimentalServerApi
internal fun OpenApiTypeRegistrar.registerApi(api: ApiDefinition) {
    api.functions.forEach { function ->
        val functionName = function.kotlinFunction.name.full

        function.kotlinFunction.returnType?.let {
            register(
                type = it,
                location = OpenApiTypeRegistrar.TypeUsageLocation.ReturnType(functionName = functionName)
            )
        }

        function.parameters
            .filter { parameter -> parameter.annotationType != null }
            .forEach { parameter ->
                register(
                    type = parameter.declaration.type,
                    location = OpenApiTypeRegistrar.TypeUsageLocation.Parameter(
                        functionName = functionName,
                        parameterName = parameter.declaration.name,
                        annotationType = parameter.annotationType!!
                    )
                )
            }
    }
}

/**
 * A component that handles "formatting" a type to a type that can be used within an Open API document. Certain types
 * are not registered, for instance [kotlin.Unit], other types have to perform special logic to extract the actual
 * type, for instance the type [com.chrynan.kapi.core.Response] wraps a generic type that is the real response body
 * type, this component handles properly returning the appropriate [KotlinTypeUsage] or `null` if the type isn't useful
 * for registration.
 */
internal class OpenApiTypeFormatter {

    /**
     * Takes the provided [type] and returns a [KotlinTypeUsage] that should be registered in an Open Api document or
     * `null` if the type should not be registered.
     */
    operator fun invoke(type: KotlinTypeUsage): KotlinTypeUsage? = when {
        type.isUnit -> null
        type.isNothing -> null
        type.isApplicationCall -> null
        type.isRoute -> null
        (type.isResponse && type.typeArguments.first().type?.isUnit == true) -> null
        (type.isResponse && type.typeArguments.first().type?.isNothing == true) -> null
        type.isResponse -> type.typeArguments.first().type // FIXME: What about subtypes of Response?
        else -> type
    }
}

internal fun OpenApiTypeFormatter.invoke(parameter: ApiParameter): KotlinTypeUsage? =
    when (parameter) {
        is PathParameter,
        is QueryParameter,
        is HeaderParameter -> invoke(parameter.declaration.type)

        else -> null
    }
