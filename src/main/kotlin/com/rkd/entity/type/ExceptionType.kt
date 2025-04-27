package com.rkd.entity.type

enum class ExceptionType(val code: String, private val message: String) {

    QUERY_NOT_FOUND("QUERY_NOT_FOUND", "The query returned no result"),
    IDENTIFIER_ALREADY_EXISTS("IDENTIFIER_ALREADY_EXISTS", "The identifier already exists");

    fun formatMessage(vararg args: Any): String {
        return if (args.isNotEmpty()) message.format(*args) else message
    }
}