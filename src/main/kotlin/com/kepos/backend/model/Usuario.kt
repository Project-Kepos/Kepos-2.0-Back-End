package com.kepos.backend.model

import jakarta.persistence.*

@Entity
data class Usuario (

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "usuario_generator")
    @TableGenerator(
        name = "usuario-generator",
        table = "id_sequences",
        pkColumnName = "seq_id",
        valueColumnName = "seq_value"
    )
    var id: Long?,
    val name: String,
    val email: String,
    val password: String,
)