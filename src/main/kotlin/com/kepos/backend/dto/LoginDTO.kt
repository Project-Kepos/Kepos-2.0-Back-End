package com.kepos.backend.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class LoginDTO(
    @field:NotBlank(message = "O e-mail é obrigatório.")
    @field:Email(message = "Insira um e-mail válido.")
    val email: String,

    @field:NotBlank(message = "A senha é obrigatória.")
    val senha: String
)