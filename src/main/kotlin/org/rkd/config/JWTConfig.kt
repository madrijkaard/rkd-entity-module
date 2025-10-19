package org.rkd.config

import io.smallrye.jwt.build.Jwt
import jakarta.enterprise.context.ApplicationScoped
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID

@ApplicationScoped
class JWTConfig {

    companion object {
        private const val ISSUER = "rkd-auth-service"
        private const val EXPIRATION_HOURS = 1L
    }

    fun generateToken(subject: String): String {
        val now = Instant.now()
        val expiresAt = now.plus(EXPIRATION_HOURS, ChronoUnit.HOURS)
        val jti = UUID.randomUUID().toString()

        return Jwt.claims()
            .subject(subject)
            .issuer(ISSUER)
            .issuedAt(now)
            .expiresAt(expiresAt)
            .claim("jti", jti)
            .jws()
            .sign()
    }

    fun expirationEpochSeconds(): Long =
        Instant.now().plus(EXPIRATION_HOURS, ChronoUnit.HOURS).epochSecond

    fun expirationSeconds(): Long = EXPIRATION_HOURS * 3600
}
