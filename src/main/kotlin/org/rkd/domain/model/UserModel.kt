package org.rkd.domain.model

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.envers.Audited
import org.hibernate.envers.NotAudited
import org.hibernate.type.SqlTypes

@Entity
@Audited
@Table(name = "\"user\"")
class UserModel : BaseModel() {

    @Column(name = "email", nullable = false, unique = true)
    var email: String? = null

    @Column(name = "hash", nullable = false)
    var hash: String? = null

    @Column(name = "salt", nullable = false)
    var salt: String? = null

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    var structure: String? = null

    @NotAudited
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var userProjects: MutableList<UserProjectModel> = mutableListOf()
}
