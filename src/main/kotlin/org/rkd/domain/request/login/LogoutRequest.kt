package org.rkd.domain.request.login

import jakarta.validation.constraints.NotBlank

data class LogoutRequest(
    @field:NotBlank(message = "Name must not be blank")
    val name: String
)