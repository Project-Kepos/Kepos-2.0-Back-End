package com.kepos.backend.controller


import com.kepos.backend.dto.DendroDTO
import com.kepos.backend.model.Resposta
import com.kepos.backend.service.IDendroService
import jakarta.servlet.http.HttpServletRequest
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/api/v1/dendro")
class APIDendroController(
    private val dendroService: IDendroService
) {

    // TODO: Revisar necessidade deste método
    @GetMapping
    fun consultaTodasDendros(): List<DendroDTO> {
        val dendros = dendroService.consultaTodasDendros()
        return dendros.map { DendroDTO(it.id) }
    }

    // TODO: Revisar necessidade deste método
    @GetMapping(params = ["nome"])
    fun consultaPorNome(@RequestParam nome: String): List<DendroDTO> {
        val dendros = dendroService.consultaDendrosPorNome(nome)
        return dendros.map { DendroDTO(it.toString()) }
    }

    @GetMapping("/usuario")
    fun consultaTodasDendrosPorUsuario(request: HttpServletRequest): List<DendroDTO> {
        val userId = request.getAttribute("userId") as Long
        val dendros = dendroService.consultaDendrosPorUsuario(userId)
        return dendros.map { DendroDTO(it.id) }
    }

    @GetMapping("/{id}")
    fun consultaPorId(@PathVariable id: String): DendroDTO {
        val dendro = dendroService.consultaDendroPorId(id)
        return DendroDTO(dendro.id)
    }

    @PostMapping
    @Transactional
    fun cadastrarDendro(
        @RequestBody json: DendroDTO,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<DendroDTO> {
        val dendro = dendroService.cadastrarDendro(json)
        val uri = uriBuilder.path("/api/v1/dendro/{id}").buildAndExpand(dendro.id).toUri()
        return ResponseEntity.created(uri).body(DendroDTO(dendro.id))
    }

    @PatchMapping("/usuario")
    @Transactional
    fun adicionarUsuario(@RequestBody json: DendroDTO, request: HttpServletRequest): DendroDTO {
        val userId = request.getAttribute("userId") as Long
        val dendro = dendroService.adicionarUsuario(userId, json)
        return DendroDTO(dendro.id)
    }

    @PatchMapping("/usuario/desassociar")
    @Transactional
    fun desassociarUsuario(@RequestBody json: DendroDTO): DendroDTO {
        val dendro = dendroService.removerUsuarioDendro(json)
        return DendroDTO(dendro.id)
    }

    @PatchMapping("/{id}")
    @Transactional
    fun atualizarDendro(@PathVariable id: String, @RequestBody json: DendroDTO): DendroDTO {
        val dendro = dendroService.atualizarDendro(id, json)
        return DendroDTO(dendro.id)
    }

    @DeleteMapping("/{id}")
    @Transactional
    fun deletarDendro(@PathVariable id: String, request: HttpServletRequest): ResponseEntity<Any> {
        dendroService.deletarDendro(id)

        val resposta = Resposta(
            mensagem = "Dendro deletado com sucesso",
            status = HttpStatus.OK,
            caminho = request.requestURI.toString(),
            metodo = request.method
        )

        return ResponseEntity.status(HttpStatus.OK).body(resposta)
    }
}