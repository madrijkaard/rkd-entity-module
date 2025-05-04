package com.rkd.entity.listener

import com.rkd.entity.component.ValidationComponent
import com.rkd.entity.exception.ValidationException
import com.rkd.entity.model.ProjectModel
import com.rkd.entity.type.ExceptionType.INVALID_INPUT_FIELD_CUSTOM
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate

class ProjectListener : ValidationComponent<ProjectModel> {
    @PreUpdate
    @PrePersist
    override fun validate(model: ProjectModel) {
        if (model.isStructureValid()) {
            throw ValidationException(INVALID_INPUT_FIELD_CUSTOM, "structure")
        }
    }
}