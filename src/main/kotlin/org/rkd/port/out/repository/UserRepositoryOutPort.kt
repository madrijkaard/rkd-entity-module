package org.rkd.port.out.repository

import org.rkd.domain.model.UserModel

interface UserRepositoryOutPort {
    fun findByName(name: String): UserModel?
    fun persist(user: UserModel)
    fun delete(user: UserModel)
}