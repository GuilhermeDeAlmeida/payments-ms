package br.com.guilhermedealmeidafreitas.paymentsms.controller
import br.com.guilhermedealmeidafreitas.paymentsms.dto.NewPaymentForm
import br.com.guilhermedealmeidafreitas.paymentsms.dto.PaymentView
import br.com.guilhermedealmeidafreitas.paymentsms.dto.UpdatePaymentForm
import br.com.guilhermedealmeidafreitas.paymentsms.service.PaymentService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/payments")
class PaymentController @Autowired constructor(private val paymentService: PaymentService) {

    @GetMapping

    fun getAllPayments(
        @RequestParam(required = false) custumerId: String?,
        @PageableDefault(size = 5, sort = ["id"],direction = Sort.Direction.DESC) pagination : Pageable
    ): Page<PaymentView> = paymentService.getAllPayments(custumerId, pagination)

    @GetMapping("/{id}")
    fun getPaymentById(@PathVariable id: Long)= paymentService.getPaymentById(id)

    @PostMapping
    @Transactional
    fun createPayment(@RequestBody @Valid payment: NewPaymentForm, uriBuilder: UriComponentsBuilder): ResponseEntity<PaymentView> {
        val paymentView = paymentService.createPayment(payment)
        val uri = uriBuilder.path("payments/${paymentView.id}").build().toUri()
        return ResponseEntity.created(uri).body(paymentView)
    }

    @PutMapping()
    @Transactional
    fun updatePayment(@RequestBody @Valid payment: UpdatePaymentForm):ResponseEntity<PaymentView> {
        val paymentView = paymentService.updatePayment(payment)
        return ResponseEntity.ok(paymentView)
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePayment(@PathVariable id: Long) = paymentService.deletePayment(id)
}