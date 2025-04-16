package com.kepos.backend.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.kepos.backend.config.error.ErroCustomizado
import com.kepos.backend.model.Usuario

import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class TokenService {

    companion object {
        private const val TOKENSECRET = "VALOR_ALEATORIO_PRIVADO_SECRETO_PRA_DIFERENCIAR_NOSSO_BACK_END"
        private const val RESTSIGNATURE = "API REST KEPOS"
    }

    fun gerarToken(usuario: Usuario): String {
        return try {
            val algorithm = Algorithm.HMAC256(TOKENSECRET)
            JWT.create()
                .withIssuer(RESTSIGNATURE)
                .withSubject(usuario.email)
                .withClaim("userId", usuario.id)
                .withExpiresAt(dataExpiracao())
                .sign(algorithm)
        } catch (exception: JWTCreationException) {
            throw ErroCustomizado("Erro ao gerar token jwt")
        }
    }

    fun getSubject(tokenJWT: String): String {
        return try {
            val algorithm = Algorithm.HMAC256(TOKENSECRET)
            JWT.require(algorithm)
                .withIssuer(RESTSIGNATURE)
                .build()
                .verify(tokenJWT)
                .subject
        } catch (exception: JWTVerificationException) {
            throw ErroCustomizado("Token JWT inválido ou expirado!")
        }
    }

    fun getUserId(tokenJWT: String): Long {
        return try {
            val algorithm = Algorithm.HMAC256(TOKENSECRET)
            JWT.require(algorithm)
                .withIssuer(RESTSIGNATURE)
                .build()
                .verify(tokenJWT)
                .getClaim("userId")
                .asLong()
        } catch (exception: JWTVerificationException) {
            throw ErroCustomizado("Token JWT inválido ou expirado!")
        }
    }

    private fun dataExpiracao(): Instant {
        return LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.of("-03:00"))
    }
}