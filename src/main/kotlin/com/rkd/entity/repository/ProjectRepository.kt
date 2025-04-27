package com.rkd.entity.repository

import com.rkd.entity.model.ProjectModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository : JpaRepository<ProjectModel, Long> {
    fun findByName(name: String): ProjectModel?
}
