package org.rkd.adapter.`in`.resource

import jakarta.inject.Inject
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.rkd.domain.request.login.LoginRequest
import org.rkd.domain.usecase.login.LoginUserUseCase
import org.rkd.domain.usecase.login.LogoutUserUseCase
import org.rkd.port.`in`.resource.LoginResourceInPort

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class LoginResourceInAdapter @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase
) : LoginResourceInPort {

    @POST
    @Path("/login")
    override fun login(@Valid request: LoginRequest): Response {
        val tokenResponse = loginUserUseCase.login(request)
        return Response.status(Response.Status.OK).entity(tokenResponse).build()
    }

    @POST
    @Path("/logout")
    override fun logout(@HeaderParam("Authorization") tokenHeader: String?): Response {
        logoutUserUseCase.logout(tokenHeader)
        return Response.noContent().build()
    }
}