package com.kepos.backend.repository

import com.kepos.backend.model.Modulo
import org.springframework.data.jpa.repository.JpaRepository

interface IModuloRepository : JpaRepository<Modulo, Long> {

    fun findAllByDendro_Id(id: String): List<Modulo>
}