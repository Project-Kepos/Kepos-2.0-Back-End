package com.kepos.backend.controller


import com.kepos.backend.config.security.DadosTokenJWT
import com.kepos.backend.dto.LoginDTO
import com.kepos.backend.dto.UsuarioDTO
import com.kepos.backend.service.IUsuarioService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/api/v1/usuario")
@Tag(name = "Usuario", description = "Endpoints para gerenciamento de Usuários")
class APIUsuarioController(
    private val usuarioService: IUsuarioService
) {

    @GetMapping
    @Operation(summary = "Consulta Usuário",
        description = "Retorna os detalhes do usuário autenticado.")
    fun listaUsuarioLogado(request: HttpServletRequest): UsuarioDTO {
        val idUsuario = request.getAttribute("userId") as Long
        val usuario = usuarioService.listaUsuarioLogado(idUsuario)
        return UsuarioDTO(usuario.id)
    }

    // TODO: Verificar necessidade deste método
    @GetMapping("/todos")
    @Operation(summary = "Consulta todos os Usuários",
        description = "Retorna uma lista de todos os usuários cadastrados no sistema.")
    fun listaTodosUsuarios(): List<UsuarioDTO> {
        val usuarios = usuarioService.listaTodosUsuarios()
        return usuarios.map { UsuarioDTO(it.id) }
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastra um novo Usuário",
        description = "Cadastra um novo usuário no sistema.")
    fun cadastrarUsuario(
        @RequestBody @Valid json: UsuarioDTO,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<UsuarioDTO> {
        val usuario = usuarioService.cadastrarUsuario(json)
        val uri = uriBuilder.path("/api/v1/usuario/{id}").buildAndExpand(usuario.id).toUri()
        return ResponseEntity.created(uri).body(UsuarioDTO(usuario.id))
    }

    @PostMapping("/login")
    @Transactional
    @Operation(summary = "Realiza o login do Usuário",
        description = "Realiza o login do usuário e retorna um token JWT.")
    fun efetuarLogin(@RequestBody @Valid json: LoginDTO): DadosTokenJWT {
        val token = usuarioService.realizarLogin(json)
        return DadosTokenJWT(token)
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Atualiza os dados do Usuário",
        description = "Atualiza os dados do usuário autenticado.")
    fun atualizarUsuario(
        request: HttpServletRequest,
        @RequestBody @Valid json: UsuarioDTO
    ): DadosTokenJWT {
        val idUsuario = request.getAttribute("userId") as Long
        val token = usuarioService.atualizarUsuario(json, idUsuario)
        request.setAttribute("Authorization", "Bearer $token")
        return DadosTokenJWT(token)
    }

    @DeleteMapping
    @Transactional
    @Operation(summary = "Deleta o Usuário",
        description = "Remove o usuário autenticado do sistema.")
    fun deletarUsuario(request: HttpServletRequest) {
        val idUsuario = request.getAttribute("userId") as Long
        usuarioService.deletarUsuario(idUsuario)
    }
}