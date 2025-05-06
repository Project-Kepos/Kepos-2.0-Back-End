package com.kepos.backend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.kepos.backend.dto.DendroDTO
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.jetbrains.annotations.NotNull
import java.sql.Time
import java.util.Date

@Entity
data class Dendro(
    @Id
    var id: String? = null,
    @NotNull
    var name: String,
    var temperature: String? = null,
    var humidity: String? = null,
    var luminosity: String? = null,

    @OneToMany(mappedBy = "dendro", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonIgnore
    var modules: MutableList<Modulo> = mutableListOf(),

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