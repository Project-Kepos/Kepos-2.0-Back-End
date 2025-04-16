package com.kepos.backend.config.error

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorsHandler {

    /* Formatando erros 404 */
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleError404(exception: EntityNotFoundException): ResponseEntity<Any> {
        return ResponseEntity.notFound().build()
    }

    /* Formatando erros 400 por validações */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleError400(exception: MethodArgumentNotValidException): ResponseEntity<Any> {
        val errors = exception.fieldErrors
        return ResponseEntity.badRequest().body(errors.map { ValidationErrorsDTO(it) })
    }

    /* Formatando erros customizados */
    @ExceptionHandler(ErroCustomizado::class)
    fun handleCustomError(exception: ErroCustomizado): ResponseEntity<Any> {
        return ResponseEntity.badRequest().body(ErroCustomizadoDTO(exception))
    }

    private data class ValidationErrorsDTO(val field: String, val message: String?) {
        constructor(error: FieldError) : this(error.field, error.defaultMessage)
    }

    private data class ErroCustomizadoDTO(val message: String?) {
        constructor(error: ErroCustomizado) : this(error.message)
    }
}