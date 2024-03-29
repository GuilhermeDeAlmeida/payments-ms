package br.com.guilhermedealmeidafreitas.paymentsms.model

import br.com.guilhermedealmeidafreitas.paymentsms.model.Payment

object PaymentTest {
        fun build() = Payment(
                id = 1,
                amount= 20.0 ,
                cardNumber= "" ,
                expirationDate= "" ,
                cvv= "" ,
                customerId= ""
        )
    }
