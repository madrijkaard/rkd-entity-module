package com.rkd.entity.component

interface ValidationComponent<MODEL> {
    fun validate(model: MODEL)
}