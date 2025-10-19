package org.rkd.domain.usecase.login

import io.quarkus.redis.datasource.RedisDataSource
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.rkd.config.JWTConfig
import org.rkd.definition.InvalidPasswordException
import org.rkd.definition.MessageDefinition
import org.rkd.definition.UserNotFoundException
import org.rkd.domain.request.login.LoginRequest
import org.rkd.domain.response.TokenResponse
import org.rkd.domain.usecase.hash.CreateHashUseCase
import org.rkd.port.out.repository.UserRepositoryOutPort

@ApplicationScoped
class LoginUserUseCase @Inject constructor(
    private val userRepository: UserRepositoryOutPort,
    private val createHashUseCase: CreateHashUseCase,
    redisDataSource: RedisDataSource,
    private val jwtConfig: JWTConfig
) {

    private val redis = redisDataSource.value(String::class.java)

    fun login(request: LoginRequest): TokenResponse {

        val userModel = userRepository.findByName(request.name)
            ?: throw UserNotFoundException(MessageDefinition.User.NOT_FOUND)

        if (!createHashUseCase.verify(request.password, userModel.hash!!)) {
            throw InvalidPasswordException(MessageDefinition.Login.INVALID_PASSWORD)
        }

        val token = jwtConfig.generateToken(userModel.name!!)
        val expiresAt = jwtConfig.expirationEpochSeconds()
        val redisKey = "session:${userModel.name}"

        redis.setex(redisKey, jwtConfig.expirationSeconds(), token)

        return TokenResponse(token = token, expiresAt = expiresAt)
    }
}
