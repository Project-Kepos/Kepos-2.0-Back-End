package com.kepos.backend.dto

data class ModuloDTO(
    val id: Long? = null,
    val name: String? = null,
    val desc: String? = null,
    val humidity: Int? = null,
    val humidityLevel: Int? = null,
    val dendroId: String? = null
)