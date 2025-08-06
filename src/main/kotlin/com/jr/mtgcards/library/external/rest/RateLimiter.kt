package com.jr.mtgcards.library.external.rest

import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

class RateLimiter(private val delayMs: Long) {

  private val requestChannel = Channel<suspend () -> Unit>(Channel.BUFFERED)
  private val isStarted = AtomicBoolean(false)

  private fun start() {
    if (isStarted.compareAndSet(false, true)) {
      CoroutineScope(Dispatchers.IO).launch {
        while (isActive) {
          val task = requestChannel.receive()
          task()
          delay(delayMs)
        }
      }
    }
  }

  suspend fun <T> submit(task: suspend () -> T): Deferred<T> {
    start()
    val deferred = CompletableDeferred<T>()
    requestChannel.send {
      try {
        deferred.complete(task())
      } catch (e: Exception) {
        deferred.completeExceptionally(e)
      }
    }
    return deferred
  }
}
