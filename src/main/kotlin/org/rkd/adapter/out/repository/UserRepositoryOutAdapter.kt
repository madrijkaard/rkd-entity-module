package org.rkd.adapter.out.repository

import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import org.rkd.domain.model.UserModel
import org.rkd.port.out.repository.UserRepositoryOutPort

@ApplicationScoped
class UserRepositoryOutAdapter : PanacheRepository<UserModel>, UserRepositoryOutPort {

    override fun findByName(name: String): UserModel? = find("name", name).firstResult()

    override fun persist(user: UserModel) = super<PanacheRepository>.persist(user)

    override fun delete(user: UserModel) = super<PanacheRepository>.delete(user)
}
