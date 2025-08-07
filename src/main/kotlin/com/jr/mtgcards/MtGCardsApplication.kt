package com.jr.mtgcards

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling @SpringBootApplication class MtGCardsApplication

fun main(args: Array<String>) {
  runApplication<MtGCardsApplication>(*args)
}
