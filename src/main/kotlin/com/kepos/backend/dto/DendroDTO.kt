package com.kepos.backend.dto

data class DendroDTO(
    val id: String? = null,
    val name: String? = null,
    val temperature: Double? = null,
    val humidity: Double? = null,
    val luminosity: Int? = null,
    val userId: Long? = null
)