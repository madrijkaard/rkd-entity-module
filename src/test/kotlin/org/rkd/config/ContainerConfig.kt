package org.rkd.config

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.PostgreSQLContainer

class ContainerConfig : QuarkusTestResourceLifecycleManager {

    companion object {
        val postgres: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:16")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .apply { start() }

        val redis: GenericContainer<*> = GenericContainer("redis:7.2.5")
            .withExposedPorts(6379)
            .apply { start() }
    }

    override fun start(): Map<String, String> {
        return mapOf(
            "quarkus.datasource.jdbc.url" to postgres.jdbcUrl,
            "quarkus.datasource.username" to postgres.username,
            "quarkus.datasource.password" to postgres.password,
            "quarkus.datasource.db-kind" to "postgresql",

            "quarkus.redis.hosts" to "redis://localhost:${redis.firstMappedPort}"
        )
    }

    override fun stop() {
        postgres.stop()
        redis.stop()
    }
}
