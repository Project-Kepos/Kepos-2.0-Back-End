package com.kepos.backend.controller

import com.kepos.backend.dto.ModuloDTO
import com.kepos.backend.model.Resposta
import com.kepos.backend.service.IModuloService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/api/v1/modulo")
@Tag(name = "Modulo", description = "Endpoints para gerenciamento de Modulos")
class APIModuloController(
    private val moduloService: IModuloService
) {

    // TODO: Revisar necessidade deste método
    @GetMapping
    @Operation(summary = "Consulta todos os Modulos",
        description = "Retorna uma lista de todos os Modulos cadastrados no sistema.")
    fun consultaTodosModulos(): List<ModuloDTO> {
        val modulos = moduloService.consultaTodosModulos()
        return modulos.map { ModuloDTO(it.id) }
    }

    @GetMapping(params = ["dendro_id"])
    @Operation(summary = "Consulta Modulos por Dendro",
        description = "Retorna uma lista de Modulos filtrados pelo ID do Dendro.")
    fun consultaPorDendro(@RequestParam dendro_id: String): List<ModuloDTO> {
        val modulos = moduloService.consultaPorDendro(dendro_id)
        return modulos.map { ModuloDTO(it.id) }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta Modulo por ID",
        description = "Retorna os detalhes de um Modulo específico.")
    fun consultaPorId(@PathVariable id: Long): ModuloDTO {
        val modulo = moduloService.consultaPorId(id)
        return ModuloDTO(modulo.id)
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastra um novo Modulo",
        description = "Cadastra um novo Modulo no sistema.")
    fun cadastrarModulo(
        @RequestBody @Valid json: ModuloDTO,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<ModuloDTO> {
        val modulo = moduloService.cadastrarModulo(json)
        val uri = uriBuilder.path("/api/v1/modulo/{id}").buildAndExpand(modulo.id).toUri()
        return ResponseEntity.created(uri).body(ModuloDTO(modulo.id))
    }

    @PatchMapping("/{id}")
    @Transactional
    @Operation(summary = "Atualiza um Modulo",
        description = "Atualiza os dados de um Modulo existente.")
    fun atualizarModulo(@PathVariable id: Long, @RequestBody @Valid json: ModuloDTO): ModuloDTO {
        val modulo = moduloService.atualizarModulo(id, json)
        return ModuloDTO(modulo.id)
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Deleta um Modulo",
        description = "Remove um Modulo do sistema.")
    fun deletarModulo(@PathVariable id: Long, request: HttpServletRequest): ResponseEntity<Any> {
        moduloService.deletarModulo(id)

        val resposta = Resposta(
            mensagem = "Modulo deletado com sucesso",
            status = HttpStatus.OK,
            caminho = request.requestURI.toString(),
            metodo = request.method
        )

        return ResponseEntity.status(HttpStatus.OK).body(resposta)
    }
}