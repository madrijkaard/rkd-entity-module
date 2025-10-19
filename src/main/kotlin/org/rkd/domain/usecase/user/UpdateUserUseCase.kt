package org.rkd.domain.usecase.user

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.rkd.domain.request.user.UpdateUserRequest
import org.rkd.port.out.repository.UserRepositoryOutPort

@ApplicationScoped
class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepositoryOutPort
) {
    @Transactional
    fun update(request: UpdateUserRequest) {

        val userModel = userRepository.findByName(request.name)
            ?: throw IllegalArgumentException("Usuário '${request.name}' não encontrado.")

        userModel.name = request.name
        userModel.email = request.email
        userModel.structure = request.structure

        userRepository.persist(userModel)
    }
}