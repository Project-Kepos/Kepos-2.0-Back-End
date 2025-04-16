package com.kepos.backend.controller

import com.kepos.backend.dto.ModuloDTO
import com.kepos.backend.model.Resposta
import com.kepos.backend.service.IModuloService
import jakarta.servlet.http.HttpServletRequest
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/api/v1/modulo")
class APIModuloController(
    private val moduloService: IModuloService
) {

    // TODO: Revisar necessidade deste método
    @GetMapping
    fun consultaTodosModulos(): List<ModuloDTO> {
        val modulos = moduloService.consultaTodosModulos()
        return modulos.map { ModuloDTO(it.id) }
    }

    @GetMapping(params = ["dendro_id"])
    fun consultaPorDendro(@RequestParam dendro_id: String): List<ModuloDTO> {
        val modulos = moduloService.consultaPorDendro(dendro_id)
        return modulos.map { ModuloDTO(it.id) }
    }

    @GetMapping("/{id}")
    fun consultaPorId(@PathVariable id: Long): ModuloDTO {
        val modulo = moduloService.consultaPorId(id)
        return ModuloDTO(modulo.id)
    }

    @PostMapping
    @Transactional
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
    fun atualizarModulo(@PathVariable id: Long, @RequestBody @Valid json: ModuloDTO): ModuloDTO {
        val modulo = moduloService.atualizarModulo(id, json)
        return ModuloDTO(modulo.id)
    }

    @DeleteMapping("/{id}")
    @Transactional
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