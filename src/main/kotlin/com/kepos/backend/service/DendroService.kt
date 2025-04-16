package com.kepos.backend.service

import com.kepos.backend.dto.DendroDTO
import com.kepos.backend.model.Dendro
import com.kepos.backend.repository.IDendroRepository
import com.kepos.backend.repository.IUsuarioRepository
import com.kepos.backend.config.error.ErroCustomizado
import jakarta.persistence.EntityNotFoundException
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service

@Service
class DendroService(
    private val dendroRepository: IDendroRepository,
    private val usuarioRepository: IUsuarioRepository
) : IDendroService {

    private val logger = LogManager.getLogger(javaClass)

    override fun consultaTodasDendros(): List<Dendro> {
        logger.info("Consultando todos os Dendros")
        return dendroRepository.findAll()
    }

    override fun consultaDendrosPorNome(nome: String): List<Dendro> {
        logger.info("Consultando Dendros por nome")
        return dendroRepository.findByName(nome)
    }

    override fun consultaDendrosPorUsuario(userId: Long): List<Dendro> {
        return dendroRepository.findAllByUser_Id(userId)
    }

    override fun consultaDendroPorId(id: String): Dendro {
        logger.info("Consultando Dendro por id")
        return dendroRepository.findById(id).orElseThrow {
            EntityNotFoundException("Dendro não encontrada com o id fornecido.")
        }
    }

    override fun cadastrarDendro(json: DendroDTO): Dendro {
        logger.info("Cadastrando Dendro")
        val id = json.id ?: throw ErroCustomizado("O id da dendro é obrigatório")
        val dendro = Dendro(id, json.name, json.temperature, json.humidity, json.luminosity)
        return dendroRepository.save(dendro)
    }

    override fun adicionarUsuario(userId: Long, json: DendroDTO): Dendro {
        val id = json.id ?: throw ErroCustomizado("O id da dendro é obrigatório.")
        val dendro = dendroRepository.findById(id).orElseThrow {
            EntityNotFoundException("Dendro não encontrada com o id fornecido.")
        }

        if (dendro.user != null) {
            throw ErroCustomizado("A dendro já possui um usuário associado")
        }

        val user = usuarioRepository.findById(userId).orElseThrow {
            EntityNotFoundException("Usuário não encontrado com o id fornecido.")
        }

        dendro.user = user
        return dendroRepository.save(dendro)
    }

    override fun atualizarDendro(id: String, json: DendroDTO): Dendro {
        logger.info("Atualizando Dendro")
        val dendro = dendroRepository.findById(id).orElseThrow {
            EntityNotFoundException("Dendro não encontrada com o id fornecido.")
        }

        dendro.atualizarDendro(json)
        return dendroRepository.save(dendro)
    }

    override fun removerUsuarioDendro(json: DendroDTO): Dendro {
        logger.info("Removendo usuário do Dendro")
        val dendro = dendroRepository.findById(json.id ?: throw ErroCustomizado("O id da dendro é obrigatório."))
            .orElseThrow { EntityNotFoundException("Dendro não encontrada com o id fornecido.") }

        dendro.user = null
        return dendroRepository.save(dendro)
    }

    override fun deletarDendro(id: String) {
        logger.info("Deletando Dendro")
        dendroRepository.deleteById(id)
    }
}