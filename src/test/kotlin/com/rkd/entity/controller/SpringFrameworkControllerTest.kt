package com.rkd.entity.controller

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.rkd.entity.config.TestConfig
import com.rkd.entity.repository.SpringFrameworkRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig::class)
class SpringFrameworkControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var springFrameworkRepository: SpringFrameworkRepository

    @AfterEach
    fun tearDown() {
        springFrameworkRepository.deleteAll()
    }

    @Test
    fun `deve criar uma entidade`() {

        val parentNode = objectMapper.createObjectNode()
            .put("groupId", "org.springframework.boot")
            .put("artifactId", "spring-boot-starter-parent")
            .put("version", "3.4.3")
            .put("relativePath", "")

        val structureNode = objectMapper.createObjectNode()
            .set<JsonNode>("parent", parentNode)

        val request = objectMapper.writeValueAsString(
            mapOf(
                "name" to "3.4.3",
                "structure" to structureNode
            )
        )

        mockMvc.perform(
            post("/springframeworks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isCreated)
    }
}