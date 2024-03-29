package br.com.guilhermedealmeidafreitas.paymentsms.mapper

import br.com.guilhermedealmeidafreitas.paymentsms.dto.NewPaymentForm
import br.com.guilhermedealmeidafreitas.paymentsms.model.Payment
import org.springframework.stereotype.Component

@Component
class PaymentFormMapper():Mapper<NewPaymentForm, Payment> {
    override fun map(p: NewPaymentForm):Payment{
        return Payment(
            amount = p.amount,
            cardNumber = p.cardNumber,
            expirationDate = p.expirationDate,
            cvv = p.cvv,
            customerId = p.customerId
        )
    }
}