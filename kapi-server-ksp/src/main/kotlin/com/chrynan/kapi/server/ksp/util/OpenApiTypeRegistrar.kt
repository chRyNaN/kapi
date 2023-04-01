@file:Suppress("unused")

package com.chrynan.kapi.server.ksp.util

import com.chrynan.kapi.server.core.annotation.ExperimentalServerApi
import com.chrynan.kapi.server.core.util.ParameterAnnotationType
import com.chrynan.kapi.server.processor.core.model.KotlinName
import com.chrynan.kapi.server.processor.core.model.KotlinTypeUsage
import com.chrynan.kapi.server.processor.core.model.KotlinTyped

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
        val registeredType = RegisteredType(type = type, location = location)

        val typeNamesByShortName = registeredTypeNamesByShortName[type.name.short] ?: emptySet()
        registeredTypeNamesByShortName[type.name.short] = typeNamesByShortName + type.name

        val typeUsagesByShortName = registeredTypeByShortName[type.name.short] ?: emptySet()
        registeredTypeByShortName[type.name.short] = typeUsagesByShortName + registeredType
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun getRecommendedName(
        type: KotlinTyped,
        location: TypeUsageLocation
    ): String {
        val typeNamesByShortName = registeredTypeNamesByShortName[type.name.short] ?: emptyList()

        if (typeNamesByShortName.isEmpty()) {
            error("Type `${type.name.full}` was not registered.")
        }

        if (typeNamesByShortName.size == 1) {
            return type.name.short
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
