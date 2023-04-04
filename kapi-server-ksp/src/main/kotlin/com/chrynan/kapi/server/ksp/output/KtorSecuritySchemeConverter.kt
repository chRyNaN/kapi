@file:OptIn(ExperimentalServerApi::class)

package com.chrynan.kapi.server.ksp.output

import com.chrynan.kapi.openapi.OAuthFlow
import com.chrynan.kapi.openapi.OAuthFlows
import com.chrynan.kapi.openapi.SecurityScheme
import com.chrynan.kapi.server.core.annotation.ExperimentalServerApi
import com.chrynan.kapi.server.core.auth.ApiSecurityDefinitionProvider
import com.chrynan.kapi.server.core.auth.KapiSecurity
import com.chrynan.kapi.server.core.auth.SecurityDefinition
import com.chrynan.kapi.server.ksp.util.apiName
import com.chrynan.kapi.server.ksp.util.simpleName
import com.chrynan.kapi.server.processor.core.model.ApiDefinition
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import kotlin.reflect.KCallable

internal class KtorSecuritySchemeConverter {

    operator fun invoke(definition: ApiDefinition): FileSpec {
        val providerTypeSpec = definition.toSecurityDefinitionProviderTypeSpec()

        return FileSpec.builder(
            packageName = definition.type.name.packageName!!,
            fileName = providerTypeSpec.name ?: definition.apiName
        )
            .addAnnotation(
                AnnotationSpec.builder(ClassName.bestGuess("kotlin.OptIn"))
                    .addMember(
                        "%T::class",
                        ClassName.bestGuess("com.chrynan.kapi.server.core.annotation.ExperimentalServerApi")
                    )
                    .build()
            )
            .addType(providerTypeSpec)
            .addProperty(definition.toKapiSecurityExtensionPropertySpec())
            .build()
    }

    private fun ApiDefinition.toKapiSecurityExtensionPropertySpec(): PropertySpec =
        PropertySpec.builder(
            name = this.apiName.lowercaseFirstChar(),
            type = ClassName(this.type.name.packageName!!, this.apiSecurityDefinitionProviderClassName),
            modifiers = arrayOf(
                KModifier.PUBLIC
            )
        ).mutable(false)
            .receiver(KapiSecurity::class)
            .getter(
                FunSpec.getterBuilder()
                    .addCode("return %L()", this.apiSecurityDefinitionProviderClassName)
                    .build()
            )
            .build()

    private fun ApiDefinition.toSecurityDefinitionProviderTypeSpec(): TypeSpec {
        val className = this.apiSecurityDefinitionProviderClassName

        val builder = TypeSpec.classBuilder(name = className)
            .addModifiers(KModifier.PUBLIC)
            .addSuperinterface(ApiSecurityDefinitionProvider::class)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addModifiers(KModifier.INTERNAL)
                    .build()
            )
            .addProperty(
                PropertySpec.builder(
                    name = ApiSecurityDefinitionProvider::apiName.simpleName,
                    type = String::class,
                    modifiers = arrayOf(
                        KModifier.PUBLIC,
                        KModifier.OVERRIDE
                    )
                ).mutable(false)
                    .getter(
                        FunSpec.getterBuilder()
                            .addCode("return %S", this.apiName)
                            .build()
                    )
                    .build()
            )

        val securitySchemeProperties = this.securitySchemes
            .map { (provider, scheme) -> SecurityDefinition(provider = provider, scheme = scheme) }
            .map { definition ->
                val propertyName = definition.providerDefinitionPropertyName()

                PropertySpec.builder(
                    name = propertyName,
                    type = SecurityDefinition::class,
                    modifiers = arrayOf(KModifier.PUBLIC)
                ).mutable(false)
                    .getter(
                        FunSpec.getterBuilder()
                            .addCode("return %L", definition.toInstantiationCodeBlock())
                            .build()
                    )
                    .build()
            }

        builder.addProperties(securitySchemeProperties)

        val definitionsPropertyGetterBuilder = FunSpec.getterBuilder()
            .addCode(
                "return %M(",
                MemberName(packageName = "kotlin.collections", simpleName = "listOf", isExtension = false)
            )

        securitySchemeProperties.forEachIndexed { index, schemeProperty ->
            definitionsPropertyGetterBuilder.addCode("\n%N", schemeProperty)

            if (index != securitySchemeProperties.lastIndex) {
                definitionsPropertyGetterBuilder.addCode(",")
            }
        }

        definitionsPropertyGetterBuilder.addCode(")")

        builder.addProperty(
            PropertySpec.builder(
                name = ApiSecurityDefinitionProvider::definitions.simpleName,
                type = LIST.parameterizedBy(SecurityDefinition::class.asTypeName()),
                modifiers = arrayOf(
                    KModifier.PUBLIC,
                    KModifier.OVERRIDE
                )
            ).mutable(false)
                .getter(definitionsPropertyGetterBuilder.build())
                .build()
        )

        return builder.build()
    }

    private fun SecurityDefinition.toInstantiationCodeBlock(): CodeBlock {
        val builder = CodeBlock.builder()

        builder.add(
            """
            |%T(
            |  %L = %S,
            |  %L = %L
            |)
            """.trimMargin(),
            SecurityDefinition::class.asTypeName(),
            SecurityDefinition::provider.simpleName,
            this.provider,
            SecurityDefinition::scheme.simpleName,
            this.scheme.toInstantiationCodeBlock()
        )

        return builder.build()
    }

    private fun SecurityScheme.toInstantiationCodeBlock(): CodeBlock {
        val builder = CodeBlock.builder()

        builder.add("%T(\n", SecurityScheme::class.asTypeName())
        builder.indent()

        builder.add(
            """
            |%L = %S,
            |%L = %L,
            |%L = %L,
            |%L = %L,
            |%L = %L,
            |%L = %L,
            |%L = %L,
            """.trimMargin(),
            SecurityScheme::type.simpleName,
            this.type,
            SecurityScheme::description.simpleName,
            this.description?.let { "\"$it\"" },
            SecurityScheme::name.simpleName,
            this.name?.let { "\"$it\"" },
            SecurityScheme::inValue.simpleName,
            this.inValue?.let { "\"$it\"" },
            SecurityScheme::scheme.simpleName,
            this.scheme?.let { "\"$it\"" },
            SecurityScheme::bearerFormat.simpleName,
            this.bearerFormat?.let { "\"$it\"" },
            SecurityScheme::openIdConnectUrl.simpleName,
            this.openIdConnectUrl?.let { "\"$it\"" }
        )

        this.flows?.let {
            builder.indent()
            builder.add("\n%L = ", SecurityScheme::flows.simpleName)
            builder.add(it.toInstantiationCodeBlock())
            builder.unindent()
        }

        builder.unindent()
        builder.add(")")

        return builder.build()
    }

    private fun OAuthFlows.toInstantiationCodeBlock(): CodeBlock {
        val builder = CodeBlock.builder()

        builder.add("%T(", OAuthFlows::class.asTypeName())
        builder.indent()

        val flows = listOf(
            OAuthFlows::implicit.simpleName to this.implicit,
            OAuthFlows::password.simpleName to this.password,
            OAuthFlows::clientCredentials.simpleName to this.clientCredentials,
            OAuthFlows::authorizationCode.simpleName to this.authorizationCode
        ).filter { (_, flow) -> flow != null }

        flows.forEachIndexed { index, (propertyName, flow) ->
            builder.add("\n%L = ", propertyName)
            builder.add(flow!!.toInstantiationCodeBlock())

            if (index != flows.lastIndex) {
                builder.add(",")
            }
        }

        builder.unindent()
        builder.add("\n)")

        return builder.build()
    }

    private fun OAuthFlow.toInstantiationCodeBlock(): CodeBlock {
        val builder = CodeBlock.builder()

        builder.addStatement(
            """
                |%T(
                |  %L = %S,
                |  %L = %L,
                |  %L = %S%L
                """.trimMargin(),
            OAuthFlow::class.asTypeName(),
            OAuthFlow::authorizationUrl.simpleName,
            this.authorizationUrl,
            OAuthFlow::tokenUrl.simpleName,
            this.tokenUrl?.let { "\"$it\"" },
            OAuthFlow::refreshUrl.simpleName,
            this.refreshUrl,
            if (this.scopes.isEmpty()) "" else ","
        )

        if (this.scopes.isNotEmpty()) {
            builder.add(
                "%L = %M(",
                OAuthFlow::scopes.simpleName,
                MemberName(packageName = "kotlin.collections", simpleName = "mapOf", isExtension = false)
            )
            builder.indent()
        }

        this.scopes.entries.forEachIndexed { index, (name, description) ->
            builder.add(
                "\n%S %M %S%L",
                name,
                MemberName(packageName = "kotlin", simpleName = "to", isExtension = true),
                description,
                if (index == this.scopes.size - 1) "" else ","
            )
        }

        if (this.scopes.isNotEmpty()) {
            builder.unindent()
            builder.add(")")
        }

        builder.add("\n)")

        return builder.build()
    }

    private fun String.lowercaseFirstChar(): String =
        this.replaceFirstChar { it.lowercase() }

    private fun String.asDefinitionPropertyName(): String =
        "${this}${suffixSecurityDefinitionProperty}".lowercaseFirstChar()

    private fun SecurityDefinition.providerDefinitionPropertyName(): String =
        provider.asDefinitionPropertyName()

    companion object {

        internal const val suffixApiSecurityDefinitionProviderClass = "SecurityDefinitionProvider"
        private const val suffixSecurityDefinitionProperty = "Definition"
    }
}

internal val ApiDefinition.apiSecurityDefinitionProviderClassName: String
    get() = "${this.apiName}${KtorSecuritySchemeConverter.suffixApiSecurityDefinitionProviderClass}"
