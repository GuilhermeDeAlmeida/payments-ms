package br.com.guilhermedealmeidafreitas.paymentsms.model

import org.springframework.security.core.userdetails.UserDetails

class UserDetail(private val user: User):UserDetails {
    override fun getAuthorities() = user.role

    override fun getPassword()= user.password

    override fun getUsername() = user.username

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}