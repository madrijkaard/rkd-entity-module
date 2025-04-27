package com.rkd.entity.controller

import com.rkd.entity.manager.CrudManager
import com.rkd.entity.model.ResourceModel
import com.rkd.entity.type.ModelType.RESOURCE
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/resources")
class ResourceController(
    private val crudManager: CrudManager
) {
    @GetMapping
    fun getAll() = ok(crudManager.getServiceCrud<ResourceModel>(RESOURCE).findAll())

    @GetMapping("/{name}")
    fun getById(@PathVariable name: String) = ok(crudManager.getServiceCrud<ResourceModel>(RESOURCE).findByName(name))

    @PostMapping
    fun create(@Valid @RequestBody model: ResourceModel): ResponseEntity<Void> {
        crudManager.getServiceCrud<ResourceModel>(RESOURCE).create(model)
        return status(CREATED).build()
    }

    @PutMapping("/{name}")
    fun update(@PathVariable name: String, @RequestBody model: ResourceModel): ResponseEntity<Void> {
        crudManager.getServiceCrud<ResourceModel>(RESOURCE).update(name, model)
        return noContent().build()
    }

    @DeleteMapping("/{name}")
    fun delete(@PathVariable name: String): ResponseEntity<Void> {
        crudManager.getServiceCrud<ResourceModel>(RESOURCE).delete(name)
        return noContent().build()
    }
}