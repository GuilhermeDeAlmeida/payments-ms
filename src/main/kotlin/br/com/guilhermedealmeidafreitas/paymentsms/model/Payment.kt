package br.com.guilhermedealmeidafreitas.paymentsms.model

import jakarta.persistence.*

@Entity
@Table(name = "payments")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val amount: Double,
    val cardNumber: String,
    val expirationDate: String,
    val cvv: String,
    val customerId: String
)