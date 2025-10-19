package org.rkd.domain.request.login

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class LoginRequest(
    @field:NotBlank(message = "Name must not be blank")
    @field:Pattern(regexp = "^\\S+$", message = "Name must not contain spaces")
    val name: String,
    @field:NotBlank(message = "Password must not be blank")
    @field:Pattern(regexp = "^\\S+$", message = "Password must not contain spaces")
    val password: String
)