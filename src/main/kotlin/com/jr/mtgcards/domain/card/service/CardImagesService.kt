package com.jr.mtgcards.domain.card.service

import com.jr.mtgcards.domain.card.dto.*
import com.jr.mtgcards.domain.card.model.ProcessingStatus
import com.jr.mtgcards.domain.card.repository.ImageUrlRepository
import com.jr.mtgcards.domain.card.repository.ImagesProcessRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CardImagesService(
  private val cardImagesProcessingService: CardImagesProcessingService,
  private val imagesProcessRepository: ImagesProcessRepository,
  private val imageUrlRepository: ImageUrlRepository,
) {

  suspend fun requestImages(dto: RequestImagesBodyDTO): RequestImagesResponseDTO {
    val operationId = cardImagesProcessingService.processImagesAsync(dto.cardNames)
    return RequestImagesResponseDTO(operationId)
  }

  suspend fun checkImagesStatus(operationId: UUID): CheckImagesStatusResponseDTO {
    val imgProcessEntity = imagesProcessRepository.findById(operationId)

    if (imgProcessEntity == null)
      return CheckImagesStatusResponseDTO.failure(operationId, "OperationId not found")

    when (imgProcessEntity.status) {
      ProcessingStatus.FAILURE ->
        return CheckImagesStatusResponseDTO.failure(operationId, "All requests failed")
      ProcessingStatus.PROCESSING -> return CheckImagesStatusResponseDTO.processing(operationId)
      else -> {}
    }

    val resultEntities = imageUrlRepository.findAllByOperationIdAndPngUrlIsNotNull(operationId)
    val failureEntities = imageUrlRepository.findAllByOperationIdAndErrorIsNotNull(operationId)

    val results = resultEntities.map { Result(it.name, it.pngUrl ?: "Unknown") }
    val failures = failureEntities.map { Failure(it.name, it.error ?: "Unknown") }

    return CheckImagesStatusResponseDTO(
      operationId,
      imgProcessEntity.status,
      results,
      failures,
      null,
    )
  }
}
