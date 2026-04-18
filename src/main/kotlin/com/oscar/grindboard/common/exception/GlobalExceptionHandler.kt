package com.oscar.grindboard.common.exception

import com.oscar.grindboard.common.model.ErrorDetail
import com.oscar.grindboard.common.model.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import reactor.core.publisher.Mono

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(WebExchangeBindException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleWebExchangeBindException(ex: WebExchangeBindException): Mono<ErrorResponse> {
        val details =
            ex.fieldErrors.map {
                ErrorDetail(field = it.field, message = it.defaultMessage ?: "Invalid value")
            }

        return Mono.just(
            ErrorResponse(HttpStatus.BAD_REQUEST.name, message = "Validation error", details),
        )
    }

    @ExceptionHandler(NoSuchElementException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFound(ex: NoSuchElementException): Mono<ErrorResponse> =
        Mono.just(
            ErrorResponse(HttpStatus.NOT_FOUND.name, message = ex.message ?: "Element not found"),
        )

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGeneralException(ex: Exception): Mono<ErrorResponse> =
        Mono.just(
            ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.name,
                message = ex.message ?: "Unknown exception occurred",
            ),
        )
}
