package com.kepos.backend.repository

import com.kepos.backend.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails

interface IUsuarioRepository : JpaRepository<Usuario, Long> {

    fun findByEmail(email: String): UserDetails

    fun existsByEmail(email: String): Boolean
}