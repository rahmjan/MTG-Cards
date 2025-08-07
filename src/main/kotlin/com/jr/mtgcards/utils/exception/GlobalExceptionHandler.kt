package com.jr.mtgcards.utils.exception

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
  @ExceptionHandler(Exception::class)
  fun logAll(ex: Exception): Void? {
    // Hack to log ERROR
    return null
  }
}
