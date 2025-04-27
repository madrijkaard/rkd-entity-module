package com.rkd.entity.service

import com.rkd.entity.component.CrudComponent
import com.rkd.entity.exception.IdentifierAlreadyExistsException
import com.rkd.entity.exception.QueryNotFoundException
import com.rkd.entity.model.ResourceModel
import com.rkd.entity.repository.ResourceRepository
import com.rkd.entity.type.ExceptionType.IDENTIFIER_ALREADY_EXISTS
import com.rkd.entity.type.ExceptionType.QUERY_NOT_FOUND
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class ResourceService(
    private val repository: ResourceRepository
) : CrudComponent<ResourceModel> {

    override fun findAll(): List<ResourceModel> = repository.findAll()

    override fun findByName(name: String) = repository.findByName(name) ?: throw QueryNotFoundException(QUERY_NOT_FOUND)

    @Transactional
    override fun create(model: ResourceModel) {
        model.name?.takeIf { repository.findByName(it) == null }
            ?.let { repository.save(model) }
            ?: throw IdentifierAlreadyExistsException(IDENTIFIER_ALREADY_EXISTS)
    }

    @Transactional
    override fun update(name: String, model: ResourceModel): ResourceModel {
        val existing = findByName(name)
        existing.structure = model.structure
        existing.projectModel = model.projectModel
        return repository.save(existing)
    }

    @Transactional
    override fun delete(name: String) {
        val entity = findByName(name)
        repository.delete(entity)
    }
}
