package com.kepos.backend.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class UsuarioDTO(
    val id: Long? = null,

    @field:NotEmpty(message = "O nome não pode estar vazio.")
    val nome: String? = null,

    @field:NotEmpty(message = "O email não pode estar vazio.")
    @field:Email(message = "O email deve ser válido.")
    val email: String? = null,

    @field:NotEmpty(message = "A senha não pode estar vazia.")
    val senha: String? = null
)