package org.rkd.adapter.`in`.resource

import io.quarkus.redis.datasource.RedisDataSource
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.inject.Inject
import jakarta.ws.rs.core.Response
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.rkd.config.ContainerConfig
import org.rkd.port.out.repository.UserRepositoryOutPort

@QuarkusTest
@QuarkusTestResource(ContainerConfig::class)
class LoginResourceInAdapterTest {

    @Inject
    lateinit var userRepository: UserRepositoryOutPort

    @Inject
    lateinit var redisDataSource: RedisDataSource

    private val redis by lazy { redisDataSource.value(String::class.java) }

    @Test
    fun `should successfully login an existing user`() {

        val name = "john_login_${System.currentTimeMillis()}"
        val email = "john.login${System.currentTimeMillis()}@example.com"
        val password = "mypassword123"

        val createUserBody = mapOf(
            "name" to name,
            "email" to email,
            "password" to password,
            "structure" to """{"role": "tester"}"""
        )

        val createResponse =
            given()
                .contentType(ContentType.JSON)
                .body(createUserBody)
                .`when`()
                .post("/users")
                .then()
                .extract()
                .response()

        assertEquals(Response.Status.CREATED.statusCode, createResponse.statusCode, "User creation failed")

        val loginBody = mapOf(
            "name" to name,
            "password" to password
        )

        val loginResponse =
            given()
                .contentType(ContentType.JSON)
                .body(loginBody)
                .`when`()
                .post("/auth/login")
                .then()
                .extract()
                .response()

        assertEquals(Response.Status.OK.statusCode, loginResponse.statusCode, "Login failed")

        val responseJson = loginResponse.jsonPath()
        val token = responseJson.getString("token")
        val expiresAt = responseJson.getLong("expiresAt")

        val redisKey = "session:$name"
        val storedValue = redis.get(redisKey)

        assertNotNull(storedValue, "Redis must contain the user's session key")
        assertEquals(token, storedValue, "Stored token must match returned token")

        val now = System.currentTimeMillis() / 1000
        assertTrue(expiresAt > now, "Expiration timestamp must be in the future")

        val user = userRepository.findByName(name)
        assertNotNull(user)
        assertEquals(name, user?.name)
    }
}
