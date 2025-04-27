package com.rkd.entity.component

import jakarta.transaction.Transactional

interface CrudComponent<T> {
    fun findAll(): List<T>
    fun findByName(name: String): T
    @Transactional
    fun create(model: T)
    @Transactional
    fun update(name: String, model: T): T
    @Transactional
    fun delete(name: String)
}