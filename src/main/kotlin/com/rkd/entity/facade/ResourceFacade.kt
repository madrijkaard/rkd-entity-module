package com.rkd.entity.facade

import com.rkd.entity.model.ResourceModel
import com.rkd.entity.service.ResourceService
import org.springframework.stereotype.Service

@Service
class ResourceFacade(
    private val resourceService: ResourceService
) {
    fun findAll() = resourceService.findAll()

    fun findByName(name: String) = resourceService.findByName(name)

    fun create(resourceModel: ResourceModel) = resourceService.create(resourceModel)

    fun update(name: String, resourceModel: ResourceModel) = resourceService.update(name, resourceModel)

    fun delete(name: String) = resourceService.delete(name)
}