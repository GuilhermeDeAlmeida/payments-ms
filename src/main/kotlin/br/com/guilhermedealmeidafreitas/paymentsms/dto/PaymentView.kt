package br.com.guilhermedealmeidafreitas.paymentsms.dto

data class PaymentView(
    var id: Long? = null,
    val amount: Double,
    val cardNumber: String,
    val expirationDate: String,
    val cvv: String,
    val customerId: String
)