package com.jr.mtgcards.domain.card.repository

import com.jr.mtgcards.domain.card.entity.ImagesProcessEntity
import com.jr.mtgcards.domain.card.model.ProcessingStatus
import java.util.*
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ImagesProcessRepository : CoroutineCrudRepository<ImagesProcessEntity, UUID> {
  @Modifying
  @Query("UPDATE images_process SET status = :status WHERE operation_id = :id")
  suspend fun updateStatus(id: UUID, status: ProcessingStatus)
}
