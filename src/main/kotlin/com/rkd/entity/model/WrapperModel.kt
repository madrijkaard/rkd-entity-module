/*
package com.rkd.entity.model

import com.fasterxml.jackson.annotation.JsonView
import com.rkd.entity.config.ViewConfig
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "wrapper")
class WrapperModel : AuditModel() {
    @field:NotBlank(message = "O campo 'wrapper' é obrigatório")
    @JsonView(ViewConfig.Public::class)
    @Enumerated(EnumType.STRING)
    @Column(name = "wrapper", nullable = false)
    val wrapperType: WrapperModel? = null
}*/
