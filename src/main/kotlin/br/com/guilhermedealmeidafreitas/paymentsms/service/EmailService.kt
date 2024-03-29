package br.com.guilhermedealmeidafreitas.paymentsms.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService (
    private val javaMailSender: JavaMailSender
)  {
    fun notificar() {
        val message = SimpleMailMessage()

        message.subject = "Assunto do Email"
        message.text = "Email enviado pelo serviço de pagamentos"
        message.setTo("guilhermeaf11@gmail.com")

        javaMailSender.send(message)
    }
}