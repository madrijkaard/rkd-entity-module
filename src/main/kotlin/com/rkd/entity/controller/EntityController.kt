package com.rkd.entity.controller

import com.rkd.entity.manager.CrudManager
import com.rkd.entity.model.EntityModel
import com.rkd.entity.model.ProjectModel
import com.rkd.entity.type.ModelType.ENTITY
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/entities")
class EntityController(
    private val crudManager: CrudManager
) {
    @GetMapping
    fun getAll() = ok(crudManager.getServiceCrud<ProjectModel>(ENTITY).findAll())

    @GetMapping("/{name}")
    fun getById(@PathVariable name: String) = ok(crudManager.getServiceCrud<ProjectModel>(ENTITY).findByName(name))

    @PostMapping
    fun create(@Valid @RequestBody model: EntityModel): ResponseEntity<Void> {
        crudManager.getServiceCrud<EntityModel>(ENTITY).create(model)
        return status(CREATED).build()
    }

    @PutMapping("/{name}")
    fun update(@PathVariable name: String, @RequestBody model: EntityModel): ResponseEntity<Void> {
        crudManager.getServiceCrud<EntityModel>(ENTITY).update(name, model)
        return noContent().build()
    }

    @DeleteMapping("/{name}")
    fun delete(@PathVariable name: String): ResponseEntity<Void> {
        crudManager.getServiceCrud<EntityModel>(ENTITY).delete(name)
        return noContent().build()
    }
}