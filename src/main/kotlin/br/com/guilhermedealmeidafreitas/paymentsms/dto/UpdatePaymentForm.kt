package br.com.guilhermedealmeidafreitas.paymentsms.dto

import javax.validation.constraints.NotEmpty

data class UpdatePaymentForm(
    @field:NotEmpty
    val id: Long,
    val amount: Double,
    val cardNumber: String,
    val expirationDate: String,
    val cvv: String
)