package com.chrynan.kapi.server.example.impl

import com.chrynan.kapi.server.example.api.IdentityApi
import com.chrynan.kapi.server.example.model.GetIdentityPageBody
import com.chrynan.kapi.server.example.model.Identity
import com.chrynan.kapi.server.example.model.Page

internal class IdentityApiImpl : IdentityApi {

    override suspend fun getIdentity(id: String): Identity {
        TODO("Not yet implemented")
    }

    override suspend fun getIdentityPage(body: GetIdentityPageBody): Page<Identity> {
        TODO("Not yet implemented")
    }
}
