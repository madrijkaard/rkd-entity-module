package com.rkd.entity.service

import com.rkd.entity.component.CrudComponent
import com.rkd.entity.exception.IdentifierAlreadyExistsException
import com.rkd.entity.exception.QueryNotFoundException
import com.rkd.entity.model.EntityModel
import com.rkd.entity.repository.EntityRepository
import com.rkd.entity.type.ExceptionType.IDENTIFIER_ALREADY_EXISTS
import com.rkd.entity.type.ExceptionType.QUERY_NOT_FOUND
import org.springframework.stereotype.Component

@Component
class EntityService(
    private val entityRepository: EntityRepository
) : CrudComponent<EntityModel> {

    override fun findAll(): List<EntityModel> = entityRepository.findAll()

    override fun findByName(name: String) =
        entityRepository.findByName(name) ?: throw QueryNotFoundException(QUERY_NOT_FOUND)

    override fun create(entityModel: EntityModel) {

        entityModel.name?.takeIf { entityRepository.findByName(it) == null }
            ?.let { entityRepository.save(entityModel) }
            ?: throw IdentifierAlreadyExistsException(IDENTIFIER_ALREADY_EXISTS)
    }

    override fun update(name: String, entityModel: EntityModel): EntityModel {

        val entity = findByName(name)

        entityModel.structure.let {
            entity.structure = it
        }

        return entityRepository.save(entity)
    }

    override fun delete(name: String) {
        val entity = findByName(name)
        entityRepository.delete(entity)
    }
}