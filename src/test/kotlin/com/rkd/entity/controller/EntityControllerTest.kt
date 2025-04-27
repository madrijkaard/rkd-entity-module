/*
package com.rkd.entity.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.rkd.entity.config.TestConfig
import com.rkd.entity.model.EntityModel
import com.rkd.entity.repository.EntityRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig::class)
class EntityControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var entityRepository: EntityRepository

    @AfterEach
    fun tearDown() {
        entityRepository.deleteAll()
    }

    @Test
    fun `deve criar uma entidade`() {

        val json = objectMapper.createObjectNode()
            .put("cars", "[{\"brand\":\"Ferrari\", \"model\":\"F8 Tributo\"}]")

        val request = objectMapper.writeValueAsString(mapOf("name" to "Car Collection", "structure" to json))

        mockMvc.perform(
            post("/entities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isCreated)
    }

    @Test
    fun `nao deve criar uma entidade que ja possua o name cadastrado`() {

        val json: ObjectNode = objectMapper.createObjectNode()
            .put("example", "test_data")

        val entity = EntityModel().apply {
            name = "TEST_ENTITY"
            structure = json
        }

        entityRepository.save(entity)

        val request = objectMapper.writeValueAsString(mapOf("name" to "TEST_ENTITY", "structure" to json))

        mockMvc.perform(
            post("/entities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `deve obter todas as entidades`() {

        val json: ObjectNode = objectMapper.createObjectNode()
            .put("example", "test_data")

        val entity = EntityModel().apply {
            name = "TEST_ENTITY"
            structure = json
        }

        entityRepository.save(entity)

        mockMvc.perform(get("/entities"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].name").value(entity.name))
    }

    @Test
    fun `deve obter uma entidade por nome`() {

        val json: ObjectNode = objectMapper.createObjectNode()
            .put("example", "test_data")

        val entity = EntityModel().apply {
            name = "TEST_ENTITY"
            structure = json
        }

        entityRepository.save(entity)

        mockMvc.perform(get("/entities/${entity.name}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value(entity.name))
    }

    @Test
    fun `deve retornar 404 ao buscar entidade inexistente`() {
        mockMvc.perform(get("/entities/NonExistentEntity"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `deve atualizar uma entidade`() {

        val json: ObjectNode = objectMapper.createObjectNode()
            .put("example", "test_data")

        val entity = EntityModel().apply {
            name = "TEST_ENTITY"
            structure = json
        }

        entityRepository.save(entity)

        val request = objectMapper.writeValueAsString(json)

        mockMvc.perform(
            put("/entities/${entity.name}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isNoContent)
    }

    @Test
    fun `deve retornar 404 ao tentar atualizar entidade inexistente`() {

        val json: ObjectNode = objectMapper.createObjectNode()
            .put("example", "test_data")

        val request = objectMapper.writeValueAsString(json)

        mockMvc.perform(
            put("/entities/NonExistentEntity")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun `deve deletar uma entidade`() {

        val json: ObjectNode = objectMapper.createObjectNode()
            .put("example", "test_data")

        val entity = EntityModel().apply {
            name = "TEST_ENTITY"
            structure = json
        }

        entityRepository.save(entity)

        mockMvc.perform(delete("/entities/${entity.name}"))
            .andExpect(status().isNoContent)
    }

    @Test
    fun `deve retornar 404 ao tentar deletar entidade inexistente`() {
        mockMvc.perform(delete("/entities/NonExistentEntity"))
            .andExpect(status().isNotFound)
    }
}
*/
