package com.kepos.backend.config.security

import com.kepos.backend.repository.IUsuarioRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/* Implementando classe necessária para configurar autenticação */
@Service
class AutenticacaoService(
    private val repository: IUsuarioRepository
) : UserDetailsService {

    /* Método para autenticar o usuário, buscando os dados do banco de dados */
    override fun loadUserByUsername(email: String): UserDetails {
        return repository.findByEmail(email)
            ?: throw UsernameNotFoundException("Usuário não encontrado com o e-mail: $email")
    }
}