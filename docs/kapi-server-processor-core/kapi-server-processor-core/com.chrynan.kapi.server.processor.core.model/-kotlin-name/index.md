//[kapi-server-processor-core](../../../index.md)/[com.chrynan.kapi.server.processor.core.model](../index.md)/[KotlinName](index.md)

# KotlinName

[jvm]\
@Serializable

data class [KotlinName](index.md)(val full: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val short: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val qualifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)

Represents a name of a Kotlin Type or component.

## Constructors

| | |
|---|---|
| [KotlinName](-kotlin-name.md) | [jvm]<br>fun [KotlinName](-kotlin-name.md)(full: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), short: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), qualifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) |

## Properties

| Name | Summary |
|---|---|
| [full](full.md) | [jvm]<br>val [full](full.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The fully qualified name of the Kotlin Type (ex: kotlin.String, com.chrynan.example.Model, etc). If this represents a &quot;simple name&quot; and not a &quot;qualified name&quot;, then this will be equivalent to the [short](short.md) value. |
| [isSimple](is-simple.md) | [jvm]<br>val [isSimple](is-simple.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determines whether this [KotlinName](index.md) is considered a &quot;simple name&quot;, that is a name without a qualifier (ex: &quot;SimpleName&quot; instead of &quot;com.chrynan.FullyQualifiedName&quot;). The [full](full.md) property represents the full name, whereas the [short](short.md) property represents the last part of a fully qualified name. If both the [full](full.md) and [short](short.md) values are equal, then there is no qualifier and this is considered a &quot;simple name&quot;. |
| [packageName](package-name.md) | [jvm]<br>val [packageName](package-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Attempts to retrieve the package name from this [KotlinName](index.md) instance. The package name is considered the [qualifier](qualifier.md) value until the first uppercase letter is reached. For instance, consider the [KotlinName](index.md) for the [KotlinName.full](full.md) value of `com.example.test.OuterType.InnerType`, then the [KotlinName.qualifier](qualifier.md) value would be `com.example.test.OuterType`, and the [KotlinName.packageName](package-name.md) would be `com.example.test`. |
| [qualifier](qualifier.md) | [jvm]<br>val [qualifier](qualifier.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>The [full](full.md) value except for the last part used for the [short](short.md) property. |
| [short](short.md) | [jvm]<br>val [short](short.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The short part of the name of the Kotlin Type or component. If this represents a &quot;fully qualified&quot; name, then it is the last part (ex: &quot;Model&quot; in &quot;com.chrynan.example.Model&quot;). Otherwise, if this represents a &quot;simple name&quot;, then this will be equivalent to the [full](full.md) name. |
