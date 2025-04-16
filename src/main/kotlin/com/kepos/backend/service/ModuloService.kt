package com.kepos.backend.service

import com.kepos.backend.config.error.ErroCustomizado
import com.kepos.backend.dto.ModuloDTO
import com.kepos.backend.repository.IDendroRepository
import com.kepos.backend.repository.IModuloRepository
import com.kepos.backend.model.Modulo
import jakarta.persistence.EntityNotFoundException
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service

@Service
class ModuloService(
    private val moduloRepository: IModuloRepository,
    private val dendroRepository: IDendroRepository
) : IModuloService {

    private val logger = LogManager.getLogger(javaClass)

    // TODO: Revisar necessidade deste método
    override fun consultaTodosModulos(): List<Modulo> {
        logger.info("Consultando todos os Modulos")
        return moduloRepository.findAll()
    }

    override fun consultaPorId(id: Long): Modulo {
        logger.info("Consultando Modulo por id")
        return moduloRepository.findById(id).orElseThrow {
            EntityNotFoundException("Módulo não encontrado com o id fornecido.")
        }
    }

    override fun consultaPorDendro(id: String): List<Modulo> {
        logger.info("Consultando Modulo por Dendro")
        if (!dendroRepository.existsById(id)) {
            throw EntityNotFoundException("Dendro não encontrada com o id fornecido.")
        }
        return moduloRepository.findAllByDendro_Id(id)
    }

    // TODO: Verifica se de fato o limite de módulos será 4 por dendro
    // TODO: Impedir valores null pós testes
    override fun cadastrarModulo(json: ModuloDTO): Modulo {
        logger.info("Cadastrando Modulo")
        val dendro = dendroRepository.findById(json.dendroId ?: throw EntityNotFoundException("Dendro ID é obrigatório"))
            .orElseThrow { EntityNotFoundException("Dendro não encontrada com o id fornecido.") }

        if (dendro.modules.size >= 4) {
            throw ErroCustomizado("O limite máximo de módulos por dendro são 4.")
        }

        val modulo = Modulo(
            name = json.name,
            desc = json.desc,
            humidity = json.humidity,
            humidityLevel = json.humidityLevel,
            dendro = dendro
        )

        return moduloRepository.save(modulo)
    }

    // TODO: Impedir valores null pós testes
    override fun atualizarModulo(id: Long, json: ModuloDTO): Modulo {
        logger.info("Atualizando Modulo")
        val modulo = moduloRepository.findById(id).orElseThrow {
            EntityNotFoundException("Módulo não encontrado com o id fornecido.")
        }

        // Atualizar os campos
        modulo.atualizarModulo(json)
        return moduloRepository.save(modulo)
    }

    override fun deletarModulo(id: Long) {
        logger.info("Deletando Modulo")
        moduloRepository.deleteById(id)
    }
}