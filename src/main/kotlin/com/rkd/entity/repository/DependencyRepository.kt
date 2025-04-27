package com.rkd.entity.repository

import com.rkd.entity.model.DependencyModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DependencyRepository : JpaRepository<DependencyModel, Long> {
    fun findByName(name: String): DependencyModel?
}
