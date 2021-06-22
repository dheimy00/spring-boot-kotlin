package br.com.produto.exception

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class WebApiExceptionHandler: ResponseEntityExceptionHandler() {


    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val errors = mutableListOf<String>()
        ex.bindingResult.fieldErrors.forEach { errors.add("${it.field}: ${it.defaultMessage}") }
        ex.bindingResult.globalErrors.forEach { errors.add("${it.objectName}: ${it.defaultMessage}") }
        val apiError = ApiError("Bad Request",HttpStatus.BAD_REQUEST, ex.localizedMessage, errors)
        return handleExceptionInternal(ex, apiError, headers, apiError.status, request)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleAll(ex: ResourceNotFoundException, request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError("Resource Not Found",HttpStatus.NOT_FOUND, ex.localizedMessage, "error occurred",ex::class.java.name)
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

}