package com.oscar.grindboard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GrindboardApplication

fun main(args: Array<String>) {
	runApplication<GrindboardApplication>(*args)
}
