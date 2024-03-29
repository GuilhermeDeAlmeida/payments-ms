package br.com.guilhermedealmeidafreitas.paymentsms.controller

import br.com.guilhermedealmeidafreitas.paymentsms.config.JWTUtil
import br.com.guilhermedealmeidafreitas.paymentsms.model.Role
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentControllerTest {
    @Autowired
    private lateinit var jwtUtil: JWTUtil
    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext
    private lateinit var mockMvc: MockMvc
    private var token: String? = null
    companion object {
        private const val RECURSO = "/payments"
        private const val RECURSO_ID = RECURSO.plus("/%s")
    }

    @BeforeEach
    fun setup() {
        token = generateToken();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                 .apply<DefaultMockMvcBuilder?>(
                        SecurityMockMvcConfigurers
                                .springSecurity()).build()
    }

    @Test
    fun `deve retornar codigo 400 quando chamar payments sem token`() {
        mockMvc.get(RECURSO).andExpect { status { is4xxClientError() } }
    }

    @Test
    fun `deve retornar codigo 200 quando chamar pyaments com token`(){
        mockMvc.get(RECURSO){
            headers {token?.let {this.setBearerAuth(it)}}
        }.andExpect { status{is2xxSuccessful()} }
    }
    @Test
    fun `deve retornar codigo 200 quando chamar pyaments COM ID com token`(){
        mockMvc.get(RECURSO_ID.format("1")){
            headers {token?.let { this.setBearerAuth(it) }}
        }.andExpect { status{is2xxSuccessful()} }
    }
    fun generateToken(): String?{
        var authorities = mutableListOf(Role(1, "ROLE_READ"))
        return jwtUtil.generateToken("ana_da_silva", authorities)
    }

}