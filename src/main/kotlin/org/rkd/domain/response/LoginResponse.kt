package org.rkd.domain.response

data class TokenResponse(
    val token: String,
    val expiresAt: Long
)
