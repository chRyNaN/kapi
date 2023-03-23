//[kapi-server-ksp](../../index.md)/[com.chrynan.kapi.server.ksp](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [Configuration](-configuration/index.md) | [jvm]<br>@Serializable<br>data class [Configuration](-configuration/index.md)(val generateJson: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val generateKtorBindings: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val generateOpenApiSpec: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |
| [JsonApiProcessor](-json-api-processor/index.md) | [jvm]<br>class [JsonApiProcessor](-json-api-processor/index.md)(codeGenerator: CodeGenerator, logger: KSPLogger) : [ApiProcessor](../../../kapi-server-processor-core/kapi-server-processor-core/com.chrynan.kapi.server.processor.core/-api-processor/index.md) |
| [KapiSymbolProcessor](-kapi-symbol-processor/index.md) | [jvm]<br>class [KapiSymbolProcessor](-kapi-symbol-processor/index.md)(apiProcessors: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ApiProcessor](../../../kapi-server-processor-core/kapi-server-processor-core/com.chrynan.kapi.server.processor.core/-api-processor/index.md)&gt;) : SymbolProcessor |
| [KapiSymbolProcessorProvider](-kapi-symbol-processor-provider/index.md) | [jvm]<br>class [KapiSymbolProcessorProvider](-kapi-symbol-processor-provider/index.md) : SymbolProcessorProvider |
| [KtorBindingsApiProcessor](-ktor-bindings-api-processor/index.md) | [jvm]<br>class [KtorBindingsApiProcessor](-ktor-bindings-api-processor/index.md)(codeGenerator: CodeGenerator, logger: KSPLogger) : [ApiProcessor](../../../kapi-server-processor-core/kapi-server-processor-core/com.chrynan.kapi.server.processor.core/-api-processor/index.md) |
| [KtorValidatorApiProcessor](-ktor-validator-api-processor/index.md) | [jvm]<br>class [KtorValidatorApiProcessor](-ktor-validator-api-processor/index.md)(logger: KSPLogger) : [ApiProcessor](../../../kapi-server-processor-core/kapi-server-processor-core/com.chrynan.kapi.server.processor.core/-api-processor/index.md) |
