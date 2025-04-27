package com.rkd.entity.model

import com.fasterxml.jackson.annotation.JsonView
import com.fasterxml.jackson.databind.JsonNode
import com.rkd.entity.config.ViewConfig
import com.rkd.entity.converter.JsonConverter
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "dependency")
class DependencyModel : AuditModel() {
    @field:NotBlank(message = "O campo 'structure' não pode estar em branco")
    @JsonView(ViewConfig.Public::class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Convert(converter = JsonConverter::class)
    @Column(name = "structure", columnDefinition = "jsonb", nullable = false)
    var structure: JsonNode? = null

    @JsonView(ViewConfig.Public::class)
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    var projectModel: ProjectModel? = null
}