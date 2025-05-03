package com.rkd.entity.definition

object MessageDefinition {

    object Audit {
        const val NAME_CANNOT_BLANK = "The 'name' field cannot be blank"
    }

    object SpringFramework {
        const val STRUCTURE_CANNOT_BLANK = "The 'structure' field cannot be blank"
    }

    object Dependency {
        const val STRUCTURE_CANNOT_BLANK = "The 'structure' field cannot be blank"
        const val PROJECT_ID_CANNOT_BLANK = "The 'project_id' field cannot be blank"
    }

    object Resource {
        const val STRUCTURE_CANNOT_BLANK = "The 'structure' field cannot be blank"
        const val PROJECT_ID_CANNOT_BLANK = "The 'project_id' field cannot be blank"
    }

    object UseCase {
        const val DESCRIPTION_CANNOT_BLANK = "The 'description' field cannot be blank"
        const val STRUCTURE_CANNOT_BLANK = "The 'structure' field cannot be blank"
        const val PROJECT_ID_CANNOT_BLANK = "The 'project_id' field cannot be blank"
    }

    object Project {
        const val SPRING_FRAMEWORK_ID_CANNOT_BLANK = "The 'spring_framework_id' field cannot be blank"
    }
}