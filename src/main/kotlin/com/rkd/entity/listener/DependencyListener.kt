package com.rkd.entity.listener

import com.rkd.entity.component.ValidationComponent
import com.rkd.entity.exception.ValidationException
import com.rkd.entity.model.DependencyModel
import com.rkd.entity.type.ExceptionType.INVALID_INPUT_FIELD_CUSTOM
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate

class DependencyListener : ValidationComponent<DependencyModel> {
    @PreUpdate
    @PrePersist
    override fun validate(model: DependencyModel) {
        if (model.isStructureValid()) {
            throw ValidationException(INVALID_INPUT_FIELD_CUSTOM, "structure")
        }
    }
}