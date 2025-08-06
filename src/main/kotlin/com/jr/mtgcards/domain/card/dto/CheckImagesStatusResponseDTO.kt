package com.jr.mtgcards.domain.card.dto

import com.jr.mtgcards.domain.card.model.ProcessingStatus
import java.util.*

data class CheckImagesStatusResponseDTO(
  val operationId: UUID,
  val status: ProcessingStatus,
  val results: List<Result>?,
  val failures: List<Failure>?,
  val error: String?,
) {
  companion object {
    fun failure(operationId: UUID, error: String?) =
      CheckImagesStatusResponseDTO(operationId, ProcessingStatus.FAILURE, null, null, error)

    fun processing(operationId: UUID) =
      CheckImagesStatusResponseDTO(operationId, ProcessingStatus.PROCESSING, null, null, null)
  }
}
