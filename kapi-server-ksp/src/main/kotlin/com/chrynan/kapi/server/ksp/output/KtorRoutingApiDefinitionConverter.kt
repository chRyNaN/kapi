package com.chrynan.kapi.server.ksp.output

import com.chrynan.kapi.server.core.annotation.ExperimentalServerApi
import com.chrynan.kapi.server.core.util.HttpRequestParameterExtractor
import com.chrynan.kapi.server.ksp.util.apiName
import com.chrynan.kapi.server.processor.core.model.ApiDefinition
import com.squareup.kotlinpoet.*

@OptIn(ExperimentalServerApi::class)
internal class KtorRoutingApiDefinitionConverter(
    private val propertyNameApi: String,
    private val propertyNameParameterExtractorFactory: String,
    private val functionConverter: KtorRoutingApiFunctionConverter
) {

    operator fun invoke(definition: ApiDefinition): FileSpec {
        val apiTypeName = ClassName.bestGuess(definition.type.name.full)

        val bindingClassName = "${definition.apiName}$suffixKtorBindingClass"

        val bindingClass = definition.toBindingClass(apiTypeName = apiTypeName, bindingClassName = bindingClassName)

        val registerApiFunction = FunSpec.builder(name = "register${definition.apiName}")
            .addModifiers(KModifier.PUBLIC)
            .receiver(ClassName.bestGuess("io.ktor.server.routing.Routing"))
            .addParameter(name = propertyNameApi, type = apiTypeName)
            .addParameter(
                ParameterSpec.builder(
                    name = propertyNameParameterExtractorFactory,
                    type = HttpRequestParameterExtractor.Factory::class.asTypeName()
                ).defaultValue(
                    "%M()",
                    MemberName(
                        packageName = "com.chrynan.kapi.server.core.util",
                        simpleName = "defaultHttpRequestParameterExtractorFactory",
                        isExtension = false
                    )
                ).build()
            )
            .addCode(
                CodeBlock.builder()
                    .addStatement(
                        "val binding = %N($propertyNameApi = $propertyNameApi, $propertyNameParameterExtractorFactory = $propertyNameParameterExtractorFactory)",
                        bindingClass
                    )
                    .addStatement("binding.$functionNameRegisterAllEndpointsForApi($parameterNameRouting = this)")
                    .build()
            )
            .build()

        return FileSpec.builder(packageName = definition.type.name.packageName!!, fileName = bindingClassName)
            .addAnnotation(
                AnnotationSpec.builder(ClassName.bestGuess("kotlin.OptIn"))
                    .addMember(
                        "%T::class",
                        ClassName.bestGuess("com.chrynan.kapi.server.core.annotation.ExperimentalServerApi")
                    )
                    .build()
            )
            .addType(bindingClass)
            .addFunction(registerApiFunction)
            .build()
    }

    private fun ApiDefinition.toBindingClass(apiTypeName: ClassName, bindingClassName: String): TypeSpec {
        val bindingFunctions = this.functions.map {
            functionConverter.invoke(
                function = it,
                basePath = this.basePath,
                parentAuths = this.auths
            )
        }

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
                PropertySpec.builder(name = propertyNameApi, type = apiTypeName)
                    .initializer(propertyNameApi)
                    .addModifiers(KModifier.PRIVATE)
                    .build()
            )
            .addProperty(
                PropertySpec.builder(
                    name = propertyNameParameterExtractorFactory,
                    type = HttpRequestParameterExtractor.Factory::class.asTypeName()
                ).initializer(propertyNameParameterExtractorFactory)
                    .addModifiers(KModifier.PRIVATE)
                    .build()
            )
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter(name = propertyNameApi, type = apiTypeName)
                    .addParameter(
                        name = propertyNameParameterExtractorFactory,
                        type = HttpRequestParameterExtractor.Factory::class.asTypeName()
                    )
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
