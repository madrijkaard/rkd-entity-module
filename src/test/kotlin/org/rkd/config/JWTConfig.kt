package org.rkd.config

import io.smallrye.jwt.build.Jwt
import java.time.Instant

object JWTConfig {

    fun generateToken(): String {
        return Jwt.claims()
            .subject("test-user")
            .issuer("rkd-auth-service")
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plusSeconds(3600))
            .sign()
    }
}