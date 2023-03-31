package com.chrynan.kapi.server.example

import com.chrynan.kapi.core.Kapi
import com.chrynan.kapi.server.core.auth.security
import com.chrynan.kapi.server.example.api.identityApi
import com.chrynan.kapi.server.example.api.registerIdentityApi
import com.chrynan.kapi.server.example.impl.IdentityApiImpl
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    routing {
        registerIdentityApi(IdentityApiImpl())
    }

    install(Authentication) {
        Kapi.security.identityApi.identityOAuthProviderDefinition.let { definition ->

        }
    }
}
