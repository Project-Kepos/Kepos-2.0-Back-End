package com.kepos.backend.config

import com.kepos.backend.model.Dendro
import com.kepos.backend.model.Modulo
import com.kepos.backend.model.Usuario
import com.kepos.backend.repository.IDendroRepository
import com.kepos.backend.repository.IModuloRepository
import com.kepos.backend.repository.IUsuarioRepository
import jakarta.persistence.EntityNotFoundException
import org.apache.logging.log4j.LogManager
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class LoadDatabase(
    private val passwordEncoder: PasswordEncoder,
    private val repositoryUsuario: IUsuarioRepository,
    private val dendroRepository: IDendroRepository,
    private val moduloRepository: IModuloRepository
) {

    private val logger = LogManager.getLogger(javaClass)

    @Bean
    fun initDatabase(): CommandLineRunner {
        return CommandLineRunner {
            salvarUsuarios()
            val dendro = salvarDendros()
        }
    }

    private fun salvarUsuarios() {
        val senha = passwordEncoder.encode("12345")
        val novoUsuario = Usuario(nome = "Usuario da Silva", email = "usuario@email.com", senha = senha)

        repositoryUsuario.save(novoUsuario)
        logger.info("Usuário teste carregado no banco de dados: $novoUsuario")
    }

    private fun salvarDendros(): Dendro {
        val dendro01 = Dendro("11111111111", "Dendro 01", 0.0, 31.22, 0)
        val dendro02 = Dendro("22222222222", "Dendro 02", 45.0, 28.22, 0)
        val dendro03 = Dendro("33333333333", "Dendro 03", 90.0, 16.71, 0)
        val dendro04 = Dendro("44444444444", "Dendro 04", 180.0, 22.44, 0)
        val dendro05 = Dendro("CC50E3A24670", "Dendro Device", 0.0, 0.0, 0)

        dendroRepository.saveAll(listOf(dendro01, dendro02, dendro03, dendro04, dendro05))
        logger.info("Dendros carregados no banco de dados")

        return dendro05
    }
}