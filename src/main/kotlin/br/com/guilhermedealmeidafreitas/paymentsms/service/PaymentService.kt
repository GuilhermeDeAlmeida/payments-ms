package br.com.guilhermedealmeidafreitas.paymentsms.service
import br.com.guilhermedealmeidafreitas.paymentsms.dto.NewPaymentForm
import br.com.guilhermedealmeidafreitas.paymentsms.dto.PaymentView
import br.com.guilhermedealmeidafreitas.paymentsms.dto.UpdatePaymentForm
import br.com.guilhermedealmeidafreitas.paymentsms.exception.NotFoundException
import br.com.guilhermedealmeidafreitas.paymentsms.mapper.PaymentFormMapper
import br.com.guilhermedealmeidafreitas.paymentsms.mapper.PaymentViewMapper
import br.com.guilhermedealmeidafreitas.paymentsms.repositories.PaymentRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PaymentService (
    private val paymentRepository: PaymentRepository,
    private val paymentViewMapper: PaymentViewMapper,
    private val paymentFormMapper: PaymentFormMapper,
    private val emailService: EmailService,
    private val notFoundMessage: String = "Payment not found!"
) {
    @Cacheable(cacheNames = ["payments"], key ="#root.method.name")
    fun getAllPayments(customerId: String?, pagination: Pageable): Page<PaymentView> {
        val payments = customerId?.let {
            paymentRepository.findByCustomerId(customerId, pagination)
        }?: paymentRepository.findAll(pagination)
//        emailService.notificar()
        return payments.map { t -> paymentViewMapper.map(t) }
    }

    fun getPaymentById(id: Long): PaymentView? {
        val payment = paymentRepository.findById(id).orElseThrow { NotFoundException(notFoundMessage) }
        return paymentViewMapper.map(payment)
    }

    @CacheEvict(value = ["payments"], allEntries = true)
    fun createPayment(payment: NewPaymentForm): PaymentView {
        val payment = paymentRepository.save(paymentFormMapper.map(payment))
        return paymentViewMapper.map(payment)
    }
    @CacheEvict(value = ["payments"], allEntries = true)
    fun updatePayment(id: UpdatePaymentForm): PaymentView {
        val existingPayment = paymentRepository.findById(id.id).orElseThrow { NotFoundException(notFoundMessage) }
        val paymentSaved = paymentRepository.save(existingPayment)
        return paymentViewMapper.map(paymentSaved)
    }
    @CacheEvict(value = ["payments"], allEntries = true)
    fun deletePayment(id: Long) = paymentRepository.deleteById(id)
}