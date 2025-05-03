package com.rkd.entity.model

import com.fasterxml.jackson.annotation.JsonView
import com.fasterxml.jackson.databind.JsonNode
import com.rkd.entity.component.ViewComponent.Public
import com.rkd.entity.converter.JsonConverter
import com.rkd.entity.definition.MessageDefinition.Dependency
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "dependency")
class DependencyModel : AuditModel() {
    @field:NotNull(message = Dependency.STRUCTURE_CANNOT_BLANK)
    @JsonView(Public::class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Convert(converter = JsonConverter::class)
    @Column(name = "structure", columnDefinition = "jsonb", nullable = false)
    var structure: JsonNode? = null

    @field:NotBlank(message = Dependency.PROJECT_ID_CANNOT_BLANK)
    @JsonView(Public::class)
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    var projectModel: ProjectModel? = null
}