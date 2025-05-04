package com.rkd.entity.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.rkd.entity.config.TestConfig
import com.rkd.entity.definition.MessageDefinition.Project
import com.rkd.entity.definition.UriBaseDefinition.PROJECTS
import com.rkd.entity.definition.UriBaseDefinition.SPRING_FRAMEWORKS
import com.rkd.entity.repository.ProjectRepository
import com.rkd.entity.repository.SpringFrameworkRepository
import com.rkd.entity.type.ExceptionType.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
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
class ProjectControllerTest {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var springFrameworkRepository: SpringFrameworkRepository

    @Autowired
    private lateinit var projectRepository: ProjectRepository

    @AfterEach
    fun tearDown() {
        springFrameworkRepository.deleteAll()
        projectRepository.deleteAll()
    }

    private fun createSpringFramework(name: String = "spring-boot"): Long {

        val structure = objectMapper.createObjectNode()
            .put("groupId", "org.springframework.boot")
            .put("artifactId", "spring-boot-starter")
            .put("version", "3.4.3")

        val request = objectMapper.writeValueAsString(mapOf("name" to name, "structure" to structure))

        mockMvc.perform(
            post(SPRING_FRAMEWORKS)
                .contentType(APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isCreated)

        return springFrameworkRepository.findByName(name)!!.id!!
    }

    @Test
    fun `should fail to create project with null structure`() {

        val springId = createSpringFramework()

        val request = objectMapper.writeValueAsString(
            mapOf(
                "name" to "proj-invalid",
                "structure" to null,
                "springFrameworkModel" to mapOf("id" to springId)
            )
        )

        mockMvc.perform(
            post(PROJECTS)
                .contentType(APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isBadRequest)
            .andExpect {
                val json = objectMapper.readTree(it.response.contentAsString)
                assertEquals(INVALID_INPUT_FIELD_CUSTOM.code, json.get("code").asText())
                assertEquals("Invalid structure field", json.get("message").asText())
            }
    }

    @Test
    fun `should fail to create project with blank structure`() {

        val springId = createSpringFramework()

        val request = objectMapper.writeValueAsString(
            mapOf(
                "name" to "proj-invalid",
                "structure" to "",
                "springFrameworkModel" to mapOf("id" to springId)
            )
        )

        mockMvc.perform(
            post(PROJECTS)
                .contentType(APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isBadRequest)
            .andExpect {
                val json = objectMapper.readTree(it.response.contentAsString)
                assertEquals(INVALID_INPUT_FIELD_CUSTOM.code, json.get("code").asText())
                assertEquals("Invalid structure field", json.get("message").asText())
            }
    }

    @Test
    fun `should return all project models`() {

        val springId = createSpringFramework()

        val structure = objectMapper.createObjectNode().put("name", "project")

        val project = { name: String ->
            objectMapper.writeValueAsString(
                mapOf("name" to name, "structure" to structure, "springFrameworkModel" to mapOf("id" to springId))
            )
        }

        mockMvc.perform(post(PROJECTS).contentType(APPLICATION_JSON).content(project("proj-1")))
            .andExpect(status().isCreated)

        mockMvc.perform(post(PROJECTS).contentType(APPLICATION_JSON).content(project("proj-2")))
            .andExpect(status().isCreated)

        mockMvc.perform(get(PROJECTS))
            .andExpect(status().isOk)
            .andExpect {
                val json = objectMapper.readTree(it.response.contentAsString)
                assertEquals(2, json.size())
                val names = json.map { n -> n.get("name").asText() }
                assertTrue(names.contains("proj-1"))
                assertTrue(names.contains("proj-2"))
            }
    }

    @Test
    fun `should create a project successfully`() {

        val springId = createSpringFramework()

        val structure = objectMapper.createObjectNode().put("name", "project")

        val request = objectMapper.writeValueAsString(
            mapOf("name" to "proj", "structure" to structure, "springFrameworkModel" to mapOf("id" to springId))
        )

        mockMvc.perform(
            post(PROJECTS)
                .contentType(APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isCreated)

        assertNotNull(projectRepository.findByName("proj"))
    }

    @Test
    fun `should fail to create project with null springFrameworkModel`() {

        val structure = objectMapper.createObjectNode().put("name", "project")

        val request = objectMapper.writeValueAsString(
            mapOf("name" to "proj", "structure" to structure, "springFrameworkModel" to null)
        )

        mockMvc.perform(
            post(PROJECTS)
                .contentType(APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isBadRequest)
            .andExpect {
                val json = objectMapper.readTree(it.response.contentAsString)
                assertEquals(INVALID_INPUT_FIELD.code, json.get("code").asText())
                assertEquals(Project.SPRING_FRAMEWORK_ID_CANNOT_BLANK, json.get("message").asText())
            }
    }

    @Test
    fun `should fail to create project with duplicate name`() {

        val springId = createSpringFramework()

        val structure = objectMapper.createObjectNode().put("name", "project")

        val request = objectMapper.writeValueAsString(
            mapOf("name" to "proj", "structure" to structure, "springFrameworkModel" to mapOf("id" to springId))
        )

        mockMvc.perform(post(PROJECTS).contentType(APPLICATION_JSON).content(request))
            .andExpect(status().isCreated)

        mockMvc.perform(post(PROJECTS).contentType(APPLICATION_JSON).content(request))
            .andExpect(status().isBadRequest)
            .andExpect {
                val json = objectMapper.readTree(it.response.contentAsString)
                assertEquals(IDENTIFIER_ALREADY_EXISTS.code, json.get("code").asText())
                assertEquals(IDENTIFIER_ALREADY_EXISTS.message, json.get("message").asText())
            }
    }

    @Test
    fun `should update a project successfully`() {

        val springId = createSpringFramework()

        val structure = objectMapper.createObjectNode().put("name", "project")

        val createRequest = objectMapper.writeValueAsString(
            mapOf("name" to "proj", "structure" to structure, "springFrameworkModel" to mapOf("id" to springId))
        )

        mockMvc.perform(post(PROJECTS).contentType(APPLICATION_JSON).content(createRequest))
            .andExpect(status().isCreated)

        val updatedStructure = objectMapper.createObjectNode().put("description", "updated project")

        val updateRequest = objectMapper.writeValueAsString(
            mapOf("name" to "proj", "structure" to updatedStructure, "springFrameworkModel" to mapOf("id" to springId))
        )

        mockMvc.perform(put("$PROJECTS/proj").contentType(APPLICATION_JSON).content(updateRequest))
            .andExpect(status().isNoContent)

        val updated = projectRepository.findByName("proj")

        assertEquals("updated project", updated!!.structure?.get("description")?.asText())
    }

    @Test
    fun `should fail to update non-existent project`() {

        val springId = createSpringFramework()

        val structure = objectMapper.createObjectNode().put("x", "y")

        val request = objectMapper.writeValueAsString(
            mapOf("name" to "nonexistent", "structure" to structure, "springFrameworkModel" to mapOf("id" to springId))
        )

        mockMvc.perform(put("$PROJECTS/nonexistent").contentType(APPLICATION_JSON).content(request))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should delete project successfully`() {

        val springId = createSpringFramework()

        val structure = objectMapper.createObjectNode().put("x", "y")

        val request = objectMapper.writeValueAsString(
            mapOf("name" to "todelete", "structure" to structure, "springFrameworkModel" to mapOf("id" to springId))
        )

        mockMvc.perform(post(PROJECTS).contentType(APPLICATION_JSON).content(request))
            .andExpect(status().isCreated)

        mockMvc.perform(delete("$PROJECTS/todelete")).andExpect(status().isNoContent)

        assertNull(projectRepository.findByName("todelete"))
    }

    @Test
    fun `should fail to delete non-existent project`() {
        mockMvc.perform(delete("$PROJECTS/unknown"))
            .andExpect(status().isNotFound)
    }
}
