package com.chrynan.kapi.server.ksp.util

import com.chrynan.kapi.core.ApiError
import com.chrynan.kapi.core.HttpMethod
import com.chrynan.kapi.openapi.OAuthFlow
import com.chrynan.kapi.openapi.OAuthFlows
import com.chrynan.kapi.openapi.SecurityScheme
import com.chrynan.kapi.server.core.annotation.*
import com.chrynan.kapi.server.core.annotation.Principal
import com.chrynan.kapi.server.processor.core.model.*
import com.chrynan.kapi.server.processor.core.model.SecurityRequirement
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.isConstructor
import com.google.devtools.ksp.symbol.*
import io.ktor.http.*

internal val KSDeclaration.kotlinName: KotlinName
    get() = this.qualifiedName?.toKotlinName() ?: this.simpleName.toKotlinName()

internal val KSType.kotlinName: KotlinName
    get() = this.declaration.kotlinName

internal fun KSName.toKotlinName(): KotlinName =
    KotlinName(
        full = this.asString(),
        short = this.getShortName(),
        qualifier = this.getQualifier()
    )

internal fun Modifier.toKotlinTypeModifier(): KotlinTypeModifier? =
    when (this) {
        Modifier.PUBLIC -> KotlinTypeModifier.PUBLIC
        Modifier.PRIVATE -> KotlinTypeModifier.PRIVATE
        Modifier.PROTECTED -> KotlinTypeModifier.PROTECTED
        Modifier.INTERNAL -> KotlinTypeModifier.INTERNAL
        Modifier.EXPECT -> KotlinTypeModifier.EXPECT
        Modifier.ACTUAL -> KotlinTypeModifier.ACTUAL
        Modifier.ENUM -> KotlinTypeModifier.ENUM
        Modifier.SEALED -> KotlinTypeModifier.SEALED
        Modifier.ANNOTATION -> KotlinTypeModifier.ANNOTATION
        Modifier.DATA -> KotlinTypeModifier.DATA
        Modifier.INNER -> KotlinTypeModifier.INNER
        Modifier.FUN -> KotlinTypeModifier.FUN
        Modifier.VALUE -> KotlinTypeModifier.VALUE
        Modifier.ABSTRACT -> KotlinTypeModifier.ABSTRACT
        Modifier.FINAL -> KotlinTypeModifier.FINAL
        Modifier.OPEN -> KotlinTypeModifier.OPEN
        else -> null
    }

internal fun Modifier.toKotlinFunctionModifier(): KotlinFunctionModifier? =
    when (this) {
        Modifier.PUBLIC -> KotlinFunctionModifier.PUBLIC
        Modifier.PRIVATE -> KotlinFunctionModifier.PRIVATE
        Modifier.PROTECTED -> KotlinFunctionModifier.PROTECTED
        Modifier.INTERNAL -> KotlinFunctionModifier.INTERNAL
        Modifier.EXPECT -> KotlinFunctionModifier.EXPECT
        Modifier.ACTUAL -> KotlinFunctionModifier.ACTUAL
        Modifier.ABSTRACT -> KotlinFunctionModifier.ABSTRACT
        Modifier.FINAL -> KotlinFunctionModifier.FINAL
        Modifier.OPEN -> KotlinFunctionModifier.OPEN
        Modifier.OPERATOR -> KotlinFunctionModifier.OPERATOR
        Modifier.OVERRIDE -> KotlinFunctionModifier.OVERRIDE
        Modifier.INLINE -> KotlinFunctionModifier.INLINE
        Modifier.EXTERNAL -> KotlinFunctionModifier.EXTERNAL
        Modifier.SUSPEND -> KotlinFunctionModifier.SUSPEND
        Modifier.TAILREC -> KotlinFunctionModifier.TAILREC
        Modifier.INFIX -> KotlinFunctionModifier.INFIX
        else -> null
    }

@Suppress("unused")
internal fun Modifier.toKotlinParameterModifier(): KotlinParameterModifier? =
    when (this) {
        Modifier.VARARG -> KotlinParameterModifier.VARARG
        Modifier.NOINLINE -> KotlinParameterModifier.NOINLINE
        Modifier.CROSSINLINE -> KotlinParameterModifier.CROSSINLINE
        else -> null
    }

internal fun Modifier.toKotlinPropertyModifier(): KotlinPropertyModifier? =
    when (this) {
        Modifier.PUBLIC -> KotlinPropertyModifier.PUBLIC
        Modifier.PRIVATE -> KotlinPropertyModifier.PRIVATE
        Modifier.PROTECTED -> KotlinPropertyModifier.PROTECTED
        Modifier.INTERNAL -> KotlinPropertyModifier.INTERNAL
        Modifier.OVERRIDE -> KotlinPropertyModifier.OVERRIDE
        Modifier.LATEINIT -> KotlinPropertyModifier.LATEINIT
        Modifier.INLINE -> KotlinPropertyModifier.INLINE
        Modifier.EXTERNAL -> KotlinPropertyModifier.EXTERNAL
        Modifier.ABSTRACT -> KotlinPropertyModifier.ABSTRACT
        Modifier.FINAL -> KotlinPropertyModifier.FINAL
        Modifier.OPEN -> KotlinPropertyModifier.OPEN
        Modifier.CONST -> KotlinPropertyModifier.CONST
        Modifier.EXPECT -> KotlinPropertyModifier.EXPECT
        Modifier.ACTUAL -> KotlinPropertyModifier.ACTUAL
        else -> null
    }

internal fun KSPropertyDeclaration.toKotlinPropertyDeclaration(): KotlinPropertyDeclaration =
    KotlinPropertyDeclaration(
        name = this.qualifiedName?.toKotlinName() ?: this.simpleName.toKotlinName(),
        type = this.type.toKotlinTypeUsage(),
        annotations = this.annotations.map { it.toKotlinAnnotation() }.toList(),
        modifiers = this.modifiers.mapNotNull { it.toKotlinPropertyModifier() },
        documentation = this.docString,
        extensionReceiver = this.extensionReceiver?.toKotlinTypeUsage(),
        hasGetter = this.getter != null,
        hasSetter = this.setter != null,
        isMutable = this.isMutable,
        hasBackingField = this.hasBackingField,
        isDelegated = this.isDelegated()
    )

internal fun KSFunctionDeclaration.toKotlinFunctionDeclaration(): KotlinFunctionDeclaration =
    KotlinFunctionDeclaration(
        name = this.kotlinName,
        annotations = this.annotations.map { it.toKotlinAnnotation() }.toList(),
        modifiers = this.modifiers.mapNotNull { it.toKotlinFunctionModifier() },
        kind = this.functionKind.toKind(),
        extensionReceiver = this.extensionReceiver?.toKotlinTypeUsage(),
        returnType = this.returnType?.toKotlinTypeUsage(),
        parameters = this.parameters.map { it.toKotlinParameterDeclaration() },
        isConstructor = this.isConstructor(),
        documentation = this.docString,
        isSuspending = this.modifiers.contains(Modifier.SUSPEND)
    )

internal fun KSClassDeclaration.toKotlinTypeDefinition(): KotlinTypeDeclaration =
    KotlinTypeDeclaration(
        name = this.kotlinName,
        annotations = this.annotations.map { it.toKotlinAnnotation() }.toList(),
        kind = this.classKind.toKind(),
        documentation = this.docString,
        typeParameters = this.typeParameters.map { it.toKotlinTypeParameter() },
        modifiers = this.modifiers.mapNotNull { it.toKotlinTypeModifier() },
        superTypes = this.superTypes.map { it.toKotlinTypeUsage() }.toList(),
        properties = this.getAllProperties().map { it.toKotlinPropertyDeclaration() }.toList(),
        functions = this.getDeclaredFunctions().map { it.toKotlinFunctionDeclaration() }.toList()
    )

internal fun ClassKind.toKind(): KotlinTypeDeclaration.Kind =
    when (this) {
        ClassKind.INTERFACE -> KotlinTypeDeclaration.Kind.INTERFACE
        ClassKind.CLASS -> KotlinTypeDeclaration.Kind.CLASS
        ClassKind.ENUM_CLASS -> KotlinTypeDeclaration.Kind.ENUM_CLASS
        ClassKind.ENUM_ENTRY -> KotlinTypeDeclaration.Kind.ENUM_ENTRY
        ClassKind.OBJECT -> KotlinTypeDeclaration.Kind.OBJECT
        ClassKind.ANNOTATION_CLASS -> KotlinTypeDeclaration.Kind.ANNOTATION_CLASS
    }

internal fun FunctionKind.toKind(): KotlinFunctionDeclaration.Kind =
    when (this) {
        FunctionKind.MEMBER -> KotlinFunctionDeclaration.Kind.MEMBER
        FunctionKind.ANONYMOUS -> KotlinFunctionDeclaration.Kind.ANONYMOUS
        FunctionKind.LAMBDA -> KotlinFunctionDeclaration.Kind.LAMBDA
        FunctionKind.STATIC -> KotlinFunctionDeclaration.Kind.STATIC
        FunctionKind.TOP_LEVEL -> KotlinFunctionDeclaration.Kind.TOP_LEVEL
    }

internal fun Variance.toKotlinGenericVariance(): KotlinGenericVariance =
    when (this) {
        Variance.STAR -> KotlinGenericVariance.STAR
        Variance.CONTRAVARIANT -> KotlinGenericVariance.CONTRAVARIANT
        Variance.COVARIANT -> KotlinGenericVariance.COVARIANT
        Variance.INVARIANT -> KotlinGenericVariance.INVARIANT
    }

internal fun KSTypeArgument.toKotlinTypeArgument(): KotlinTypeArgument =
    KotlinTypeArgument(
        variance = this.variance.toKotlinGenericVariance(),
        type = this.type?.toKotlinTypeUsage()
    )

internal fun KSTypeParameter.toKotlinTypeParameter(): KotlinTypeParameter =
    KotlinTypeParameter(
        name = this.name.asString(),
        variance = this.variance.toKotlinGenericVariance(),
        isReified = this.isReified,
        bounds = this.bounds.map { it.toKotlinTypeUsage() }.toList()
    )

internal fun KSTypeReference.toKotlinTypeUsage(): KotlinTypeUsage {
    val type = this.resolve()

    return KotlinTypeUsage(
        name = type.kotlinName,
        typeArguments = this.element?.typeArguments?.map { it.toKotlinTypeArgument() }?.toList() ?: emptyList(),
        isNullable = type.isMarkedNullable,
        annotations = type.annotations.map { it.toKotlinAnnotation() }.toList()
    )
}

internal fun KSTypeReference.toKotlinResolvedTypeUsage(): KotlinTypeUsageWithDeclaration {
    val type = this.resolve()
    val usage = KotlinTypeUsage(
        name = type.kotlinName,
        typeArguments = this.element?.typeArguments?.map { it.toKotlinTypeArgument() }?.toList() ?: emptyList(),
        isNullable = type.isMarkedNullable,
        annotations = type.annotations.map { it.toKotlinAnnotation() }.toList()
    )
    val definition = (type.declaration as KSClassDeclaration).toKotlinTypeDefinition()
    val declaredGenericTypes =
        this.element?.typeArguments?.mapNotNull { (it.type?.resolve() as? KSClassDeclaration)?.toKotlinTypeDefinition() }
            ?: emptyList()

    return KotlinTypeUsageWithDeclaration(
        usage = usage,
        declaration = definition,
        declaredGenericTypes = declaredGenericTypes
    )
}

internal fun KSValueParameter.toKotlinParameterDeclaration(): KotlinParameterDeclaration =
    KotlinParameterDeclaration(
        name = this.name?.asString() ?: "",
        type = this.type.toKotlinTypeUsage(),
        isVararg = this.isVararg,
        isNoInline = this.isNoInline,
        isCrossInline = this.isCrossInline,
        isVal = this.isVal,
        isVar = this.isVar,
        hasDefaultValue = this.hasDefault,
        annotations = this.annotations.map { it.toKotlinAnnotation() }.toList(),
        modifiers = listOfNotNull(
            if (this.isVararg) KotlinParameterModifier.VARARG else null,
            if (this.isNoInline) KotlinParameterModifier.NOINLINE else null,
            if (this.isCrossInline) KotlinParameterModifier.CROSSINLINE else null
        )
    )

internal fun Auth.toApiAuth(): ApiAuth =
    ApiAuth(
        requirements = this.requirements.map {
            SecurityRequirement(
                name = it.name,
                scopes = it.scopes.toList(),
                scopeConcatenation = when (it.scopeRequirementType) {
                    Auth.RequirementType.ANY -> ApiAuth.RequirementConcatenation.OR
                    Auth.RequirementType.ALL -> ApiAuth.RequirementConcatenation.AND
                }
            )
        },
        concatenation = when (this.requirementType) {
            Auth.RequirementType.ANY -> ApiAuth.RequirementConcatenation.OR
            Auth.RequirementType.ALL -> ApiAuth.RequirementConcatenation.AND
        }
    )

@OptIn(KspExperimental::class)
internal fun KSClassDeclaration.toApiDefinition(
    contentTypesByAnnotationName: Map<String, String?>,
    authAnnotationsByName: Map<String, List<ApiAuth>>
): ApiDefinition {
    val api = this.getAnnotationsByType(Api::class).firstOrNull()
        ?: error(message = "API definition must be annotated with the Api annotation.", symbol = this)

    val appInfo = api.info.takeIf { !it.isEmpty }?.let { info ->
        ApiInfo(
            title = info.title,
            summary = info.summary.takeIf { it.isNotBlank() },
            termsOfService = info.termsOfService.takeIf { it.isNotBlank() },
            contact = ApiInfo.Contact(
                name = info.contact.name.takeIf { it.isNotBlank() },
                url = info.contact.url.takeIf { it.isNotBlank() },
                email = info.contact.email.takeIf { it.isNotBlank() }
            ).takeIf { !info.contact.isEmpty },
            license = ApiInfo.License(
                name = info.license.name,
                identifier = info.license.identifier.takeIf { it.isNotBlank() },
                url = info.license.url.takeIf { it.isNotBlank() }
            ).takeIf { !info.license.isEmpty }
        )
    }
    val servers = api.servers.map { server ->
        ApiServer(
            url = server.url,
            description = server.description.takeIf { it.isNotBlank() },
            variables = server.variables.map { variable ->
                ApiServer.Variable(
                    name = variable.name,
                    defaultValue = variable.defaultValue,
                    description = variable.description.takeIf { it.isNotBlank() },
                    allowableValues = variable.allowableValues.toList()
                )
            }
        )
    }
    val tags = api.tags.map { tag ->
        ApiTag(
            name = tag.name,
            description = tag.description.takeIf { it.isNotBlank() }
        )
    }
    val securitySchemes = api.securitySchemes.associate { scheme ->
        val oauthFlowMap = scheme.flows.associateBy { it.type }

        val oauthFlows = if (oauthFlowMap.isNotEmpty()) {
            OAuthFlows(
                implicit = oauthFlowMap[com.chrynan.kapi.server.core.annotation.OAuthFlow.Type.IMPLICIT]?.let { flow ->
                    OAuthFlow(
                        authorizationUrl = flow.authorizationUrl,
                        tokenUrl = flow.tokenUrl.takeIf { it.isNotBlank() },
                        refreshUrl = flow.refreshUrl,
                        scopes = flow.scopes.associate { it.name to it.description }
                    )
                },
                password = oauthFlowMap[com.chrynan.kapi.server.core.annotation.OAuthFlow.Type.PASSWORD]?.let { flow ->
                    OAuthFlow(
                        authorizationUrl = flow.authorizationUrl,
                        tokenUrl = flow.tokenUrl.takeIf { it.isNotBlank() },
                        refreshUrl = flow.refreshUrl,
                        scopes = flow.scopes.associate { it.name to it.description }
                    )
                },
                clientCredentials = oauthFlowMap[com.chrynan.kapi.server.core.annotation.OAuthFlow.Type.CLIENT_CREDENTIALS]?.let { flow ->
                    OAuthFlow(
                        authorizationUrl = flow.authorizationUrl,
                        tokenUrl = flow.tokenUrl.takeIf { it.isNotBlank() },
                        refreshUrl = flow.refreshUrl,
                        scopes = flow.scopes.associate { it.name to it.description }
                    )
                },
                authorizationCode = oauthFlowMap[com.chrynan.kapi.server.core.annotation.OAuthFlow.Type.AUTHORIZATION_CODE]?.let { flow ->
                    OAuthFlow(
                        authorizationUrl = flow.authorizationUrl,
                        tokenUrl = flow.tokenUrl.takeIf { it.isNotBlank() },
                        refreshUrl = flow.refreshUrl,
                        scopes = flow.scopes.associate { it.name to it.description }
                    )
                }
            )
        } else {
            null
        }

        scheme.name to SecurityScheme(
            type = scheme.type.openApiValue,
            description = scheme.description.takeIf { it.isNotBlank() },
            name = scheme.keyName.takeIf { it.isNotBlank() },
            inValue = scheme.keyLocation.openApiValue,
            scheme = scheme.scheme.takeIf { it.isNotBlank() },
            bearerFormat = scheme.bearerFormat.takeIf { it.isNotBlank() },
            openIdConnectUrl = scheme.openIdConnectUrl.takeIf { it.isNotBlank() },
            flows = oauthFlows
        )
    }

    val apiAuths = mutableListOf<ApiAuth>()
    this.annotations.associateBy { annotation ->
        annotation.annotationType.resolve().declaration.qualifiedName?.asString()
    }.forEach { (name, annotation) ->
        when {
            name == Auth::class.qualifiedName -> {
                apiAuths.add(annotation.toAnnotation(Auth::class).toApiAuth())
            }

            authAnnotationsByName.containsKey(name) -> {
                apiAuths.addAll(authAnnotationsByName[name] ?: emptyList())
            }
        }
    }
    validateAuthAnnotations(securitySchemes = securitySchemes, apiAuths = apiAuths, symbol = this)

    return ApiDefinition(
        name = api.name,
        basePath = api.basePath.takeIf { it.isNotBlank() },
        info = appInfo,
        servers = servers,
        tags = tags,
        type = this.toKotlinTypeDefinition(),
        documentation = this.docString,
        functions = this.getAllFunctions()
            .map {
                it.toApiFunction(
                    contentTypesByAnnotationName = contentTypesByAnnotationName,
                    authAnnotationsByName = authAnnotationsByName,
                    securitySchemes = securitySchemes
                )
            }
            .filterNotNull()
            .toList(),
        annotations = this.annotations.map { it.toKotlinAnnotation() }.toList(),
        securitySchemes = securitySchemes,
        auths = apiAuths
    )
}

internal fun KSFunctionDeclaration.toApiFunction(
    contentTypesByAnnotationName: Map<String, String?>,
    authAnnotationsByName: Map<String, List<ApiAuth>>,
    securitySchemes: Map<String, SecurityScheme>
): ApiFunction? {
    val functionName = this.kotlinName.full

    if (this.typeParameters.isNotEmpty()) {
        error(
            message = "API function $functionName cannot have generic type parameters as the API processor has no way of knowing which value to use when calling the function.",
            symbol = this
        )
    }

    var delete: DELETE? = null
    var get: GET? = null
    var head: HEAD? = null
    var options: OPTIONS? = null
    var patch: PATCH? = null
    var post: POST? = null
    var put: PUT? = null

    var produces: Produces? = null

    var isDeprecated = false

    var requestContentType: String? = null
    var contentTypeCount = 0

    val apiAuths = mutableListOf<ApiAuth>()

    // Get the annotation names. Not performant as it has to resolve the types for each annotation, but we need the
    // full name and this is the only way to get it. Note that duplicate annotations result in the usage of the last
    // one; that should be fine for our current use cases.
    val annotationsByNames = this.annotations.associateBy { annotation ->
        annotation.annotationType.resolve().declaration.qualifiedName?.asString()
    }

    annotationsByNames.forEach { (name, annotation) ->
        when {
            name == DELETE::class.qualifiedName -> delete = annotation.toAnnotation(DELETE::class)

            name == GET::class.qualifiedName -> get = annotation.toAnnotation(GET::class)

            name == HEAD::class.qualifiedName -> head = annotation.toAnnotation(HEAD::class)

            name == OPTIONS::class.qualifiedName -> options = annotation.toAnnotation(OPTIONS::class)

            name == PATCH::class.qualifiedName -> patch = annotation.toAnnotation(PATCH::class)

            name == POST::class.qualifiedName -> post = annotation.toAnnotation(POST::class)

            name == PUT::class.qualifiedName -> put = annotation.toAnnotation(PUT::class)

            name == Produces::class.qualifiedName -> produces = annotation.toAnnotation(Produces::class)

            name == Deprecated::class.qualifiedName -> isDeprecated = true

            name == Consumes::class.qualifiedName -> {
                requestContentType = annotation.toAnnotation(Consumes::class).contentType
                contentTypeCount++
            }

            name == ApplicationFormUrlEncoded::class.qualifiedName -> {
                requestContentType = annotation.toAnnotation(ApplicationFormUrlEncoded::class).contentType
                contentTypeCount++
            }

            name == MultipartFormData::class.qualifiedName -> {
                requestContentType = annotation.toAnnotation(MultipartFormData::class).contentType
                contentTypeCount++
            }

            name == ApplicationJson::class.qualifiedName -> {
                requestContentType = annotation.toAnnotation(ApplicationJson::class).contentType
                contentTypeCount++
            }

            contentTypesByAnnotationName.containsKey(name) -> {
                requestContentType = contentTypesByAnnotationName[name]
                contentTypeCount++
            }

            name == Auth::class.qualifiedName -> {
                apiAuths.add(annotation.toAnnotation(Auth::class).toApiAuth())
            }

            authAnnotationsByName.containsKey(name) -> {
                apiAuths.addAll(authAnnotationsByName[name] ?: emptyList())
            }
        }
    }

    check(value = listOfNotNull(delete, get, head, options, patch, post, put).size <= 1, symbol = this) {
        "Only one of the following annotations is allowed for each API function: DELETE, GET, HEAD, OPTIONS, PATCH, POST, PUT. Function: $functionName"
    }

    check(value = contentTypeCount <= 1, symbol = this) {
        "Only one Consumes annotation is allowed for an API function. Function: $functionName"
    }

    validateAuthAnnotations(securitySchemes = securitySchemes, apiAuths = apiAuths, symbol = this)

    val httpMethod = when {
        delete != null -> HttpMethod.DELETE
        get != null -> HttpMethod.GET
        head != null -> HttpMethod.HEAD
        options != null -> HttpMethod.OPTIONS
        patch != null -> HttpMethod.PATCH
        post != null -> HttpMethod.POST
        put != null -> HttpMethod.PUT
        else -> null
    } ?: return null
    val path =
        (delete?.path ?: get?.path ?: head?.path ?: options?.path ?: patch?.path ?: post?.path ?: put?.path)
            ?: return null
    val parameters = this.parameters.map { it.toApiParameter(functionName = functionName) }
    val successResponse = produces?.success?.let { success ->
        ApiResponse.Success(
            statusCode = success.statusCode,
            description = success.description,
            headers = success.headers.map { it.toResponseHeader() },
            contentType = success.contentType
        )
    }
    val errorResponses = produces?.errors?.map { error -> error.toErrorResponse() }

    val apiFunction = ApiFunction(
        kotlinFunction = this.toKotlinFunctionDeclaration(),
        method = httpMethod,
        path = path,
        requestContentType = requestContentType,
        successResponse = successResponse,
        extensionReceiver = this.extensionReceiver?.toKotlinTypeUsage(),
        parameters = parameters,
        errorResponses = errorResponses ?: emptyList(),
        isDeprecated = isDeprecated,
        auths = apiAuths
    )

    validateApiFunction(function = apiFunction, symbol = this)

    return apiFunction
}

/**
 * Converts this [Header] annotation into a [ApiResponseHeader] model.
 */
internal fun ResponseHeader.toResponseHeader(): ApiResponseHeader =
    ApiResponseHeader(
        name = this.name,
        value = this.value,
        safeOnly = this.safeOnly,
        onlyIfAbsent = this.onlyIfAbsent
    )

internal fun Error<*>.toErrorResponse(): ApiResponse.Error =
    ApiResponse.Error(
        statusCode = this.statusCode,
        description = this.details.takeIf { it.isNotBlank() } ?: "${this.statusCode} ${this.title}",
        headers = this.headers.map { it.toResponseHeader() },
        exception = KotlinTypeUsage(
            name = KotlinName(
                full = this.exception.qualifiedName ?: this.exception.simpleName!!
            )
        ),
        value = ApiError(
            type = this.type,
            title = this.title,
            details = this.details.takeIf { it.isNotBlank() },
            status = this.statusCode,
            instance = this.instance.takeIf { it.isNotBlank() },
            timestamp = null,
            help = this.help.takeIf { it.isNotBlank() },
            signature = null
        )
    )

/**
 * Converts this [KSValueParameter] to an [ApiParameter] model.
 */
@OptIn(KspExperimental::class)
internal fun KSValueParameter.toApiParameter(functionName: String): ApiParameter {
    val path = this.getAnnotationsByType(Path::class).firstOrNull()
    val query = this.getAnnotationsByType(Query::class).firstOrNull()
    val field = this.getAnnotationsByType(Field::class).firstOrNull()
    val part = this.getAnnotationsByType(Part::class).firstOrNull()
    val header = this.getAnnotationsByType(Header::class).firstOrNull()
    val body = this.getAnnotationsByType(Body::class).firstOrNull()
    val principal = this.getAnnotationsByType(Principal::class).firstOrNull()
    val isDeprecated = this.getAnnotationsByType(Deprecated::class).firstOrNull() != null

    check(value = listOfNotNull(path, query, field, part, header, body).size <= 1, symbol = this) {
        "Only one of the following annotations is allowed for each API function parameter: 'Path', 'Query', 'Field', 'Part', 'Header', and 'Body'. Function: $functionName; Parameter: ${this.name?.asString()}"
    }

    val parameterDeclaration = this.toKotlinParameterDeclaration()

    return when {
        path != null -> PathParameter(
            declaration = parameterDeclaration,
            value = path.value.takeIf { it.isNotBlank() } ?: parameterDeclaration.name,
            isDeprecated = isDeprecated
        )

        query != null -> QueryParameter(
            declaration = parameterDeclaration,
            value = query.value.takeIf { it.isNotBlank() } ?: parameterDeclaration.name,
            isDeprecated = isDeprecated
        )

        field != null -> FieldParameter(
            declaration = parameterDeclaration,
            value = field.value.takeIf { it.isNotBlank() } ?: parameterDeclaration.name,
            isDeprecated = isDeprecated
        )

        part != null -> PartParameter(
            declaration = parameterDeclaration,
            value = part.value.takeIf { it.isNotBlank() } ?: parameterDeclaration.name,
            encoding = part.encoding,
            isDeprecated = isDeprecated
        )

        header != null -> HeaderParameter(
            declaration = parameterDeclaration,
            value = header.value.takeIf { it.isNotBlank() } ?: parameterDeclaration.name,
            isDeprecated = isDeprecated
        )

        body != null -> BodyParameter(
            declaration = parameterDeclaration,
            kotlinType = this.type.toKotlinResolvedTypeUsage(),
            isDeprecated = isDeprecated
        )

        principal != null -> PrincipalParameter(
            declaration = parameterDeclaration,
            value = principal.value.takeIf { it.isNotBlank() } ?: parameterDeclaration.name,
            isDeprecated = isDeprecated
        )

        this.hasDefault -> DefaultValueParameter(declaration = parameterDeclaration, isDeprecated = isDeprecated)

        else -> {
            val type = parameterDeclaration.type

            when {
                type.isRoute || type.isApplicationCall || type.isUnit || type.isParameters || type.isMultiPartData ->
                    SupportedTypeParameter(declaration = parameterDeclaration, isDeprecated = isDeprecated)

                else -> error(message = "Unsupported API function parameter type.", symbol = this)
            }
        }
    }
}

private fun validateAuthAnnotations(
    securitySchemes: Map<String, SecurityScheme>,
    apiAuths: List<ApiAuth>,
    symbol: KSNode?
) {
    val symbolName = when (symbol) {
        is KSClassDeclaration -> "Class: ${symbol.kotlinName.full}"
        is KSFunctionDeclaration -> "Function: ${symbol.kotlinName.full}"
        else -> "Component: location: ${symbol?.location}"
    }

    apiAuths.forEach { auth ->
        auth.requirements.forEach { requirement ->
            if (requirement.name.isBlank()) {
                error(
                    message = "Cannot apply security requirement with blank name. $symbolName",
                    symbol = symbol
                )
            } else if (!securitySchemes.containsKey(requirement.name)) {
                error(
                    message = "Cannot apply security requirement with name `${requirement.name}` to API without a corresponding SecurityScheme defined for that requirement. Add the `SecurityScheme` value to the `securitySchemes` property of the `@Api` annotation. $symbolName",
                    symbol = symbol
                )
            }
        }
    }
}

private fun validateApiFunction(function: ApiFunction, symbol: KSNode?) {
    val functionName = function.kotlinFunction.name.full
    val requestContentType = function.requestContentType

    if (
        function.bodyParameterOrNull() != null &&
        ContentType.Application.FormUrlEncoded.matches(requestContentType) &&
        !function.bodyParameter().declaration.type.isParameters
    ) {
        error(
            message = "API function Body parameter type must be `io.ktor.http.Parameters` if the content type is `application/x-www-form-urlencoded`. Function: $functionName",
            symbol = symbol
        )
    } else if (
        function.bodyParameterOrNull() != null &&
        ContentType.MultiPart.FormData.matches(requestContentType) &&
        !function.bodyParameter().declaration.type.isMultiPartData
    ) {
        error(
            message = "API function Body parameter type must be `io.ktor.http.content.MultiPartData` if the content type is `multipart/form-data`. Function: $functionName",
            symbol = symbol
        )
    } else if (function.partParameters.isNotEmpty() && !ContentType.MultiPart.FormData.matches(requestContentType)) {
        error(
            message = "API function containing a Part parameter must be setup to consume the content type of `multipart/form-data`. Function: $functionName",
            symbol = symbol
        )
    } else if (function.fieldParameters.isNotEmpty() && function.partParameters.isNotEmpty()) {
        error(
            message = "API function cannot mix Field and Part parameters. Function: $functionName",
            symbol = symbol
        )
    } else if (function.fieldParameters.isNotEmpty() && function.bodyParameterOrNull() != null) {
        error(
            message = "API function cannot mix Field and Body parameters. Function: $functionName",
            symbol = symbol
        )
    } else if (function.partParameters.isNotEmpty() && function.bodyParameterOrNull() != null) {
        error(
            message = "API function cannot mix Part and Body parameters. Function: $functionName",
            symbol = symbol
        )
    }
}
