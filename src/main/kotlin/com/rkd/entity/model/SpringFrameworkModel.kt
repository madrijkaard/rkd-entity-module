package com.rkd.entity.model

import com.fasterxml.jackson.annotation.JsonView
import com.fasterxml.jackson.databind.JsonNode
import com.rkd.entity.config.ViewConfig
import com.rkd.entity.converter.JsonConverter
import com.rkd.entity.definition.MessageDefinition.SpringFramework
import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.validation.constraints.NotBlank
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "spring-framework")
class SpringFrameworkModel : AuditModel() {
    @field:NotBlank(message = SpringFramework.STRUCTURE_CANNOT_BLANK)
    @JsonView(ViewConfig.Public::class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Convert(converter = JsonConverter::class)
    @Column(name = "structure", columnDefinition = "jsonb", nullable = false)
    var structure: JsonNode? = null

    @JsonView(ViewConfig.Public::class)
    @OneToMany(mappedBy = "springFrameworkModel", cascade = [ALL], orphanRemoval = true)
    var projects: List<ProjectModel> = mutableListOf()
}