package com.kepos.backend.service

import com.kepos.backend.dto.LoginDTO
import com.kepos.backend.dto.UsuarioDTO
import com.kepos.backend.model.Usuario


interface IUsuarioService {

    fun listaUsuarioLogado(id: Long): Usuario

    fun listaTodosUsuarios(): List<Usuario>

    fun cadastrarUsuario(json: UsuarioDTO): Usuario

    fun realizarLogin(json: LoginDTO): String

    fun atualizarUsuario(json: UsuarioDTO, id: Long): String

    fun deletarUsuario(id: Long)
}