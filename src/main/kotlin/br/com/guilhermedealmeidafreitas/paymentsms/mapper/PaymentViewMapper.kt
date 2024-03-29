package br.com.guilhermedealmeidafreitas.paymentsms.mapper

import br.com.guilhermedealmeidafreitas.paymentsms.dto.PaymentView
import br.com.guilhermedealmeidafreitas.paymentsms.model.Payment
import org.springframework.stereotype.Component

@Component
class PaymentViewMapper:Mapper<Payment, PaymentView> {
    override fun map(p: Payment): PaymentView {
        return PaymentView(
            id = p.id,
            amount = p.amount,
            cardNumber = p.cardNumber,
            expirationDate = p.expirationDate,
            cvv = p.cvv,
            customerId = p.customerId
        )
    }
}