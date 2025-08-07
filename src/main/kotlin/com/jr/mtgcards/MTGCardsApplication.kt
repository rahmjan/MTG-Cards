package com.jr.mtgcards

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling @SpringBootApplication class MTGCardsApplication

fun main(args: Array<String>) {
  runApplication<MTGCardsApplication>(*args)
}
