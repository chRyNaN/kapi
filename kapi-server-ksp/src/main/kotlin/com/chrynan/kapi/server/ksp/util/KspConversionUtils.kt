package com.chrynan.kapi.server.ksp.util

import com.chrynan.kapi.core.ApiError
import com.chrynan.kapi.core.HttpMethod
import com.chrynan.kapi.server.core.annotation.*
import com.chrynan.kapi.server.core.annotation.method.*
import com.chrynan.kapi.server.core.annotation.parameter.*
import com.chrynan.kapi.server.processor.core.model.*
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.isConstructor
import com.google.devtools.ksp.symbol.*

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

@OptIn(KspExperimental::class)
internal fun KSClassDeclaration.toApiDefinition(): ApiDefinition {
    val api = this.getAnnotationsByType(Api::class).firstOrNull()
        ?: error("API definition must be annotated with the Api annotation.")

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

    return ApiDefinition(
        name = api.name,
        basePath = api.basePath.takeIf { it.isNotBlank() },
        info = appInfo,
        servers = servers,
        tags = tags,
        type = this.toKotlinTypeDefinition(),
        documentation = this.docString,
        functions = this.getAllFunctions().map { it.toApiFunction() }.filterNotNull().toList(),
        annotations = this.annotations.map { it.toKotlinAnnotation() }.toList()
    )
}

internal fun KSFunctionDeclaration.toApiFunction(): ApiFunction? {
    val functionName = this.kotlinName.full

    if (this.typeParameters.isNotEmpty()) {
        error("API function $functionName cannot have generic type parameters as the API processor has no way of knowing which value to use when calling the function.")
    }

    var delete: DELETE? = null
    var get: GET? = null
    var head: HEAD? = null
    var options: OPTIONS? = null
    var patch: PATCH? = null
    var post: POST? = null
    var put: PUT? = null

    var formUrlEncoded: FormUrlEncoded? = null
    var multipart: Multipart? = null
    var contentNegotiation: ContentNegotiation? = null

    var responseHeaders: ResponseHeaders? = null

    var isDeprecated = false

    this.annotations.forEach { annotation ->
        when {
            annotation.isOfType(DELETE::class) -> delete =
                annotation.toAnnotation(DELETE::class)

            annotation.isOfType(GET::class) -> get =
                annotation.toAnnotation(GET::class)

            annotation.isOfType(HEAD::class) -> head =
                annotation.toAnnotation(HEAD::class)

            annotation.isOfType(OPTIONS::class) -> options =
                annotation.toAnnotation(OPTIONS::class)

            annotation.isOfType(PATCH::class) -> patch =
                annotation.toAnnotation(PATCH::class)

            annotation.isOfType(POST::class) -> post =
                annotation.toAnnotation(POST::class)

            annotation.isOfType(PUT::class) -> put =
                annotation.toAnnotation(PUT::class)

            annotation.isOfType(FormUrlEncoded::class) -> formUrlEncoded =
                annotation.toAnnotation(FormUrlEncoded::class)

            annotation.isOfType(Multipart::class) -> multipart =
                annotation.toAnnotation(Multipart::class)

            annotation.isOfType(ContentNegotiation::class) -> contentNegotiation =
                annotation.toAnnotation(ContentNegotiation::class)

            annotation.isOfType(ResponseHeaders::class) -> responseHeaders =
                annotation.toAnnotation(
                    ResponseHeaders::class
                )

            annotation.isOfType(Deprecated::class) -> isDeprecated = true
        }
    }

    check(listOfNotNull(delete, get, head, options, patch, post, put).size <= 1) {
        "Only one of the following annotations is allowed for each API function: DELETE, GET, HEAD, OPTIONS, PATCH, POST, PUT. Function: $functionName"
    }

    check(listOfNotNull(formUrlEncoded, multipart, contentNegotiation).size <= 1) {
        "Only one of the following annotations is allowed for each API function: FormUrlEncoded, Multipart, ContentNegotiation. Function: $functionName"
    }

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
    val requestBodyType = when {
        formUrlEncoded != null -> ApiRequestBodyType.FormUrlEncoded
        multipart != null -> ApiRequestBodyType.Multipart
        contentNegotiation != null -> ApiRequestBodyType.ContentNegotiation(value = contentNegotiation?.value?.takeIf { it.isNotBlank() })
        parameters.any { it is BodyParameter } -> ApiRequestBodyType.ContentNegotiation()
        else -> ApiRequestBodyType.None
    }
    val responseBody = this.returnType?.toKotlinResolvedTypeUsage()
    val errors = this.annotations
        .filter { it.shortName.asString() == "Errors" }
        .map { it.toAnnotation(Errors::class) }
        .firstOrNull()
        ?.toErrorAnnotations()

    val apiFunction = ApiFunction(
        kotlinFunction = this.toKotlinFunctionDeclaration(),
        method = httpMethod,
        path = path,
        requestBodyType = requestBodyType,
        responseBody = responseBody,
        extensionReceiver = this.extensionReceiver?.toKotlinTypeUsage(),
        parameters = parameters,
        responseHeaders = responseHeaders?.values?.map { it.toResponseHeader() } ?: emptyList(),
        errors = errors ?: emptyList(),
        isDeprecated = isDeprecated
    )

    if (apiFunction.bodyParameterOrNull() != null && (formUrlEncoded != null || multipart != null)) {
        error("API function containing a Body parameter cannot be annotated with the FormUrlEncoded or Multipart annotations. Function: $functionName")
    } else if (formUrlEncoded == null && apiFunction.fieldParameters.isNotEmpty()) {
        error("API function containing a Field parameter must be annotated with the FormUrlEncoded annotation. Function: $functionName")
    } else if (multipart == null && apiFunction.partParameters.isNotEmpty()) {
        error("API function containing a Part parameter must be annotated with the Multipart annotation. Function: $functionName")
    }

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

/**
 * Converts this [Errors] annotation to a [List] of [ErrorAnnotation] models.
 */
internal fun Errors.toErrorAnnotations(): List<ErrorAnnotation> =
    this.errors.map { error ->
        ErrorAnnotation(
            exceptionType = KotlinTypeUsage(
                name = KotlinName(full = error.exception.qualifiedName ?: error.exception.simpleName!!)
            ),
            error = ApiError(
                type = error.type,
                title = error.title,
                details = error.details.takeIf { it.isNotBlank() },
                status = error.statusCode,
                instance = error.instance.takeIf { it.isNotBlank() },
                timestamp = null,
                help = error.help.takeIf { it.isNotBlank() },
                signature = null
            )
        )
    }

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
    val isDeprecated = this.getAnnotationsByType(Deprecated::class).firstOrNull() != null

    check(listOfNotNull(path, query, field, part, header, body).size <= 1) {
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

        this.hasDefault -> DefaultValueParameter(declaration = parameterDeclaration, isDeprecated = isDeprecated)

        else -> {
            val type = parameterDeclaration.type

            when {
                type.isRoute || type.isApplicationCall || type.isUnit || type.isParameters || type.isMultiPartData ->
                    SupportedTypeParameter(declaration = parameterDeclaration, isDeprecated = isDeprecated)

                else -> error("Unsupported API function parameter type.")
            }
        }
    }
}
