package org.rkd.domain.request.login

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank(message = "Name must not be blank")
    val name: String,
    @field:NotBlank(message = "Password must not be blank")
    val password: String
)