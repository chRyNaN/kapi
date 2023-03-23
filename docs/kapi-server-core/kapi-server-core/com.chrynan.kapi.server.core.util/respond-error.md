//[kapi-server-core](../../index.md)/[com.chrynan.kapi.server.core.util](index.md)/[respondError](respond-error.md)

# respondError

[common]\
suspend fun ApplicationCall.[respondError](respond-error.md)(error: [ApiError](../../../kapi-core/kapi-core/com.chrynan.kapi.core/-api-error/index.md))

Responds with the provided [error](respond-error.md) applying the [ApiError.status](../../../kapi-core/kapi-core/com.chrynan.kapi.core/-api-error/status.md) code if it is not `null`.
