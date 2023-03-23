//[kapi-server-core](../../../index.md)/[com.chrynan.kapi.server.core.annotation](../index.md)/[Tag](index.md)

# Tag

[common]\
@[Target](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-target/index.html)(allowedTargets = [])

annotation class [Tag](index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)

Represents a tag, or category, that describes the annotated component. This could be used to group or associate related API components within a UI, for instance. Tags are unique within an API, so there cannot be more than one Tag with the same [Tag.name](name.md) value.

Note that blank [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) properties are equivalent to `null`. This is due to Kotlin annotations not allowing nullable properties, so blank strings can be defaults. Therefore, required [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) fields must not be blank.

## Properties

| Name | Summary |
|---|---|
| [description](description.md) | [common]<br>val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>A short description for this tag. If this value is blank, it is considered `null`. |
| [name](name.md) | [common]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The name of this tag. |
