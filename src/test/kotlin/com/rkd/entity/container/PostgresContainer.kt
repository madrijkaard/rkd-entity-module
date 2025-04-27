package com.rkd.entity.container

import com.rkd.entity.config.ContainerConfig
import org.springframework.stereotype.Component
import org.springframework.test.context.DynamicPropertyRegistry
import org.testcontainers.containers.PostgreSQLContainer
import java.util.*

@Component
object PostgresContainer : ContainerConfig {

    private val postgresSqlContainer = PostgreSQLContainer("postgres:latest")
        .apply {
            withDatabaseName("entity-module")
            withUsername(UUID.randomUUID().toString())
            withPassword(UUID.randomUUID().toString())
            start()
        }

    override fun configureProperties(registry: DynamicPropertyRegistry) {
        registry.add("spring.datasource.url") { postgresSqlContainer.jdbcUrl }
        registry.add("spring.datasource.username") { postgresSqlContainer.username }
        registry.add("spring.datasource.password") { postgresSqlContainer.password }
    }

    override fun close() {
        postgresSqlContainer.stop()
    }
}
