package org.rkd.domain.request.user

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class CreateUserRequest(
    @field:NotBlank(message = "Name must not be blank")
    @field:Pattern(regexp = "^\\S+$", message = "Name must not contain spaces")
    val name: String,
    @field:NotBlank(message = "E-mail must not be blank")
    @field:Pattern(regexp = "^\\S+$", message = "E-mail must not contain spaces")
    val email: String,
    @field:NotBlank(message = "Password must not be blank")
    @field:Pattern(regexp = "^\\S+$", message = "Password must not contain spaces")
    val password: String,
    val structure: String?
)