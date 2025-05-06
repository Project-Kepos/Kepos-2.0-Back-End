package com.kepos.backend.controller


import com.kepos.backend.dto.DendroDTO
import com.kepos.backend.model.Resposta
import com.kepos.backend.service.IDendroService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/api/v1/dendro")
@Tag(name = "Dendro", description = "Endpoints para gerenciamento de Dendros")
class APIDendroController(
    private val dendroService: IDendroService
) {

    // TODO: Revisar necessidade deste método
    @GetMapping
    @Operation(summary = "Consulta todos os Dendros",
        description = "Retorna uma lista de todos os Dendros cadastrados no sistema.")
    fun consultaTodasDendros(): List<DendroDTO> {
        val dendros = dendroService.consultaTodasDendros()
        return dendros.map { DendroDTO(it.id) }
    }

    // TODO: Revisar necessidade deste método
    @GetMapping(params = ["nome"])
    @Operation(summary = "Consulta Dendros por nome",
        description = "Retorna uma lista de Dendros filtrados pelo nome.")
    fun consultaPorNome(@RequestParam nome: String): List<DendroDTO> {
        val dendros = dendroService.consultaDendrosPorNome(nome)
        return dendros.map { DendroDTO(it.toString()) }
    }

    @GetMapping("/usuario")
    @Operation(summary = "Consulta Dendros por usuário",
        description = "Retorna uma lista de Dendros associadas ao usuário autenticado.")
    fun consultaTodasDendrosPorUsuario(request: HttpServletRequest): List<DendroDTO> {
        val userId = request.getAttribute("userId") as Long
        val dendros = dendroService.consultaDendrosPorUsuario(userId)
        return dendros.map { DendroDTO(it.id) }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta Dendro por ID",
        description = "Retorna os detalhes de um Dendro específico.")
    fun consultaPorId(@PathVariable id: String): DendroDTO {
        val dendro = dendroService.consultaDendroPorId(id)
        return DendroDTO(dendro.id)
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastra um novo Dendro",
        description = "Cria um novo Dendro no sistema.")
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
    @Operation(summary = "Associa um usuário a um Dendro",
        description = "Associa o usuário autenticado a um Dendro.")
    fun adicionarUsuario(@RequestBody json: DendroDTO, request: HttpServletRequest): DendroDTO {
        val userId = request.getAttribute("userId") as Long
        val dendro = dendroService.adicionarUsuario(userId, json)
        return DendroDTO(dendro.id)
    }

    @PatchMapping("/usuario/desassociar")
    @Transactional
    @Operation(summary = "Desassocia um usuário de um Dendro",
        description = "Remove a associação do usuário autenticado com o Dendro.")
    fun desassociarUsuario(@RequestBody json: DendroDTO): DendroDTO {
        val dendro = dendroService.removerUsuarioDendro(json)
        return DendroDTO(dendro.id)
    }

    @PatchMapping("/{id}")
    @Transactional
    @Operation(summary = "Atualiza um Dendro",
        description = "Atualiza os detalhes de um Dendro existente.")
    fun atualizarDendro(@PathVariable id: String, @RequestBody json: DendroDTO): DendroDTO {
        val dendro = dendroService.atualizarDendro(id, json)
        return DendroDTO(dendro.id)
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Deleta um Dendro",
        description = "Remove um Dendro do sistema.")
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