package org.rkd.port.`in`.resource

import jakarta.validation.Valid
import jakarta.ws.rs.core.Response
import org.rkd.domain.request.login.LoginRequest
import org.rkd.domain.request.login.LogoutRequest

interface LoginResourceInPort {
    fun login(@Valid request: LoginRequest): Response
    fun logout(@Valid request: LogoutRequest): Response
}