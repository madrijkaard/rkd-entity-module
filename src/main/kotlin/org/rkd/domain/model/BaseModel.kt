package org.rkd.domain.model

import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.persistence.*

@MappedSuperclass
abstract class BaseModel : PanacheEntityBase() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "name", nullable = false, unique = true)
    var name: String? = null

    @Column(name = "active", nullable = false)
    var active: Boolean? = null
}