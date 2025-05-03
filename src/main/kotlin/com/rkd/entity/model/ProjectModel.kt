package com.rkd.entity.model

import com.fasterxml.jackson.annotation.JsonView
import com.rkd.entity.component.ViewComponent.Public
import com.rkd.entity.definition.MessageDefinition.Project
import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "project")
class ProjectModel : AuditModel() {
    @field:NotBlank(message = Project.SPRING_FRAMEWORK_ID_CANNOT_BLANK)
    @JsonView(Public::class)
    @ManyToOne
    @JoinColumn(name = "spring_framework_id", nullable = false)
    var springFrameworkModel: SpringFrameworkModel? = null

    @JsonView(Public::class)
    @OneToMany(mappedBy = "projectModel", cascade = [ALL], orphanRemoval = true)
    var dependencies: List<DependencyModel> = mutableListOf()

    @JsonView(Public::class)
    @OneToMany(mappedBy = "projectModel", cascade = [ALL], orphanRemoval = true)
    var resources: List<ResourceModel> = mutableListOf()

    @JsonView(Public::class)
    @OneToMany(mappedBy = "projectModel", cascade = [ALL], orphanRemoval = true)
    var useCases: List<UseCaseModel> = mutableListOf()
}