package com.jr.mtgcards.domain.card.cron

import com.jr.mtgcards.domain.card.repository.ImageUrlCacheRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ImageUrlCacheCronService(private val imageUrlCacheRepository: ImageUrlCacheRepository) {
  @Scheduled(cron = "0 0 3 * * *")
  suspend fun clearCacheDaily() {
    imageUrlCacheRepository.deleteAll()
  }
}
