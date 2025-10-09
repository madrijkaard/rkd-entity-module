package org.rkd.domain.listener

import jakarta.persistence.EntityListeners
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import org.rkd.domain.model.UserProjectModel
import java.time.LocalDateTime

@EntityListeners
class UserProjectListener {

    @PrePersist
    fun setPrePersist(model: UserProjectModel) {
        model.createdAt = LocalDateTime.now()
        model.updatedAt = LocalDateTime.now()
    }

    @PreUpdate
    fun setUpdatedAt(model: UserProjectModel) {
        model.updatedAt = LocalDateTime.now()
    }
}