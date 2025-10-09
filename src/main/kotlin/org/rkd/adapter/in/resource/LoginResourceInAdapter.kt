package org.rkd.adapter.`in`.resource

import jakarta.inject.Inject
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.Response
import org.rkd.domain.request.login.LoginRequest
import org.rkd.domain.request.login.LogoutRequest
import org.rkd.domain.usecase.login.LoginUserUseCase
import org.rkd.port.`in`.resource.LoginResourceInPort

class LoginResourceInAdapter @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) : LoginResourceInPort {

    @Path("/login")
    override fun login(request: LoginRequest): Response {
        val model = loginUserUseCase.login(request)
        return Response.status(Response.Status.OK).entity(model).build()
    }

    @Path("/logout")
    override fun logout(request: LogoutRequest): Response {
        TODO("Not yet implemented")
    }
}