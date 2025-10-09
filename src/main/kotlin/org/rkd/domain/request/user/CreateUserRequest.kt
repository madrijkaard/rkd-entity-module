package org.rkd.domain.request.user

import jakarta.validation.constraints.NotBlank

data class CreateUserRequest(
    @field:NotBlank(message = "Name must not be blank")
    val name: String,
    @field:NotBlank(message = "E-mail must not be blank")
    val email: String,
    @field:NotBlank(message = "Password must not be blank")
    val password: String,
    val structure: String?
)