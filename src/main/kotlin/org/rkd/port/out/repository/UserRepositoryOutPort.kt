package org.rkd.port.out.repository

import org.rkd.domain.model.UserModel

interface UserRepositoryOutPort {
    fun findByName(name: String): UserModel?
    fun insert(user: UserModel)
    fun update(user: UserModel)
    fun delete(name: String)
}