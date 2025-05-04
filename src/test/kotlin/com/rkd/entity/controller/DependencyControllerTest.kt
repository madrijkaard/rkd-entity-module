package com.rkd.entity.controller

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.rkd.entity.config.TestConfig
import com.rkd.entity.definition.MessageDefinition.Audit
import com.rkd.entity.repository.DependencyRepository
import com.rkd.entity.type.ExceptionType.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig::class)
class DependencyControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var dependencyRepository: DependencyRepository

    @AfterEach
    fun tearDown() {
        dependencyRepository.deleteAll()
    }

    @Test
    fun `should return all spring framework models`() {

        val parentNode = objectMapper.createObjectNode()
            .put("groupId", "org.springframework.boot")
            .put("artifactId", "spring-boot-starter-parent")
            .put("version", "3.4.3")
            .put("relativePath", "")

        val structureNode = objectMapper.createObjectNode().set<JsonNode>("parent", parentNode)

        val request1 = objectMapper.writeValueAsString(
            mapOf("name" to "spring-boot-1", "structure" to structureNode)
        )

        val request2 = objectMapper.writeValueAsString(
            mapOf("name" to "spring-boot-2", "structure" to structureNode)
        )

        mockMvc.perform(
            post("/dependencies")
                .contentType(APPLICATION_JSON)
                .content(request1)
        ).andExpect(status().isCreated)

        mockMvc.perform(
            post("/dependencies")
                .contentType(APPLICATION_JSON)
                .content(request2)
        ).andExpect(status().isCreated)

        mockMvc.perform(
            get("/dependencies")
        ).andExpect(status().isOk)
            .andExpect {
                val response = it.response.contentAsString
                val json = objectMapper.readTree(response)
                assertEquals(2, json.size())
                val names = json.map { node -> node.get("name").asText() }
                assert(names.contains("spring-boot-1"))
                assert(names.contains("spring-boot-2"))
            }
    }

    @Test
    fun `should fail when trying to create a spring framework model with null name field`() {

        val parentNode = objectMapper.createObjectNode()
            .put("groupId", "org.springframework.boot")
            .put("artifactId", "spring-boot-starter-parent")
            .put("version", "3.4.3")
            .put("relativePath", "")

        val structureNode = objectMapper.createObjectNode()
            .set<JsonNode>("parent", parentNode)

        val request = objectMapper.writeValueAsString(
            mapOf(
                "name" to null,
                "structure" to structureNode
            )
        )

        mockMvc.perform(
            post("/dependencies")
                .contentType(APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isBadRequest)
            .andExpect {
                val response = it.response.contentAsString
                val json = objectMapper.readTree(response)
                assertEquals(INVALID_INPUT_FIELD.code, json.get("code").asText())
                assertEquals(Audit.NAME_CANNOT_BLANK, json.get("message").asText())
            }
    }

    @Test
    fun `should fail when trying to create a spring framework model with blank name field`() {

        val parentNode = objectMapper.createObjectNode()
            .put("groupId", "org.springframework.boot")
            .put("artifactId", "spring-boot-starter-parent")
            .put("version", "3.4.3")
            .put("relativePath", "")

        val structureNode = objectMapper.createObjectNode()
            .set<JsonNode>("parent", parentNode)

        val request = objectMapper.writeValueAsString(
            mapOf(
                "name" to "",
                "structure" to structureNode
            )
        )

        mockMvc.perform(
            post("/dependencies")
                .contentType(APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isBadRequest)
            .andExpect {
                val response = it.response.contentAsString
                val json = objectMapper.readTree(response)
                assertEquals(INVALID_INPUT_FIELD.code, json.get("code").asText())
                assertEquals(Audit.NAME_CANNOT_BLANK, json.get("message").asText())
            }
    }

    @Test
    fun `should fail when trying to create a spring framework model with null structure field`() {

        val request = objectMapper.writeValueAsString(
            mapOf(
                "name" to "3.4.3",
                "structure" to null
            )
        )

        mockMvc.perform(
            post("/dependencies")
                .contentType(APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isBadRequest)
            .andExpect {
                val response = it.response.contentAsString
                val json = objectMapper.readTree(response)
                assertEquals(INVALID_INPUT_FIELD_CUSTOM.code, json.get("code").asText())
                assertEquals("Invalid structure field", json.get("message").asText())
            }
    }

    @Test
    fun `should fail when trying to create a spring framework model with blank structure field`() {

        val request = objectMapper.writeValueAsString(
            mapOf(
                "name" to "3.4.3",
                "structure" to ""
            )
        )

        mockMvc.perform(
            post("/dependencies")
                .contentType(APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isBadRequest)
            .andExpect {
                val response = it.response.contentAsString
                val json = objectMapper.readTree(response)
                assertEquals(INVALID_INPUT_FIELD_CUSTOM.code, json.get("code").asText())
                assertEquals("Invalid structure field", json.get("message").asText())
            }
    }

    @Test
    fun `must create a spring framework model successfully`() {

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
            post("/dependencies")
                .contentType(APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isCreated)

        val entity = dependencyRepository.findByName("3.4.3")
        assertNotNull(entity)
    }

    @Test
    fun `should fail when trying to create a spring framework model with duplicate name`() {

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
            post("/dependencies")
                .contentType(APPLICATION_JSON)
                .content(request)
        ).andExpect(status().isCreated)

        val entity = dependencyRepository.findByName("3.4.3")
        assertNotNull(entity)

        mockMvc.perform(
            post("/dependencies")
                .contentType(APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isBadRequest)
            .andExpect {
                val response = it.response.contentAsString
                val json = objectMapper.readTree(response)
                assertEquals(IDENTIFIER_ALREADY_EXISTS.code, json.get("code").asText())
                assertEquals(IDENTIFIER_ALREADY_EXISTS.message, json.get("message").asText())
            }
    }

    @Test
    fun `must update spring framework model successfully`() {

        val parentNode = objectMapper.createObjectNode()
            .put("groupId", "org.springframework.boot")
            .put("artifactId", "spring-boot-starter-parent")
            .put("version", "3.4.3")
            .put("relativePath", "")

        val initialStructure = objectMapper.createObjectNode().set<JsonNode>("parent", parentNode)

        val createRequest = objectMapper.writeValueAsString(
            mapOf("name" to "3.4.3", "structure" to initialStructure)
        )

        mockMvc.perform(
            post("/dependencies")
                .contentType(APPLICATION_JSON)
                .content(createRequest)
        ).andExpect(status().isCreated)

        val updatedStructure = objectMapper.createObjectNode()
            .put("description", "Updated Spring Framework")

        val updateRequest = objectMapper.writeValueAsString(
            mapOf("name" to "3.4.3", "structure" to updatedStructure)
        )

        mockMvc.perform(
            put("/dependencies/3.4.3")
                .contentType(APPLICATION_JSON)
                .content(updateRequest)
        ).andExpect(status().isNoContent)

        val updatedEntity = dependencyRepository.findByName("3.4.3")
        assertNotNull(updatedEntity)
        assertEquals("Updated Spring Framework", updatedEntity!!.structure?.get("description")?.asText())
    }

    @Test
    fun `should fail to update a non-existent spring framework model`() {

        val structure = objectMapper.createObjectNode().put("info", "non-existent update")

        val updateRequest = objectMapper.writeValueAsString(
            mapOf("name" to "not-found", "structure" to structure)
        )

        mockMvc.perform(
            put("/dependencies/not-found")
                .contentType(APPLICATION_JSON)
                .content(updateRequest)
        ).andExpect(status().isNotFound)
    }

    @Test
    fun `must delete spring framework model successfully`() {

        val structure = objectMapper.createObjectNode().put("info", "delete me")

        val request = objectMapper.writeValueAsString(
            mapOf("name" to "delete-me", "structure" to structure)
        )

        mockMvc.perform(
            post("/dependencies")
                .contentType(APPLICATION_JSON)
                .content(request)
        ).andExpect(status().isCreated)

        mockMvc.perform(
            delete("/dependencies/delete-me")
        ).andExpect(status().isNoContent)

        val entity = dependencyRepository.findByName("delete-me")
        assertEquals(null, entity)
    }

    @Test
    fun `should fail to delete a non-existent spring framework model`() {
        mockMvc.perform(
            delete("/dependencies/does-not-exist")
        )
            .andExpect(status().isNotFound)
    }
}