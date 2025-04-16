package com.kepos.backend.service

import com.kepos.backend.config.error.ErroCustomizado
import com.kepos.backend.config.security.TokenService
import com.kepos.backend.dto.LoginDTO
import com.kepos.backend.dto.UsuarioDTO
import com.kepos.backend.model.Usuario
import com.kepos.backend.repository.IUsuarioRepository
import jakarta.persistence.EntityNotFoundException
import org.apache.logging.log4j.LogManager
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private val usuarioRepository: IUsuarioRepository,
    private val passwordEncoder: PasswordEncoder,
    private val manager: AuthenticationManager,
    private val tokenService: TokenService
) : IUsuarioService {

    private val logger = LogManager.getLogger(javaClass)

    override fun listaUsuarioLogado(id: Long): Usuario {
        logger.info("Listando usuario pelo token")
        return usuarioRepository.findById(id).orElseThrow {
            EntityNotFoundException("Usuário não encontrado com o id fornecido.")
        }
    }

    // TODO: Verificar necessidade deste método
    override fun listaTodosUsuarios(): List<Usuario> {
        logger.info("Listando usuarios no sistema")
        return usuarioRepository.findAll()
    }

    override fun cadastrarUsuario(json: UsuarioDTO): Usuario {
        logger.info("Cadastrando usuario")

        if (usuarioRepository.existsByEmail(json.email ?: throw ErroCustomizado("E-mail é obrigatório."))) {
            throw ErroCustomizado("E-mail já cadastrado no sistema.")
        }

        val novoUsuario = Usuario(
            nome = json.nome,
            email = json.email,
            senha = passwordEncoder.encode(json.senha)
        )
        return usuarioRepository.save(novoUsuario)
    }

    override fun atualizarUsuario(json: UsuarioDTO, id: Long): String {
        val usuario = usuarioRepository.findById(id).orElseThrow {
            EntityNotFoundException("Usuário não encontrado com o id fornecido.")
        }

        usuario.atualizarUsuario(json)

        json.senha?.let {
            usuario.senha = passwordEncoder.encode(it)
        }

        return tokenService.gerarToken(usuario)
    }

    override fun deletarUsuario(id: Long) {
        if (!usuarioRepository.existsById(id)) {
            throw EntityNotFoundException("Usuário não encontrado com o id fornecido.")
        }
        usuarioRepository.deleteById(id)
    }

    override fun realizarLogin(json: LoginDTO): String {
        return try {
            val token = UsernamePasswordAuthenticationToken(json.email, json.senha)
            val authentication = manager.authenticate(token)
            tokenService.gerarToken(authentication.principal as Usuario)
        } catch (e: AuthenticationException) {
            throw ErroCustomizado("E-mail ou senha inválidos.")
        }
    }
}