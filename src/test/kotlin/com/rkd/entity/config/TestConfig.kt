package com.rkd.entity.config

import com.rkd.entity.container.PostgresContainer
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@TestConfiguration
class TestConfig {
    @Bean
    fun postgresContainer(): PostgresContainer {
        return PostgresContainer
    }
}