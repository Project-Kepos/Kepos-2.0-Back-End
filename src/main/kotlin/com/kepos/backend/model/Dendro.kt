package com.kepos.backend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.kepos.backend.dto.DendroDTO
import com.raposo.experiment.model.Modulo
import com.raposo.experiment.model.Usuario
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
data class Dendro(
    @Id
    var id: String? = null,
    var name: String? = null,
    var temperature: Double? = null,
    var humidity: Double? = null,
    var luminosity: Int? = null,

    @JsonIgnore
    @OneToMany(mappedBy = "dendro")
    var modules: List<Modulo> = mutableListOf(),

    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    var user: Usuario? = null
) {
    fun atualizarDendro(json: DendroDTO) {
        json.name?.let { name = it }
        json.temperature?.let { temperature = it }
        json.luminosity?.let { luminosity = it }
        json.humidity?.let { humidity = it }
    }
}