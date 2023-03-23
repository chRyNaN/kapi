//[kapi-server-core](../../index.md)/[com.chrynan.kapi.server.core.annotation](index.md)/[contentType](content-type.md)

# contentType

[common]\
val [ContentNegotiation](-content-negotiation/index.md).[contentType](content-type.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?

Represents the [Consumes.contentType](-consumes/content-type.md) value for the [ContentNegotiation](-content-negotiation/index.md) annotation. The actual value used in the [Consumes](-consumes/index.md) annotation is an empty [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) since annotation properties cannot have null values, but that is essentially equivalent to `null` and will be converted to it.

[common]\
val [ApplicationFormUrlEncoded](-application-form-url-encoded/index.md).[contentType](content-type.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Represents the [Consumes.contentType](-consumes/content-type.md) value for the [ApplicationFormUrlEncoded](-application-form-url-encoded/index.md) annotation.

[common]\
val [MultipartFormData](-multipart-form-data/index.md).[contentType](content-type.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Represents the [Consumes.contentType](-consumes/content-type.md) value for the [MultipartFormData](-multipart-form-data/index.md) annotation.

[common]\
val [ApplicationJson](-application-json/index.md).[contentType](content-type.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Represents the [Consumes.contentType](-consumes/content-type.md) value for the [ApplicationJson](-application-json/index.md) annotation.
