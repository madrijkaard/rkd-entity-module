package com.rkd.entity.listener

import com.rkd.entity.component.ValidationComponent
import com.rkd.entity.exception.ValidationException
import com.rkd.entity.model.SpringFrameworkModel
import com.rkd.entity.type.ExceptionType.INVALID_INPUT_FIELD_CUSTOM
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate

class SpringFrameworkListener : ValidationComponent<SpringFrameworkModel> {
    @PreUpdate
    @PrePersist
    override fun validate(model: SpringFrameworkModel) {
        if (model.isStructureNullOrEmpty()) {
            throw ValidationException(INVALID_INPUT_FIELD_CUSTOM, "structure")
        }
    }
}