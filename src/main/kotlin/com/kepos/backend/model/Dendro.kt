package com.kepos.backend.model

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
data class Dendro (
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "dendro_generator")
    @TableGenerator(
        name = "dendro-generator",
        table = "id_sequences",
        pkColumnName = "seq_id",
        valueColumnName = "seq_value"
    )

    var id: Long?,
    val name: String,
    val temperature: Double,
    val humidity: Double,
    val luminosity: Int,

    @OneToMany(mappedBy = "dendro")
    var modulo : MutableList<Modulo>,

    @ManyToOne(cascade = [CascadeType.ALL])
    @OnDelete(action = OnDeleteAction.CASCADE)
    var usuario: Usuario
)