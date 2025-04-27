package com.rkd.entity.model

import com.fasterxml.jackson.annotation.JsonView
import com.fasterxml.jackson.databind.JsonNode
import com.rkd.entity.config.ViewConfig
import com.rkd.entity.converter.JsonConverter
import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.validation.constraints.NotBlank
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "use_case")
class UseCaseModel : AuditModel() {
    @field:NotBlank(message = "{field.description.cannot.blank}")
    @JsonView(ViewConfig.Public::class)
    @Column(nullable = false)
    var description: String? = null

    @field:NotBlank(message = "{field.structure.cannot.blank}")
    @JsonView(ViewConfig.Public::class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Convert(converter = JsonConverter::class)
    @Column(name = "structure", columnDefinition = "jsonb", nullable = false)
    var structure: JsonNode? = null

    @JsonView(ViewConfig.Public::class)
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    var projectModel: ProjectModel? = null

    @JsonView(ViewConfig.Public::class)
    @OneToMany(mappedBy = "useCaseModel", cascade = [ALL], orphanRemoval = true)
    var entities: List<EntityModel> = mutableListOf()
}