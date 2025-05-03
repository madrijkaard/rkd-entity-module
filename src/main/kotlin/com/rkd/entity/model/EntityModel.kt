package com.rkd.entity.model

import com.fasterxml.jackson.annotation.JsonView
import com.fasterxml.jackson.databind.JsonNode
import com.rkd.entity.component.ViewComponent
import com.rkd.entity.converter.JsonConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "entity")
class EntityModel : AuditModel() {
    @field:NotBlank(message = "{field.structure.cannot.blank}")
    @JsonView(ViewComponent.Public::class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Convert(converter = JsonConverter::class)
    @Column(columnDefinition = "jsonb", nullable = false)
    var structure: JsonNode? = null

    @JsonView(ViewComponent.Public::class)
    @ManyToOne
    @JoinColumn(name = "use_case_id", nullable = false)
    var useCaseModel: UseCaseModel? = null
}