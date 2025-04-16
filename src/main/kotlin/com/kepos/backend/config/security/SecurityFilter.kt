package com.kepos.backend.config.security

import com.kepos.backend.repository.IUsuarioRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
@Qualifier("securityFilter")
class SecurityFilter(
    private val tokenService: TokenService,
    private val repository: IUsuarioRepository
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val tokenJWT = recuperarToken(request) // Puxando o token

        if (tokenJWT != null) {
            val subject = tokenService.getSubject(tokenJWT) // Puxando os dados do token (Email)
            val usuario = repository.findByEmail(subject) // Puxando o usuário do banco pelo e-mail

            val authentication = UsernamePasswordAuthenticationToken(usuario, null, usuario?.authorities)
            SecurityContextHolder.getContext().authentication = authentication // Forçando autenticação
            request.setAttribute("userId", tokenService.getUserId(tokenJWT)) // Passando o id do cliente do token pra frente
        }

        filterChain.doFilter(request, response) // Dando continuidade à cadeia de filtros
    }

    // Método que puxa a header Authorization e verifica se existe um Bearer token anexado, retornando ele caso exista
    private fun recuperarToken(request: HttpServletRequest): String? {
        val authorizationHeader = request.getHeader("Authorization")
        return authorizationHeader?.replace("Bearer ", "")
    }
}