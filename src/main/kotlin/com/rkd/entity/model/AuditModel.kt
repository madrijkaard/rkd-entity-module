package com.rkd.entity.model

import com.fasterxml.jackson.annotation.JsonView
import com.rkd.entity.config.ViewConfig
import com.rkd.entity.converter.StatusConverter
import com.rkd.entity.definition.MessageDefinition.Audit
import com.rkd.entity.type.StatusType
import com.rkd.entity.type.StatusType.ATIVO
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AuditModel {
    @JsonView(ViewConfig.Internal::class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @field:NotBlank(message = Audit.NAME_CANNOT_BLANK)
    @JsonView(ViewConfig.Public::class)
    @Column(nullable = false)
    var name: String? = null

    @JsonView(ViewConfig.Internal::class)
    @Convert(converter = StatusConverter::class)
    @Column(nullable = false, length = 1)
    var status: StatusType = ATIVO

    @JsonView(ViewConfig.Internal::class)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null

    @JsonView(ViewConfig.Internal::class)
    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: LocalDateTime? = null
}