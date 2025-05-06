package com.kepos.backend.model

import com.kepos.backend.dto.ModuloDTO
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.jetbrains.annotations.NotNull
import java.sql.Time
import java.util.*

@Entity
data class Modulo(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @NotNull
    var name: String? = null,
    var desc: String? = null,
    var humidity: String? = null,
    var humidityLevel: String? = null,

    @ManyToOne
    @JoinColumn(name = "dendro_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    var dendro: Dendro? = null,

    @Version
    var version: Long? = null // Controle de versão
) {
    fun atualizarModulo(json: ModuloDTO) {
        json.name?.let { name = it }
        json.desc?.let { desc = it }
        json.humidity?.let { humidity = it }
        json.humidityLevel?.let { humidityLevel = it }
    }
}