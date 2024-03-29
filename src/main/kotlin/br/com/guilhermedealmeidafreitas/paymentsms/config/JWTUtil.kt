package br.com.guilhermedealmeidafreitas.paymentsms.config

import br.com.guilhermedealmeidafreitas.paymentsms.service.UserService
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.util.*

@Component
class JWTUtil(
    private val userService: UserService
) {
    @Value("\${jwt.secret}")
    private var secret: String = "secret"
    private val expiration = 600000
    private val key = Algorithm.HMAC256(secret.toByteArray())

    fun generateToken(username: String, authorities: MutableCollection<out GrantedAuthority>): String? {
        return JWT.create()
            .withSubject(username)
            .withClaim("role", authorities.map { it.authority })
            .withExpiresAt(Date(System.currentTimeMillis() + expiration))
            .sign(key)
    }

    fun isValid(jwt: String?): Boolean {
        return try {
            JWT.require(key)
                .build()
                .verify(jwt)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getAuthentication(jwt: String?): Authentication {
        val username = JWT.require(key)
            .build()
            .verify(jwt)
            .subject
        val user = userService.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(username, null, user.authorities)
    }
}