package com.rkd.entity.converter

import com.rkd.entity.type.StatusType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class StatusConverter : AttributeConverter<StatusType, String> {

    override fun convertToDatabaseColumn(statusType: StatusType): String {
        return statusType.code
    }

    override fun convertToEntityAttribute(statusType: String): StatusType {
        return StatusType.fromCode(statusType)
    }
}