package com.jr.mtgcards.domain.card.repository

import com.jr.mtgcards.domain.card.entity.ImageUrlCacheEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageUrlCacheRepository : CoroutineCrudRepository<ImageUrlCacheEntity, String> {}
