package com.jr.mtgcards.domain.card.entity

import com.jr.mtgcards.domain.card.model.ProcessingStatus
import com.jr.mtgcards.library.repository.BaseEntity
import java.util.*
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("images_process")
data class ImagesProcessEntity(
  @Column("operation_id") @Id val operationId: UUID,
  @Column("status") val status: ProcessingStatus,
) : BaseEntity<UUID>(operationId)
