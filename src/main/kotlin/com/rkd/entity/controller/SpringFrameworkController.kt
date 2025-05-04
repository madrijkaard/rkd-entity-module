package com.rkd.entity.controller

import com.rkd.entity.definition.UriBaseDefinition.SPRING_FRAMEWORKS
import com.rkd.entity.manager.CrudManager
import com.rkd.entity.model.SpringFrameworkModel
import com.rkd.entity.type.ModelType.SPRING_FRAMEWORK
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(SPRING_FRAMEWORKS)
class SpringFrameworkController(
    private val crudManager: CrudManager
) {
    @GetMapping
    fun getAll() = ok(crudManager.getServiceCrud<SpringFrameworkModel>(SPRING_FRAMEWORK).findAll())

    @GetMapping("/{name}")
    fun getById(@PathVariable name: String) =
        ok(crudManager.getServiceCrud<SpringFrameworkModel>(SPRING_FRAMEWORK).findByName(name))

    @PostMapping
    fun create(@Valid @RequestBody model: SpringFrameworkModel): ResponseEntity<Void> {
        crudManager.getServiceCrud<SpringFrameworkModel>(SPRING_FRAMEWORK).create(model)
        return status(CREATED).build()
    }

    @PutMapping("/{name}")
    fun update(@PathVariable name: String, @RequestBody model: SpringFrameworkModel): ResponseEntity<Void> {
        crudManager.getServiceCrud<SpringFrameworkModel>(SPRING_FRAMEWORK).update(name, model)
        return noContent().build()
    }

    @DeleteMapping("/{name}")
    fun delete(@PathVariable name: String): ResponseEntity<Void> {
        crudManager.getServiceCrud<SpringFrameworkModel>(SPRING_FRAMEWORK).delete(name)
        return noContent().build()
    }
}