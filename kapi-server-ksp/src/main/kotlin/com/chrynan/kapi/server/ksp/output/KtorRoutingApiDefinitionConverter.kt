package com.chrynan.kapi.server.ksp.output

import com.chrynan.kapi.server.ksp.util.apiName
import com.chrynan.kapi.server.processor.core.model.ApiDefinition
import com.squareup.kotlinpoet.*

internal class KtorRoutingApiDefinitionConverter(
    private val classPropertyNameApi: String,
    private val functionConverter: KtorRoutingApiFunctionConverter
) {

    operator fun invoke(definition: ApiDefinition): FileSpec {
        val apiTypeName = ClassName.bestGuess(definition.type.name.full)

        val bindingClassName = "${definition.apiName}$suffixKtorBindingClass"

        val bindingClass = definition.toBindingClass(apiTypeName = apiTypeName, bindingClassName = bindingClassName)

        val registerApiFunction = FunSpec.builder(name = "register${definition.apiName}")
            .addModifiers(KModifier.PUBLIC)
            .receiver(ClassName.bestGuess("io.ktor.server.routing.Routing"))
            .addParameter(name = classPropertyNameApi, type = apiTypeName)
            .addCode(
                CodeBlock.builder()
                    .addStatement("val binding = %N($classPropertyNameApi = $classPropertyNameApi)", bindingClass)
                    .addStatement("binding.$functionNameRegisterAllEndpointsForApi($parameterNameRouting = this)")
                    .build()
            )
            .build()

        return FileSpec.builder(packageName = definition.type.name.packageName!!, fileName = bindingClassName)
            .addType(bindingClass)
            .addFunction(registerApiFunction)
            .build()
    }

    private fun ApiDefinition.toBindingClass(apiTypeName: ClassName, bindingClassName: String): TypeSpec {
        val bindingFunctions = this.functions.map { functionConverter.invoke(function = it, basePath = this.basePath) }

        val registerAllFunctionBuilder = FunSpec.builder(name = functionNameRegisterAllEndpointsForApi)
            .addModifiers(KModifier.INTERNAL)
            .addParameter(name = parameterNameRouting, type = ClassName.bestGuess("io.ktor.server.routing.Routing"))
            .beginControlFlow(
                "${parameterNameRouting}.%M",
                MemberName(packageName = "kotlin", simpleName = "apply", isExtension = true)
            )

        bindingFunctions.forEach { function ->
            registerAllFunctionBuilder.addStatement("%N()", function)
        }

        registerAllFunctionBuilder.endControlFlow()

        return TypeSpec.classBuilder(name = bindingClassName)
            .addModifiers(KModifier.PRIVATE)
            .addProperty(
                PropertySpec.builder(name = classPropertyNameApi, type = apiTypeName)
                    .initializer(classPropertyNameApi)
                    .addModifiers(KModifier.PRIVATE)
                    .build()
            )
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter(name = classPropertyNameApi, type = apiTypeName)
                    .build()
            )
            .addFunctions(bindingFunctions)
            .addFunction(registerAllFunctionBuilder.build())
            .build()
    }

    companion object {

        internal const val suffixKtorBindingClass = "Routing"
        private const val functionNameRegisterAllEndpointsForApi = "registerAllEndpoints"
        private const val parameterNameRouting = "routing"
    }
}
