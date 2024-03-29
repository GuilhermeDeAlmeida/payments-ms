package br.com.guilhermedealmeidafreitas.paymentsms.model

import br.com.guilhermedealmeidafreitas.paymentsms.dto.PaymentView
import br.com.guilhermedealmeidafreitas.paymentsms.model.Payment

object PaymentViewTest {
        fun build() = PaymentView(
                id = 1,
                amount= 20.0 ,
                cardNumber= "" ,
                expirationDate= "" ,
                cvv= "" ,
                customerId= ""
        )
    }
