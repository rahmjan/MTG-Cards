package com.jr.mtgcards.library.external.scryfall

import com.jr.mtgcards.library.external.rest.RateLimiter
import com.jr.mtgcards.library.external.rest.WebClientWrapper
import com.jr.mtgcards.library.external.scryfall.model.ScryfallCard
import org.springframework.stereotype.Component

@Component
class ScryfallApi {
  private val webClient: WebClientWrapper = WebClientWrapper("https://api.scryfall.com")
  private val rateLimiter = RateLimiter(100)

  companion object {
    private const val CARDS_NAMED = "/cards/named"
    private const val EXACT = "exact"
  }

  suspend fun getNamedCard(name: String): ScryfallCard? {
    val result =
      rateLimiter.submit { webClient.get(CARDS_NAMED, ScryfallCard::class, mapOf(EXACT to name)) }
    return result.await()
  }
}
