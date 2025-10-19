package org.rkd.domain.usecase.user

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.rkd.definition.MessageDefinition
import org.rkd.definition.UserAlreadyRegisteredException
import org.rkd.domain.model.UserModel
import org.rkd.domain.request.user.CreateUserRequest
import org.rkd.domain.usecase.hash.CreateHashUseCase
import org.rkd.port.out.repository.UserRepositoryOutPort

@ApplicationScoped
class CreateUserUseCase @Inject constructor(
    private val userRepository: UserRepositoryOutPort,
    private val createHashUseCase: CreateHashUseCase
) {

    @Transactional
    fun create(request: CreateUserRequest) {

        userRepository.findByName(request.name)?.let { throw UserAlreadyRegisteredException(MessageDefinition.User.ALREADY_EXISTS) }

        val (hash, salt) = createHashUseCase.hash(request.password)
        val userModel = request.toUserModel(hash, salt)

        userRepository.persist(userModel)
    }

    private fun CreateUserRequest.toUserModel(hash: String, salt: String) = UserModel().apply {
        name = this@toUserModel.name
        active = false
        email = this@toUserModel.email
        this.hash = hash
        this.salt = salt
        this.structure = this@toUserModel.structure
    }
}
