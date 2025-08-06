package com.jr.mtgcards.domain.card.repository

import com.jr.mtgcards.domain.card.entity.ImageUrlEntity
import java.util.*
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageUrlRepository : CoroutineCrudRepository<ImageUrlEntity, UUID> {
  suspend fun findAllByOperationIdAndPngUrlIsNotNull(operationId: UUID): List<ImageUrlEntity>

  suspend fun findAllByOperationIdAndErrorIsNotNull(operationId: UUID): List<ImageUrlEntity>
}
