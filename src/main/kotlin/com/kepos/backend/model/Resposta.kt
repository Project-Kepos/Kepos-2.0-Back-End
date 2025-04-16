package com.kepos.backend.model

import org.springframework.http.HttpStatus

data class Resposta(
    var erro: String? = null,
    var mensagem: String? = null,
    var status: HttpStatus? = null,
    var caminho: String? = null,
    var metodo: String? = null
)