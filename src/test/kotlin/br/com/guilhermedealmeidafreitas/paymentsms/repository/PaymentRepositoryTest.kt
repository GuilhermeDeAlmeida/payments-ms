package br.com.guilhermedealmeidafreitas.paymentsms.repository

import br.com.guilhermedealmeidafreitas.paymentsms.model.PaymentTest
import br.com.guilhermedealmeidafreitas.paymentsms.model.Payment
import br.com.guilhermedealmeidafreitas.paymentsms.repositories.PaymentRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PaymentRepositoryTest {
    @Autowired
    private lateinit var paymentRepository: PaymentRepository
    private val payment: Payment = PaymentTest.build()


    @Test
    fun `deve gerar um relatorio`() {
        paymentRepository.save(payment)
        val relatorio = paymentRepository.findByCustomerId("1")

        assertThat(relatorio).isNotNull
    }



}