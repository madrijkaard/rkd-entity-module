package org.rkd.domain.usecase.login

import io.quarkus.redis.datasource.RedisDataSource
import io.smallrye.jwt.auth.principal.JWTParser
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.jwt.JsonWebToken
import org.rkd.definition.MessageDefinition
import org.rkd.definition.UnauthorizedActionException

@ApplicationScoped
class LogoutUserUseCase @Inject constructor(
    redisDataSource: RedisDataSource,
    private val jwtParser: JWTParser
) {

    private val redis = redisDataSource.value(String::class.java)

    fun logout(tokenHeader: String?) {

        if (tokenHeader.isNullOrBlank() || !tokenHeader.startsWith("Bearer "))
            throw UnauthorizedActionException(MessageDefinition.Login.MISSING_TOKEN)

        val token = tokenHeader.removePrefix("Bearer ").trim()

        val jwt: JsonWebToken = jwtParser.parse(token)

        val username = jwt.subject

        if (username.isNullOrBlank()) {
            throw UnauthorizedActionException(MessageDefinition.Login.INVALID_TOKEN)
        }

        val redisKey = "session:$username"

        val storedToken = redis.get(redisKey)

        if (storedToken != null && storedToken == token) {
            redis.getdel(redisKey)
        } else {
            throw UnauthorizedActionException(MessageDefinition.Login.INVALID_TOKEN)
        }
    }
}
