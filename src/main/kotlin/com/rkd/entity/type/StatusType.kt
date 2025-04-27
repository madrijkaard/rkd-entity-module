package com.rkd.entity.type

enum class StatusType(val code: String) {

    ATIVO("A"),
    INATIVO("I");

    companion object {
        fun fromCode(code: String): StatusType {
            return entries.firstOrNull { it.code == code }
                ?: throw IllegalArgumentException("Status inválido: $code")
        }
    }
}