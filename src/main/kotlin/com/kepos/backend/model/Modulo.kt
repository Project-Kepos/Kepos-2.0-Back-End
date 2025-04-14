package com.raposo.experiment.model

import com.kepos.backend.dto.ModuloDTO
import com.kepos.backend.model.Dendro
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
data class Modulo(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var name: String? = null,
    var desc: String? = null,
    var humidity: Int? = null,
    var humidityLevel: Int? = null,

    @ManyToOne
    @JoinColumn(name = "dendro_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    var dendro: Dendro? = null
) {
    fun atualizarModulo(json: ModuloDTO) {
        json.name?.let { name = it }
        json.desc?.let { desc = it }
        json.humidity?.let { humidity = it }
        json.humidityLevel?.let { humidityLevel = it }
    }
}