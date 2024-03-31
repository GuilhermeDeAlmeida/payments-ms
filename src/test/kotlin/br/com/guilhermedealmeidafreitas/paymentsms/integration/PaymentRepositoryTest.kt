package br.com.guilhermedealmeidafreitas.paymentsms.integration

import br.com.guilhermedealmeidafreitas.paymentsms.config.ContainerConfiguration
import br.com.guilhermedealmeidafreitas.paymentsms.model.PaymentTest
import br.com.guilhermedealmeidafreitas.paymentsms.model.Payment
import br.com.guilhermedealmeidafreitas.paymentsms.repositories.PaymentRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PaymentRepositoryTest: ContainerConfiguration(){
    @Autowired
    private lateinit var paymentRepository: PaymentRepository
    private val payment: Payment = PaymentTest.build()
    private val pagination = PageRequest.of(0,5)


        companion object {
            @Container
            private val mysqlContainer = MySQLContainer<Nothing>("mysql:8.0.28").apply {
                withDatabaseName("testdb")
                withUsername("joao")
                withPassword("123456")
                withReuse(true)
            }

            @Container
            private val redisContainer = GenericContainer<Nothing>("redis:latest").apply {
                withExposedPorts(6379)

            }

            @JvmStatic
            @DynamicPropertySource
            fun properties(registry: DynamicPropertyRegistry) {
                registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
                registry.add("spring.datasource.password", mysqlContainer::getPassword);
                registry.add("spring.datasource.username", mysqlContainer::getUsername);

                registry.add("spring.redis.host", redisContainer::getContainerIpAddress)
                registry.add("spring.redis.port", redisContainer::getFirstMappedPort)
            }
        }
    @Test
    fun `deve gerar um relatorio`() {
        paymentRepository.save(payment)
        val relatorio = paymentRepository.findByCustomerId("1", pagination)

        assertThat(relatorio).isNotNull
    }



}