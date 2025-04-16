package com.kepos.backend.service

import com.kepos.backend.dto.DendroDTO
import com.kepos.backend.model.Dendro

interface IDendroService {

    fun consultaTodasDendros(): List<Dendro>

    fun consultaDendrosPorNome(nome: String): List<Dendro>

    fun consultaDendrosPorUsuario(userId: Long): List<Dendro>

    fun consultaDendroPorId(id: String): Dendro

    fun cadastrarDendro(json: DendroDTO): Dendro

    fun adicionarUsuario(userId: Long, json: DendroDTO): Dendro

    fun atualizarDendro(id: String, json: DendroDTO): Dendro

    fun deletarDendro(id: String)

    fun removerUsuarioDendro(json: DendroDTO): Dendro
}