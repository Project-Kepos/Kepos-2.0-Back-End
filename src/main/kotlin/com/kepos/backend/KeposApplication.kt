package com.kepos.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KeposApplication

fun main(args: Array<String>) {
    runApplication<KeposApplication>(*args)
}