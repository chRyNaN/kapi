//[kapi-server-core](../../../index.md)/[com.chrynan.kapi.server.core.annotation](../index.md)/[Field](index.md)

# Field

[common]\
@[Target](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-target/index.html)(allowedTargets = [[AnnotationTarget.VALUE_PARAMETER](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.annotation/-annotation-target/-v-a-l-u-e_-p-a-r-a-m-e-t-e-r/index.html)])

annotation class [Field](index.md)(val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)

The parameter of an API function annotated with [Field](index.md) represents a form url-encoded field extracted from the request body of the HTTP endpoint. A [Field](index.md) parameter can be one of the following types:

- 
   Kotlin primitive
- 
   List, Collection, or Array of type String

**Note:** To use this annotation on a parameter in an API function, the API function has to be annotated with [ApplicationFormUrlEncoded](../-application-form-url-encoded/index.md).

**Note:** That this annotation cannot be provided on a parameter of an API function if a [Body](../-body/index.md) or [Part](../-part/index.md) annotation is present on a parameter of that same API function.

## Properties

| Name | Summary |
|---|---|
| [value](value.md) | [common]<br>val [value](value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The name of the field. If this value is blank, then the name of the parameter will be used instead. |
