package com.jr.mtgcards.domain.card.entity

import com.jr.mtgcards.library.repository.BaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("image_url_cache")
data class ImageUrlCacheEntity(
  @Column("name") @Id val name: String,
  @Column("png_url") val pngUrl: String,
) : BaseEntity<String>(name)
