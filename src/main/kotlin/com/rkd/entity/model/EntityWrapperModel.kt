package com.rkd.entity.model

import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "entity_wrapper")
class EntityWrapperModel : AuditModel() {
}