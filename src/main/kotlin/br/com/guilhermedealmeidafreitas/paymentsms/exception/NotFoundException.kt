package br.com.guilhermedealmeidafreitas.paymentsms.exception
import java.lang.RuntimeException

class NotFoundException(message: String?) : RuntimeException(message) {
}