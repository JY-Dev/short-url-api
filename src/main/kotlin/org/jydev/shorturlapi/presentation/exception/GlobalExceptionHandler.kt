package org.jydev.shorturlapi.presentation.exception

import org.jydev.shorturlapi.presentation.model.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(exception: MethodArgumentNotValidException): ErrorResponse {
        val errors = exception.bindingResult.fieldErrors.joinToString(", ") { it.defaultMessage ?: "Invalid value" }
        return ErrorResponse(errors)
    }
}