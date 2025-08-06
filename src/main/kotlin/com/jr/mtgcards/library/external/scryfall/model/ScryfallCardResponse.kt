package com.jr.mtgcards.library.external.scryfall.model

import java.util.*

data class ScryfallCard(val id: UUID, val name: String, val image_uris: ImageUris)
