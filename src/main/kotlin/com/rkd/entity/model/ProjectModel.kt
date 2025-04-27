package com.rkd.entity.model

import com.fasterxml.jackson.annotation.JsonView
import com.rkd.entity.config.ViewConfig
import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "project")
class ProjectModel : AuditModel() {
    @field:NotBlank(message = "{field.spring.framework.id.cannot.blank}")
    @JsonView(ViewConfig.Public::class)
    @ManyToOne
    @JoinColumn(name = "spring_framework_id", nullable = false)
    var springFrameworkModel: SpringFrameworkModel? = null

    @JsonView(ViewConfig.Public::class)
    @OneToMany(mappedBy = "projectModel", cascade = [ALL], orphanRemoval = true)
    var dependencies: List<DependencyModel> = mutableListOf()

    @JsonView(ViewConfig.Public::class)
    @OneToMany(mappedBy = "projectModel", cascade = [ALL], orphanRemoval = true)
    var resources: List<ResourceModel> = mutableListOf()

    @JsonView(ViewConfig.Public::class)
    @OneToMany(mappedBy = "projectModel", cascade = [ALL], orphanRemoval = true)
    var useCases: List<UseCaseModel> = mutableListOf()
}