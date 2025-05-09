package com.rkd.entity

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class EntityModuleApplication

fun main(args: Array<String>) {
	runApplication<EntityModuleApplication>(*args)
}
