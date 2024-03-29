package br.com.guilhermedealmeidafreitas.paymentsms.config

import br.com.guilhermedealmeidafreitas.paymentsms.security.JWTAuthenticationFilter
import br.com.guilhermedealmeidafreitas.paymentsms.security.JWTLoginFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    val jwtUtil: JWTUtil
){

    @Bean
    fun securityFilterChain(http: HttpSecurity, config: AuthenticationConfiguration): SecurityFilterChain {
//        http{
        http
            .csrf {
                it.disable()
            }
            .authorizeHttpRequests { authorize ->
                authorize.requestMatchers(HttpMethod.GET, "/swagger-ui.html")?.permitAll()
                authorize.requestMatchers(HttpMethod.GET, "/index.html")?.permitAll()
                authorize.requestMatchers(HttpMethod.GET, "/swagger-ui/index.html")?.permitAll()
                authorize.requestMatchers(HttpMethod.GET, "/swagger-ui/*")?.permitAll()
                authorize.requestMatchers(HttpMethod.GET, "/v3/api-docs/**")?.permitAll()
                authorize.requestMatchers(HttpMethod.POST, "/login")?.permitAll()


                authorize.requestMatchers("/payments").hasAuthority("ROLE_READ")

                authorize.anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .headers {
                it.frameOptions{it.disable()}
            }
            .addFilterBefore(
                JWTLoginFilter(
                    authManager = config.authenticationManager,
                    jwtUtil = jwtUtil
                ),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .addFilterBefore(
                JWTAuthenticationFilter(jwtUtil = jwtUtil), UsernamePasswordAuthenticationFilter::class.java
            )
        return http.build()
    }

    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()
}