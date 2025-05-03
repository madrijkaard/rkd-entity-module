package com.rkd.entity.component

import com.rkd.entity.dto.ExceptionDto
import com.rkd.entity.exception.IdentifierAlreadyExistsException
import com.rkd.entity.exception.QueryNotFoundException
import com.rkd.entity.exception.ValidationException
import com.rkd.entity.type.ExceptionType.INVALID_INPUT_FIELD
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlerComponent {

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(ex: ValidationException): ResponseEntity<ExceptionDto> {

        val exceptionDto = ExceptionDto(
            code = ex.code,
            message = ex.description
        )
        return ResponseEntity(exceptionDto, BAD_REQUEST)
    }

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

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ExceptionDto> {

        val message = ex.bindingResult.fieldErrors.firstOrNull()?.defaultMessage ?: INVALID_INPUT_FIELD.message

        val exceptionDto = ExceptionDto(
            code = INVALID_INPUT_FIELD.code,
            message = message
        )

        return ResponseEntity(exceptionDto, BAD_REQUEST)
    }
}