package com.rkd.entity.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonView
import com.fasterxml.jackson.databind.JsonNode
import com.rkd.entity.component.ViewComponent.Public
import com.rkd.entity.converter.JsonConverter
import com.rkd.entity.listener.SpringFrameworkListener
import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "spring_framework")
@EntityListeners(SpringFrameworkListener::class)
class SpringFrameworkModel : AuditModel() {
    @JsonView(Public::class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Convert(converter = JsonConverter::class)
    @Column(name = "structure", columnDefinition = "jsonb", nullable = false)
    var structure: JsonNode? = null

    @JsonView(Public::class)
    @JsonManagedReference
    @OneToMany(mappedBy = "springFrameworkModel", cascade = [ALL], orphanRemoval = true)
    var projects: List<ProjectModel> = mutableListOf()

    fun isStructureValid(): Boolean {
        return structure == null || structure!!.isEmpty ||
                (structure!!.isTextual && structure!!.textValue().isBlank())
    }
}