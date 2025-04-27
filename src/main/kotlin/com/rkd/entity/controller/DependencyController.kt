package com.rkd.entity.controller

import com.rkd.entity.manager.CrudManager
import com.rkd.entity.model.DependencyModel
import com.rkd.entity.model.EntityModel
import com.rkd.entity.model.ProjectModel
import com.rkd.entity.type.ModelType.DEPENDENCY
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/dependencies")
class DependencyController(
    private val crudManager: CrudManager
) {
    @GetMapping
    fun getAll() = ok(crudManager.getServiceCrud<DependencyModel>(DEPENDENCY).findAll())

    @GetMapping("/{name}")
    fun getById(@PathVariable name: String) = ok(crudManager.getServiceCrud<ProjectModel>(DEPENDENCY).findByName(name))

    @PostMapping
    fun create(@Valid @RequestBody model: DependencyModel): ResponseEntity<Void> {
        crudManager.getServiceCrud<DependencyModel>(DEPENDENCY).create(model)
        return status(CREATED).build()
    }

    @PutMapping("/{name}")
    fun update(@PathVariable name: String, @RequestBody model: DependencyModel): ResponseEntity<Void> {
        crudManager.getServiceCrud<DependencyModel>(DEPENDENCY).update(name, model)
        return noContent().build()
    }

    @DeleteMapping("/{name}")
    fun delete(@PathVariable name: String): ResponseEntity<Void> {
        crudManager.getServiceCrud<EntityModel>(DEPENDENCY).delete(name)
        return noContent().build()
    }
}