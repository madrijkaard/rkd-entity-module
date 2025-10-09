package org.rkd.domain.usecase.hash

import com.password4j.Argon2Function
import com.password4j.Password
import com.password4j.types.Argon2
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class CreateHashUseCase {

    private val argon2 = Argon2Function.getInstance(
        65536,
        3,
        1,
        32,
        Argon2.ID,
        Argon2Function.ARGON2_VERSION_13
    )

    data class HashResult(val hash: String, val salt: String)

    fun hash(password: String): HashResult {
        val result = Password.hash(password)
            .addRandomSalt(16)
            .with(argon2)

        return HashResult(
            hash = result.result,
            salt = result.salt
        )
    }

    fun verify(rawPassword: String, hashedPassword: String): Boolean {
        return Password.check(rawPassword, hashedPassword).with(argon2)
    }
}