package com.rkd.entity.repository

import com.rkd.entity.model.ResourceModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ResourceRepository : JpaRepository<ResourceModel, Long> {
    fun findByName(name: String): ResourceModel?
}
