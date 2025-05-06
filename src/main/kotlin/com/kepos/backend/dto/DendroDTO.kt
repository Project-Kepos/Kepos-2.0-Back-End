package com.kepos.backend.dto

data class DendroDTO(
    val id: String? = null,
    val name: String? = null,
    val temperature: String? = null,
    val humidity: String? = null,
    val luminosity: String? = null,
    val userId: Long? = null
)