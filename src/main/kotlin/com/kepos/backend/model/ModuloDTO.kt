package com.kepos.backend.model

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Past

data class ModuloDTO (
    var id : Long,

    @field: NotEmpty
    var name : String,
)