package org.rkd.domain.usecase.user

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.rkd.domain.request.user.DeleteUserRequest
import org.rkd.port.out.repository.UserRepositoryOutPort

@ApplicationScoped
class DeleteUserUseCase @Inject constructor(
    private val userRepository: UserRepositoryOutPort
) {
    @Transactional
    fun delete(request: DeleteUserRequest) {

        val userModel = userRepository.findByName(request.name)
            ?: throw IllegalArgumentException("Usuário '${request.name}' não encontrado.")

        userRepository.delete(userModel.name!!)
    }
}