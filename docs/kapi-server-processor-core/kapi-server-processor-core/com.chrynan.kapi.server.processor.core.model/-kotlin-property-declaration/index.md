//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[KotlinPropertyDeclaration](index.md)

# KotlinPropertyDeclaration

[jvm]\
@Serializable

data class [KotlinPropertyDeclaration](index.md)(val name: [KotlinName](../-kotlin-name/index.md), val type: [KotlinTypeUsage](../-kotlin-type-usage/index.md), val annotations: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage](../-kotlin-annotation-usage/index.md)&gt; = emptyList(), val modifiers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinPropertyModifier](../-kotlin-property-modifier/index.md)&gt; = emptyList(), val documentation: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val extensionReceiver: [KotlinTypeUsage](../-kotlin-type-usage/index.md)? = null, val hasGetter: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val hasSetter: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val isMutable: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val hasBackingField: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val isDelegated: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)

## Constructors

| | |
|---|---|
| [KotlinPropertyDeclaration](-kotlin-property-declaration.md) | [jvm]<br>fun [KotlinPropertyDeclaration](-kotlin-property-declaration.md)(name: [KotlinName](../-kotlin-name/index.md), type: [KotlinTypeUsage](../-kotlin-type-usage/index.md), annotations: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage](../-kotlin-annotation-usage/index.md)&gt; = emptyList(), modifiers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinPropertyModifier](../-kotlin-property-modifier/index.md)&gt; = emptyList(), documentation: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, extensionReceiver: [KotlinTypeUsage](../-kotlin-type-usage/index.md)? = null, hasGetter: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, hasSetter: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, isMutable: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, hasBackingField: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, isDelegated: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |

## Properties

| Name | Summary |
|---|---|
| [annotations](annotations.md) | [jvm]<br>val [annotations](annotations.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinAnnotationUsage](../-kotlin-annotation-usage/index.md)&gt; |
| [documentation](documentation.md) | [jvm]<br>val [documentation](documentation.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [extensionReceiver](extension-receiver.md) | [jvm]<br>val [extensionReceiver](extension-receiver.md): [KotlinTypeUsage](../-kotlin-type-usage/index.md)? = null |
| [hasBackingField](has-backing-field.md) | [jvm]<br>val [hasBackingField](has-backing-field.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [hasGetter](has-getter.md) | [jvm]<br>val [hasGetter](has-getter.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [hasSetter](has-setter.md) | [jvm]<br>val [hasSetter](has-setter.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [isDelegated](is-delegated.md) | [jvm]<br>val [isDelegated](is-delegated.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [isMutable](is-mutable.md) | [jvm]<br>val [isMutable](is-mutable.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [modifiers](modifiers.md) | [jvm]<br>val [modifiers](modifiers.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[KotlinPropertyModifier](../-kotlin-property-modifier/index.md)&gt; |
| [name](name.md) | [jvm]<br>val [name](name.md): [KotlinName](../-kotlin-name/index.md) |
| [type](type.md) | [jvm]<br>val [type](type.md): [KotlinTypeUsage](../-kotlin-type-usage/index.md) |
