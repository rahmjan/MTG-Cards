package com.jr.mtgcards.domain.card.entity

import com.jr.mtgcards.library.repository.BaseEntity
import java.util.*
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("image_url")
data class ImageUrlEntity(
  @Column("id") @Id val id: UUID,
  @Column("operation_id") val operationId: UUID,
  @Column("name") val name: String,
  @Column("png_url") val pngUrl: String?,
  @Column("error") val error: String?,
) : BaseEntity<UUID>(id)
