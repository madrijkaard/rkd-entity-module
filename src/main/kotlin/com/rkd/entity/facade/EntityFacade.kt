package com.rkd.entity.facade

import com.rkd.entity.model.EntityModel
import com.rkd.entity.service.EntityService
import org.springframework.stereotype.Service

@Service
class EntityFacade(
    private val entityService: EntityService
) {
    fun findAll() = entityService.findAll()

    fun findByName(name: String) = entityService.findByName(name)

    fun create(entityModel: EntityModel) = entityService.create(entityModel)

    fun update(name: String, entityModel: EntityModel) = entityService.update(name, entityModel)

    fun delete(name: String) = entityService.delete(name)
}