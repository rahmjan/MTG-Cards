package com.jr.mtgcards.library.external.rest

import kotlin.reflect.KClass
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

class WebClientWrapper(val apiUrl: String) {
  private val restClient = WebClient.create()

  suspend fun <T : Any> get(
    endpoint: String,
    responseCls: KClass<T>,
    params: Map<String, String> = emptyMap(),
  ): T? {
    return restClient
      .get()
      .uri(apiUrl + endpoint) { uriBuilder ->
        uriBuilder.apply { params.forEach { (key, value) -> queryParam(key, value) } }.build()
      }
      .exchangeToMono { response ->
        when {
          response.statusCode() == HttpStatus.NOT_FOUND -> Mono.empty()
          response.statusCode().is2xxSuccessful -> response.bodyToMono(responseCls.java)
          else -> response.createError()
        }
      }
      .awaitSingleOrNull()
  }
}
