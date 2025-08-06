package com.jr.mtgcards.domain.card.service

import com.jr.mtgcards.domain.card.entity.ImageUrlEntity
import com.jr.mtgcards.domain.card.entity.ImagesProcessEntity
import com.jr.mtgcards.domain.card.model.ProcessingStatus
import com.jr.mtgcards.domain.card.repository.ImageUrlRepository
import com.jr.mtgcards.domain.card.repository.ImagesProcessRepository
import com.jr.mtgcards.library.external.scryfall.ScryfallApi
import com.jr.mtgcards.utils.logger.log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

@Service
class CardImagesProcessingService(
  private val imagesProcessRepository: ImagesProcessRepository,
  private val imageUrlRepository: ImageUrlRepository,
  private val scryfallApi: ScryfallApi,
) {

  companion object {
    private const val COROUTINE_COUNT = 20
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
  }

  suspend fun processImagesAsync(cardNames: Set<String>): UUID {
    val operationId = UUID.randomUUID()
    imagesProcessRepository.save(
      ImagesProcessEntity(operationId, ProcessingStatus.PROCESSING).apply { insert = true }
    )
    coroutineScope.launch { processCardNames(operationId, cardNames) }
    return operationId
  }

  private suspend fun processCardNames(operationId: UUID, cardNames: Set<String>) {
    val successCount = AtomicInteger(0)
    val channel = Channel<String>(Channel.UNLIMITED)

    val workers =
      List(COROUTINE_COUNT) {
        coroutineScope.launch {
          for (cardName in channel) {
            val success = processCard(operationId, cardName)
            if (success) successCount.incrementAndGet()
          }
        }
      }

    cardNames.forEach { cardName -> channel.send(cardName) }

    channel.close()
    workers.joinAll()

    val finalStatus =
      when {
        successCount.get() == cardNames.size -> ProcessingStatus.SUCCESS
        successCount.get() > 0 -> ProcessingStatus.PARTIAL_SUCCESS
        else -> ProcessingStatus.FAILURE
      }
    imagesProcessRepository.updateStatus(operationId, finalStatus)
  }

  private suspend fun processCard(operationId: UUID, cardName: String): Boolean {
    val cName = cardName.trim()

    val entity =
      runCatching {
          val response = scryfallApi.getNamedCard(cName)
          ImageUrlEntity(
            id = UUID.randomUUID(),
            operationId = operationId,
            name = cName,
            pngUrl = response?.image_uris?.png,
            error = if (response == null) "Card not found" else null,
          )
        }
        .getOrElse { ex ->
          log.error { "ProcessCard error: $ex" }
          ImageUrlEntity(
            id = UUID.randomUUID(),
            operationId = operationId,
            name = cName,
            pngUrl = null,
            error = ex.message ?: "Unknown error",
          )
        }
        .apply { insert = true }

    imageUrlRepository.save(entity)

    return entity.error == null
  }
}
