package com.rkd.entity.repository

import com.rkd.entity.model.EntityModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EntityRepository : JpaRepository<EntityModel, Long> {
    fun findByName(name: String): EntityModel?
}