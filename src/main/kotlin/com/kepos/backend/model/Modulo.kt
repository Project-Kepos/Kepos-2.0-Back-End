package com.kepos.backend.model

import jakarta.persistence.*

@Entity
data class Modulo (
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "modulo_generator")
    @TableGenerator(name = "contato-generator",
        table = "id_sequences",
        pkColumnName = "seq_id",
        valueColumnName = "seq_value")

    var id : Long?,
    val name : String,
    val desc : String,
    val humidity : Int,
    val humidityLevel: Int,

    @ManyToOne
    @JoinColumn(name = "dendro_id")
    var dendro: Dendro?
)

