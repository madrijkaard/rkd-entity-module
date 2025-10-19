package org.rkd.adapter.`in`.resource

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.inject.Inject
import jakarta.ws.rs.core.Response
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.rkd.config.ContainerConfig
import org.rkd.config.JWTConfig
import org.rkd.definition.MessageDefinition
import org.rkd.port.out.repository.UserRepositoryOutPort
import org.hamcrest.Matchers.equalTo

@QuarkusTest
@QuarkusTestResource(ContainerConfig::class)
class UserResourceInAdapterTest {

    @Inject
    lateinit var userRepository: UserRepositoryOutPort

    private val token = JWTConfig.generateToken()

    @Test
    fun `should successfully create a user`() {
        val name = "john_doe_${System.currentTimeMillis()}"
        val email = "john.doe${System.currentTimeMillis()}@example.com"

        val requestBody = mapOf(
            "name" to name,
            "email" to email,
            "password" to "password123",
            "structure" to """{"role": "tester"}"""
        )

        val response = given()
            .header("Authorization", "Bearer $token")
            .contentType(ContentType.JSON)
            .body(requestBody)
            .`when`()
            .post("/users")
            .then()
            .log().ifValidationFails()
            .extract()
            .response()

        assertEquals(Response.Status.CREATED.statusCode, response.statusCode)

        val user = userRepository.findByName(name)
        assertNotNull(user, "Usuário não foi encontrado no banco")
        assertEquals(email, user?.email)
        assertFalse(user?.active ?: true)
        assertNotNull(user?.hash)
        assertNotNull(user?.salt)
        assertEquals("""{"role": "tester"}""", user?.structure)
    }

    @Test
    fun `should return 400 when trying to create an already registered user`() {
        val name = "test"
        val email = "test@example.com"

        val requestBody = mapOf(
            "name" to name,
            "email" to email,
            "password" to "password123",
            "structure" to """{"role": "tester"}"""
        )

        given()
            .header("Authorization", "Bearer $token")
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post("/users")
            .then()
            .statusCode(201)

        given()
            .header("Authorization", "Bearer $token")
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post("/users")
            .then()
            .statusCode(400)
            .body("message", equalTo(MessageDefinition.User.ALREADY_EXISTS))
    }

    @Test
    fun `should successfully update a user`() {
        val name = "john_doe_${System.currentTimeMillis()}"
        val email = "john.doe${System.currentTimeMillis()}@example.com"

        val requestBody = mapOf(
            "name" to name,
            "email" to email,
            "password" to "password123",
            "structure" to """{"role": "tester"}"""
        )

        given()
            .header("Authorization", "Bearer $token")
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post("/users")
            .then()
            .statusCode(201)

        val updateRequest = mapOf(
            "name" to name,
            "email" to email,
            "structure" to """{"role": "developer"}"""
        )

        given()
            .header("Authorization", "Bearer $token")
            .contentType(ContentType.JSON)
            .body(updateRequest)
            .put("/users")
            .then()
            .statusCode(204)

        val user = userRepository.findByName(name)
        assertNotNull(user, "Usuário não foi encontrado no banco")
        assertEquals("""{"role": "developer"}""", user?.structure)
    }

    @Test
    fun `should successfully delete a user`() {
        val name = "john_doe_${System.currentTimeMillis()}"
        val email = "john.doe${System.currentTimeMillis()}@example.com"

        val requestBody = mapOf(
            "name" to name,
            "email" to email,
            "password" to "password123",
            "structure" to """{"role": "tester"}"""
        )

        given()
            .header("Authorization", "Bearer $token")
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post("/users")
            .then()
            .statusCode(201)

        val deleteRequest = mapOf("name" to name)

        given()
            .header("Authorization", "Bearer $token")
            .contentType(ContentType.JSON)
            .body(deleteRequest)
            .delete("/users")
            .then()
            .statusCode(200)

        val user = userRepository.findByName(name)
        assertNull(user, "Usuário foi encontrado no banco")
    }
}
