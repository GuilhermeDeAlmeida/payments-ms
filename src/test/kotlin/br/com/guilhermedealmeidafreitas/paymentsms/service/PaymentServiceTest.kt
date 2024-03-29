package br.com.guilhermedealmeidafreitas.paymentsms.service

import br.com.guilhermedealmeidafreitas.paymentsms.dto.PaymentView
import br.com.guilhermedealmeidafreitas.paymentsms.model.PaymentTest
import br.com.guilhermedealmeidafreitas.paymentsms.model.PaymentViewTest
import br.com.guilhermedealmeidafreitas.paymentsms.exception.NotFoundException
import br.com.guilhermedealmeidafreitas.paymentsms.mapper.PaymentFormMapper
import br.com.guilhermedealmeidafreitas.paymentsms.mapper.PaymentViewMapper
import br.com.guilhermedealmeidafreitas.paymentsms.model.Payment
import br.com.guilhermedealmeidafreitas.paymentsms.repositories.PaymentRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.*

class PaymentServiceTest {
    private val payments = listOf(PaymentTest.build())
    private val pages = PageImpl(payments)
    private val page: Pageable = mockk()
    private val paymentRepository: PaymentRepository = mockk{
        every { findByCustomerId(any()) } returns pages
}
    private val paymentViewMapper: PaymentViewMapper = mockk()
    private val paymentFormMapper: PaymentFormMapper = mockk()
    private val emailService: EmailService = mockk()
    private val paymentService = PaymentService(
            paymentRepository,paymentViewMapper, paymentFormMapper, emailService
    )

    @Test
    fun `deve listar payments a partir`() {
        val slot = slot<Payment>()
        every { paymentViewMapper.map(capture(slot)) } returns PaymentViewTest.build()
//        every { emailService.notificar() }

        paymentService.getAllPayments("1", page)

        verify(exactly = 1) { paymentRepository.findByCustomerId(any()) }
        verify(exactly = 1) { paymentViewMapper.map(any()) }
        verify(exactly = 0) { paymentRepository.findAll(page) }
    }

    @Test
    fun `deve listar not found exception quando o payment nao for achado`() {
        every { paymentRepository.findById(any()) } returns Optional.empty()

        val current = assertThrows<NotFoundException> {
            paymentService.getPaymentById(1)
        }

        assertThat(current.message).isEqualTo("Payment not found!")
    }
}