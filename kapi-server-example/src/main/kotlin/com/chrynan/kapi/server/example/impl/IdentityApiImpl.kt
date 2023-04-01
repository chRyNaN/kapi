package com.chrynan.kapi.server.example.impl

import com.chrynan.kapi.server.example.api.IdentityApi
import com.chrynan.kapi.server.example.model.Identity
import com.chrynan.kapi.server.example.model.Page
import com.chrynan.kapi.server.example.model.PageDirection

internal class IdentityApiImpl : IdentityApi {

    override suspend fun getIdentity(id: String): Identity {
        TODO("Not yet implemented")
    }

    override suspend fun getIdentityPage(direction: PageDirection, id: String): Page<Identity> {
        TODO("Not yet implemented")
    }
}
