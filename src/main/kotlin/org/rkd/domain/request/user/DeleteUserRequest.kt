package org.rkd.domain.request.user

import jakarta.validation.constraints.NotBlank

data class DeleteUserRequest(
    @field:NotBlank(message = "Name must not be blank")
    val name: String
)