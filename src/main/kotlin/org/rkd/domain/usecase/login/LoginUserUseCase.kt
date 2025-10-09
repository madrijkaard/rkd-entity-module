package org.rkd.domain.usecase.login

import io.quarkus.redis.datasource.RedisDataSource
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.rkd.definition.InvalidPasswordException
import org.rkd.definition.MessageDefinition
import org.rkd.definition.UserNotFoundException
import org.rkd.domain.model.UserModel
import org.rkd.domain.request.login.LoginRequest
import org.rkd.domain.usecase.hash.CreateHashUseCase
import org.rkd.port.out.repository.UserRepositoryOutPort
import java.time.Instant
import java.time.temporal.ChronoUnit

@ApplicationScoped
class LoginUserUseCase @Inject constructor(
    private val userRepository: UserRepositoryOutPort,
    private val createHashUseCase: CreateHashUseCase,
    redisDataSource: RedisDataSource
) {

    private val redis = redisDataSource.value(String::class.java)

    fun login(request: LoginRequest): UserModel {

        val userModel = userRepository.findByName(request.name) ?: throw UserNotFoundException(MessageDefinition.User.NOT_FOUND)

        if (!createHashUseCase.verify(request.password, userModel.hash!!)) {
            throw InvalidPasswordException(MessageDefinition.Login.INVALID_PASSWORD)
        }

        val expirationTime = Instant.now().plus(30, ChronoUnit.MINUTES).epochSecond.toString()

        redis.setex(request.name, 30 * 60L, expirationTime)

        return userModel
    }
}