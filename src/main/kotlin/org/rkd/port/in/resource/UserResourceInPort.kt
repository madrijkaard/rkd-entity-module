package org.rkd.port.`in`.resource

import jakarta.validation.Valid
import jakarta.ws.rs.core.Response
import org.rkd.domain.request.user.CreateUserRequest
import org.rkd.domain.request.user.DeleteUserRequest
import org.rkd.domain.request.login.LoginRequest
import org.rkd.domain.request.user.UpdateUserRequest

interface UserResourceInPort {
    fun create(@Valid request: CreateUserRequest): Response
    fun update(@Valid request: UpdateUserRequest): Response
    fun remove(@Valid request: DeleteUserRequest): Response
}