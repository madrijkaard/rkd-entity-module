package com.rkd.entity.service

import com.rkd.entity.component.CrudComponent
import com.rkd.entity.exception.IdentifierAlreadyExistsException
import com.rkd.entity.exception.QueryNotFoundException
import com.rkd.entity.model.DependencyModel
import com.rkd.entity.repository.DependencyRepository
import com.rkd.entity.type.ExceptionType.IDENTIFIER_ALREADY_EXISTS
import com.rkd.entity.type.ExceptionType.QUERY_NOT_FOUND
import org.springframework.stereotype.Component

@Component
class DependencyService(
    private val repository: DependencyRepository
) : CrudComponent<DependencyModel> {

    override fun findAll(): List<DependencyModel> = repository.findAll()

    override fun findByName(name: String) = repository.findByName(name) ?: throw QueryNotFoundException(QUERY_NOT_FOUND)

    override fun create(model: DependencyModel) {
        model.name?.takeIf { repository.findByName(it) == null }
            ?.let { repository.save(model) }
            ?: throw IdentifierAlreadyExistsException(IDENTIFIER_ALREADY_EXISTS)
    }

    override fun update(name: String, model: DependencyModel): DependencyModel {
        val existing = findByName(name)
        existing.structure = model.structure
        existing.projectModel = model.projectModel
        return repository.save(existing)
    }

    override fun delete(name: String) {
        val entity = findByName(name)
        repository.delete(entity)
    }
}
