package com.jr.mtgcards.domain.card

import com.jr.mtgcards.domain.card.dto.CheckImagesStatusResponseDTO
import com.jr.mtgcards.domain.card.dto.RequestImagesBodyDTO
import com.jr.mtgcards.domain.card.dto.RequestImagesResponseDTO
import com.jr.mtgcards.domain.card.service.CardImagesService
import java.util.*
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("/cards")
class CardController(private val cardImagesService: CardImagesService) {

  @PostMapping
  suspend fun requestImages(@RequestBody body: RequestImagesBodyDTO): RequestImagesResponseDTO {
    return cardImagesService.requestImages(body)
  }

  @GetMapping("/{operationId}")
  suspend fun checkImagesStatus(@PathVariable operationId: UUID): CheckImagesStatusResponseDTO {
    return cardImagesService.checkImagesStatus(operationId)
  }
}
