package org.rkd.domain.model

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.envers.Audited
import org.hibernate.envers.NotAudited
import org.hibernate.type.SqlTypes

@Entity
@Audited
@Table(name = "project")
class ProjectModel : BaseModel() {

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    var description: String? = null

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    var structure: String? = null

    @NotAudited
    @OneToMany(mappedBy = "project", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var userProjects: MutableList<UserProjectModel> = mutableListOf()
}