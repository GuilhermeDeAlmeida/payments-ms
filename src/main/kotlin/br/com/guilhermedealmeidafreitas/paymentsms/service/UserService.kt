package br.com.guilhermedealmeidafreitas.paymentsms.service

import br.com.guilhermedealmeidafreitas.paymentsms.model.UserDetail
import br.com.guilhermedealmeidafreitas.paymentsms.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository : UserRepository):UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findByUsername(username)?: throw RuntimeException()
        return UserDetail(user)
    }
}