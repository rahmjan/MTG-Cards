package com.jr.mtgcards.utils.exception

import com.jr.mtgcards.utils.logger.log
import kotlinx.coroutines.CoroutineExceptionHandler

val coroutineExceptionHandler = CoroutineExceptionHandler { _, ex ->
  log.error(ex) { "Unhandled coroutine exception" }
}
