package org.rkd.domain.model

import jakarta.persistence.*
import org.rkd.domain.listener.UserProjectListener
import java.io.Serializable
import java.time.LocalDateTime

@Embeddable
data class UserProjectKey(
    @Column(name = "user_id")
    val userId: Long?,

    @Column(name = "project_id")
    val projectId: Long?
) : Serializable

@Entity
@Table(name = "user_project")
@EntityListeners(UserProjectListener::class)
class UserProjectModel {

    @EmbeddedId
    var id: UserProjectKey? = null

    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime? = null

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserModel? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    @JoinColumn(name = "project_id", nullable = false)
    var project: ProjectModel? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserProjectModel) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
