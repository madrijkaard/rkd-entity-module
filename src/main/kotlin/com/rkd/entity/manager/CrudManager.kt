package com.rkd.entity.manager

import com.rkd.entity.component.CrudComponent
import com.rkd.entity.service.*
import com.rkd.entity.type.ModelType
import com.rkd.entity.type.ModelType.*
import org.springframework.stereotype.Component

@Component
class CrudManager(
    private val springFrameworkService: SpringFrameworkService,
    private val dependencyService: DependencyService,
    private val resourceService: ResourceService,
    private val projectService: ProjectService,
    private val entityService: EntityService
) {
    @Suppress("UNCHECKED_CAST")
    fun <T> getServiceCrud(type: ModelType): CrudComponent<T> {
        return when (type) {
            SPRING_FRAMEWORK -> springFrameworkService as CrudComponent<T>
            DEPENDENCY -> dependencyService as CrudComponent<T>
            RESOURCE -> resourceService as CrudComponent<T>
            PROJECT -> projectService as CrudComponent<T>
            ENTITY -> entityService as CrudComponent<T>
        }
    }
}