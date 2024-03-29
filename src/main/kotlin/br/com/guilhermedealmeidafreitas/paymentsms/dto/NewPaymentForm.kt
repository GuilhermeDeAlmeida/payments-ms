package br.com.guilhermedealmeidafreitas.paymentsms.dto

import javax.validation.constraints.NotEmpty

data class NewPaymentForm(
    @field:NotEmpty(message = "Amount cannot be empty")
    val amount: Double,
    @field:NotEmpty(message = "Card number cannot be empty")
    val cardNumber: String,
    @field:NotEmpty(message = "Expiration date cannot be empty")
    val expirationDate: String,
    @field:NotEmpty(message = "CVV cannot be empty")
    val cvv: String,
    @field:NotEmpty(message = "Customer ID cannot be empty")
    val customerId: String
)