package org.rkd.adapter.`in`.resource

import jakarta.inject.Inject
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.Response
import org.rkd.domain.request.user.CreateUserRequest
import org.rkd.domain.request.user.DeleteUserRequest
import org.rkd.domain.request.user.UpdateUserRequest
import org.rkd.domain.usecase.user.CreateUserUseCase
import org.rkd.domain.usecase.user.DeleteUserUseCase
import org.rkd.domain.usecase.user.UpdateUserUseCase
import org.rkd.port.`in`.resource.UserResourceInPort

@Path("/users")
class UserResourceInAdapter @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : UserResourceInPort {

    @POST
    override fun create(request: CreateUserRequest): Response {
        createUserUseCase.create(request)
        return Response.status(Response.Status.CREATED).build()
    }

    @PUT
    override fun update(request: UpdateUserRequest): Response {
        updateUserUseCase.update(request)
        return Response.noContent().build()
    }

    @DELETE
    override fun remove(request: DeleteUserRequest): Response {
        deleteUserUseCase.delete(request)
        return Response.ok().build()
    }
}