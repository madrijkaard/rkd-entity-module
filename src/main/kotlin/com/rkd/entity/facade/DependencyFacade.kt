package com.rkd.entity.facade

import com.rkd.entity.model.DependencyModel
import com.rkd.entity.service.DependencyService
import org.springframework.stereotype.Service

@Service
class DependencyFacade(
    private val dependencyService: DependencyService
) {
    fun findAll() = dependencyService.findAll()

    fun findByName(name: String) = dependencyService.findByName(name)

    fun create(dependencyModel: DependencyModel) = dependencyService.create(dependencyModel)

    fun update(name: String, dependencyModel: DependencyModel) = dependencyService.update(name, dependencyModel)

    fun delete(name: String) = dependencyService.delete(name)
}