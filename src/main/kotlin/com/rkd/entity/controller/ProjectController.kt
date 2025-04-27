package com.rkd.entity.controller

import com.rkd.entity.manager.CrudManager
import com.rkd.entity.model.ProjectModel
import com.rkd.entity.type.ModelType.PROJECT
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/projects")
class ProjectController(
    private val crudManager: CrudManager
) {
    @GetMapping
    fun getAll() = ok(crudManager.getServiceCrud<ProjectModel>(PROJECT).findAll())

    @GetMapping("/{name}")
    fun getById(@PathVariable name: String) = ok(crudManager.getServiceCrud<ProjectModel>(PROJECT).findByName(name))

    @PostMapping
    fun create(@Valid @RequestBody model: ProjectModel): ResponseEntity<Void> {
        crudManager.getServiceCrud<ProjectModel>(PROJECT).create(model)
        return status(CREATED).build()
    }

    @PutMapping("/{name}")
    fun update(@PathVariable name: String, @RequestBody model: ProjectModel): ResponseEntity<Void> {
        crudManager.getServiceCrud<ProjectModel>(PROJECT).update(name, model)
        return noContent().build()
    }

    @DeleteMapping("/{name}")
    fun delete(@PathVariable name: String): ResponseEntity<Void> {
        crudManager.getServiceCrud<ProjectModel>(PROJECT).delete(name)
        return noContent().build()
    }
}