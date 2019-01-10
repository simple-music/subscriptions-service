package ru.bmstu.iu7.simplemusic.subscriptionsservice.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.bmstu.iu7.simplemusic.subscriptionsservice.exception.NotFoundException
import ru.bmstu.iu7.simplemusic.subscriptionsservice.exception.ValidationException
import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.Error

@RestControllerAdvice
class ExceptionMapper {
    @ExceptionHandler(ValidationException::class)
    fun handleDuplicateException(exception: ValidationException): ResponseEntity<Error> {
        val error = Error(exception.message!!)
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(error)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(exception: NotFoundException): ResponseEntity<Error> {
        val error = Error(exception.message!!)
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error)
    }
}
