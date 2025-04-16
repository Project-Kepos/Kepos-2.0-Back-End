package com.kepos.backend.service

import com.kepos.backend.dto.ModuloDTO
import com.kepos.backend.model.Modulo


interface IModuloService {

    fun consultaTodosModulos(): List<Modulo>

    fun consultaPorId(id: Long): Modulo

    fun consultaPorDendro(id: String): List<Modulo>

    fun cadastrarModulo(json: ModuloDTO): Modulo

    fun atualizarModulo(id: Long, json: ModuloDTO): Modulo

    fun deletarModulo(id: Long)
}