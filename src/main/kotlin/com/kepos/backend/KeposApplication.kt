package com.kepos.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.kepos.backend"])
class KeposApplication

fun main(args: Array<String>) {
    runApplication<KeposApplication>(*args)
}