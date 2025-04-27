package com.rkd.entity.service

import com.rkd.entity.component.CrudComponent
import com.rkd.entity.exception.IdentifierAlreadyExistsException
import com.rkd.entity.exception.QueryNotFoundException
import com.rkd.entity.model.SpringFrameworkModel
import com.rkd.entity.repository.SpringFrameworkRepository
import com.rkd.entity.type.ExceptionType.IDENTIFIER_ALREADY_EXISTS
import com.rkd.entity.type.ExceptionType.QUERY_NOT_FOUND
import org.springframework.stereotype.Component

@Component
class SpringFrameworkService(
    private val repository: SpringFrameworkRepository
) : CrudComponent<SpringFrameworkModel> {

    override fun findAll(): List<SpringFrameworkModel> = repository.findAll()

    override fun findByName(name: String) = repository.findByName(name) ?: throw QueryNotFoundException(QUERY_NOT_FOUND)

    override fun create(model: SpringFrameworkModel) {
        model.name?.takeIf { repository.findByName(it) == null }
            ?.let { repository.save(model) }
            ?: throw IdentifierAlreadyExistsException(IDENTIFIER_ALREADY_EXISTS)
    }

    override fun update(name: String, model: SpringFrameworkModel): SpringFrameworkModel {
        val existing = findByName(name)
        existing.structure = model.structure
        return repository.save(existing)
    }

    override fun delete(name: String) {
        val entity = findByName(name)
        repository.delete(entity)
    }
}
