package org.rkd.domain.request.user

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class DeleteUserRequest(
    @field:NotBlank(message = "Name must not be blank")
    @field:Pattern(regexp = "^\\S+$", message = "Name must not contain spaces")
    val name: String
)