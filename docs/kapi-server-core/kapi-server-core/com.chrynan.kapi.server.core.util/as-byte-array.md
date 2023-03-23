//[kapi-server-core](../../index.md)/[com.chrynan.kapi.server.core.util](index.md)/[asByteArray](as-byte-array.md)

# asByteArray

[common]\
suspend fun PartData.[asByteArray](as-byte-array.md)(): [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)

Converts this PartData into a [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html) if possible. If this is a PartData.FormItem instance, then an exception will be thrown as that cannot be converted into an [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html).
