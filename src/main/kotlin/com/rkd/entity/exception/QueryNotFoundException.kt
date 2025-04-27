package com.rkd.entity.exception

import com.rkd.entity.type.ExceptionType

class QueryNotFoundException(exceptionType: ExceptionType, vararg args: Any) : RuntimeException() {
    val code = exceptionType.code
    val description = exceptionType.formatMessage(*args)
}