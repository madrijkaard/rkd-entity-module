package org.rkd.adapter.out.repository

import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import org.rkd.domain.model.UserModel
import org.rkd.port.out.repository.UserRepositoryOutPort

@ApplicationScoped
class UserRepositoryOutAdapter : PanacheRepository<UserModel>, UserRepositoryOutPort {

    override fun findByName(name: String): UserModel? {
        return find("name", name).firstResult()
    }

    override fun insert(user: UserModel) {
        persist(user)
    }

    override fun update(user: UserModel) {

        val existingUser = findByName(user.name!!)

        if (existingUser != null) {
            existingUser.name = user.name
            existingUser.active = user.active
            existingUser.email = user.email
            existingUser.hash = user.hash
            existingUser.salt = user.salt
            existingUser.structure = user.structure
        }
    }

    override fun delete(name: String) {
        val user = findByName(name)
        delete(user)
    }
}