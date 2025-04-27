package com.rkd.entity.service

import com.rkd.entity.component.CrudComponent
import com.rkd.entity.exception.IdentifierAlreadyExistsException
import com.rkd.entity.exception.QueryNotFoundException
import com.rkd.entity.model.ProjectModel
import com.rkd.entity.repository.ProjectRepository
import com.rkd.entity.type.ExceptionType.IDENTIFIER_ALREADY_EXISTS
import com.rkd.entity.type.ExceptionType.QUERY_NOT_FOUND
import org.springframework.stereotype.Component

@Component
class ProjectService(
    private val projectRepository: ProjectRepository
) : CrudComponent<ProjectModel> {

    override fun findAll(): List<ProjectModel> = projectRepository.findAll()

    override fun findByName(name: String) =
        projectRepository.findByName(name) ?: throw QueryNotFoundException(QUERY_NOT_FOUND)

    override fun create(model: ProjectModel) {
        model.name?.takeIf { projectRepository.findByName(it) == null }
            ?.let { projectRepository.save(model) }
            ?: throw IdentifierAlreadyExistsException(IDENTIFIER_ALREADY_EXISTS)
    }

    override fun update(name: String, model: ProjectModel): ProjectModel {
        val existing = findByName(name)
        existing.springFrameworkModel = model.springFrameworkModel
        return projectRepository.save(existing)
    }

    override fun delete(name: String) {
        val entity = findByName(name)
        projectRepository.delete(entity)
    }
}
