package com.rkd.entity.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonView
import com.fasterxml.jackson.databind.JsonNode
import com.rkd.entity.component.ViewComponent.Public
import com.rkd.entity.converter.JsonConverter
import com.rkd.entity.definition.MessageDefinition.Project
import com.rkd.entity.listener.ProjectListener
import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "project")
@EntityListeners(ProjectListener::class)
class ProjectModel : AuditModel() {
    @JsonView(Public::class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Convert(converter = JsonConverter::class)
    @Column(name = "structure", columnDefinition = "jsonb", nullable = false)
    var structure: JsonNode? = null

    @field:NotNull(message = Project.SPRING_FRAMEWORK_ID_CANNOT_BLANK)
    @JsonView(Public::class)
    @JsonBackReference
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

    fun isStructureValid(): Boolean {
        return structure == null || structure!!.isEmpty ||
                (structure!!.isTextual && structure!!.textValue().isBlank())
    }
}