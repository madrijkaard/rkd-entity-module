package com.rkd.entity.config

import com.rkd.entity.dto.ExceptionDto
import com.rkd.entity.exception.IdentifierAlreadyExistsException
import com.rkd.entity.exception.QueryNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.BAD_REQUEST

@RestControllerAdvice
class ExceptionHandlerConfig {

    @ExceptionHandler(QueryNotFoundException::class)
    fun handleQueryNotFoundException(ex: QueryNotFoundException): ResponseEntity<ExceptionDto> {

        val exceptionDto = ExceptionDto(
            code = ex.code,
            message = ex.description
        )

        return ResponseEntity(exceptionDto, NOT_FOUND)
    }

    @ExceptionHandler(IdentifierAlreadyExistsException::class)
    fun handleIdentifierAlreadyExistsException(ex: IdentifierAlreadyExistsException): ResponseEntity<ExceptionDto> {

        val exceptionDto = ExceptionDto(
            code = ex.code,
            message = ex.description
        )

        return ResponseEntity(exceptionDto, BAD_REQUEST)
    }
}