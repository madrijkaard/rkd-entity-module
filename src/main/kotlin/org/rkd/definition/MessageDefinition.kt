package org.rkd.definition

object MessageDefinition {

    private object Template {
        const val NOT_FOUND = "%s não encontrado."
        const val ALREADY_EXISTS = "%s is already registered"
    }

    private fun entityMessages(entityName: String) = EntityMessages(entityName)

    open class EntityMessages(private val name: String) {
        val NOT_FOUND get() = Template.NOT_FOUND.format(name)
        val ALREADY_EXISTS get() = Template.ALREADY_EXISTS.format(name)
    }

    object User : EntityMessages("User") {
        const val INVALID_CREDENTIALS = "Credenciais inválidas."
        const val UNAUTHORIZED = "Acesso não autorizado."
    }

    object Project : EntityMessages("Project") {
        const val INVALID_OWNER = "Usuário não autorizado para este projeto."
    }

    object Login : EntityMessages("Login") {
        const val INVALID_PASSWORD = "Invalid password"
    }

    object Common {
        const val INTERNAL_SERVER_ERROR = "Internal server error"
    }
}
