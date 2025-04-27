package com.rkd.entity.config

import jakarta.annotation.PreDestroy
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

interface ContainerConfig {
    @DynamicPropertySource
    fun configureProperties(registry: DynamicPropertyRegistry)
    @PreDestroy
    fun close()
}