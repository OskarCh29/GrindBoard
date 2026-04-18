package com.oscar.grindboard

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<GrindboardApplication>().with(TestcontainersConfiguration::class).run(*args)
}
