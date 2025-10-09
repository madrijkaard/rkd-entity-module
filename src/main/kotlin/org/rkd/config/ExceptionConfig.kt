package org.rkd.config

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.rkd.definition.InvalidPasswordException
import org.rkd.definition.MessageDefinition
import org.rkd.definition.UnauthorizedActionException
import org.rkd.definition.UserAlreadyRegisteredException
import org.rkd.definition.UserNotFoundException

data class ErrorResponse(
    val code: Int,
    val message: String
)

@Provider
class ExceptionConfig : ExceptionMapper<Throwable> {

    override fun toResponse(exception: Throwable): Response {

        val (status, message) = when (exception) {
            is UserNotFoundException -> Response.Status.NOT_FOUND to exception.message
            is UserAlreadyRegisteredException -> Response.Status.BAD_REQUEST to exception.message
            is InvalidPasswordException -> Response.Status.UNAUTHORIZED to exception.message
            is UnauthorizedActionException -> Response.Status.FORBIDDEN to exception.message
            is IllegalArgumentException -> Response.Status.BAD_REQUEST to exception.message
            else -> Response.Status.INTERNAL_SERVER_ERROR to MessageDefinition.Common.INTERNAL_SERVER_ERROR
        }

        val error = ErrorResponse(status.statusCode, message ?: status.reasonPhrase)

        return Response.status(status).entity(error).build()
    }
}
