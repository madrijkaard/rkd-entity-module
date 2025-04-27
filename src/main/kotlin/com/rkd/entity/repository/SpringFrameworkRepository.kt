package com.rkd.entity.repository

import com.rkd.entity.model.SpringFrameworkModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpringFrameworkRepository : JpaRepository<SpringFrameworkModel, Long> {
    fun findByName(name: String): SpringFrameworkModel?
}
