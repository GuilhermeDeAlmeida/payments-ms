package br.com.guilhermedealmeidafreitas.paymentsms.repositories

import br.com.guilhermedealmeidafreitas.paymentsms.model.Payment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentRepository : JpaRepository<Payment, Long> {
    fun findByCustomerId(customerId: String, pagination: Pageable): Page<Payment>
}