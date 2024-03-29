package br.com.guilhermedealmeidafreitas.paymentsms.mapper

interface Mapper<T, U> {

    fun map(t: T): U

}