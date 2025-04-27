package com.rkd.entity.facade

import com.rkd.entity.model.ProjectModel
import com.rkd.entity.service.ProjectService
import org.springframework.stereotype.Service

@Service
class ProjectFacade(
    private val projectService: ProjectService
) {
    fun findAll() = projectService.findAll()

    fun findByName(name: String) = projectService.findByName(name)

    fun create(projectModel: ProjectModel) = projectService.create(projectModel)

    fun update(name: String, projectModel: ProjectModel) = projectService.update(name, projectModel)

    fun delete(name: String) = projectService.delete(name)
}