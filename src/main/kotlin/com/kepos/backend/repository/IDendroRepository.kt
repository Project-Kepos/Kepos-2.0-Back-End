package com.kepos.backend.repository

import com.kepos.backend.model.Dendro
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface IDendroRepository : JpaRepository<Dendro, String> {

    fun findByName(name: String): List<Dendro>

    fun findAllByUser_Id(userId: Long): List<Dendro>
}