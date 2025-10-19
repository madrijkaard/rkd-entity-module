package org.rkd.port.`in`.resource

import jakarta.validation.Valid
import jakarta.ws.rs.HeaderParam
import jakarta.ws.rs.core.Response
import org.rkd.domain.request.login.LoginRequest

interface LoginResourceInPort {
    fun login(@Valid request: LoginRequest): Response
    fun logout(@HeaderParam("Authorization") tokenHeader: String?): Response
}