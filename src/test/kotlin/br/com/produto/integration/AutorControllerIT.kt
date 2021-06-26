package br.com.produto.integration

import br.com.produto.builders.AutorBuilder
import br.com.produto.builders.requests.CreateAutorRequestBuilder
import br.com.produto.model.Autor
import br.com.produto.repository.AutorRepository
import br.com.produto.response.AutorResponse
import br.com.produto.utils.GeneralMessage
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@DisplayName("Autor Controller Test Integration")
class AutorControllerIT {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @LocalServerPort
    private lateinit var port: java.lang.Integer

    @Autowired
    lateinit var autorRepository: AutorRepository

    @Test
    @Order(1)
    fun `Criar autor quando sucesso`() {


        val createAutorRequestrBuilder = CreateAutorRequestBuilder.createAutor()

        val responseEntity: ResponseEntity<Autor> =
            testRestTemplate.postForEntity("/v1/api/autores", createAutorRequestrBuilder, Autor::class);

        Assertions.assertThat(responseEntity).isNotNull
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.CREATED)
        Assertions.assertThat(responseEntity.body).isNotNull
        Assertions.assertThat(responseEntity.body?.nome).isEqualTo(CreateAutorRequestBuilder.validAutor().nome)
    }


    @Test
    @Order(2)
    fun `Consultar autor quando sucesso`() {

        val autor = AutorBuilder.createAutor()
        autor.email = "teste1@gmail.com"
        val saveAutor = autorRepository.save(autor)

        val listAutores: List<Autor>? = testRestTemplate.exchange(
            "/v1/api/autores",
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<List<Autor>>() {}).body


        Assertions.assertThat(listAutores).isNotNull
        Assertions.assertThat(listAutores?.get(0)?.nome).isEqualTo(saveAutor.nome)

    }


    @Test
    @Order(3)
    fun `Obter id autor quando sucesso`() {


        val saveAutor = autorRepository.save(AutorBuilder.createAutor())

        val autor: Autor = testRestTemplate.getForObject("/v1/api/autores/{id}", Autor::class.java, saveAutor.id);

        Assertions.assertThat(autor).isNotNull
        Assertions.assertThat(autor.id).isEqualTo(saveAutor.id)
    }

    @Test
    @Order(4)
    fun `Excluir id autor quando sucesso`() {

        val saveAutor = autorRepository.save(AutorBuilder.createAutor())
        val responseEntity: ResponseEntity<GeneralMessage> = testRestTemplate.exchange(
            "/v1/api/autores/{id}",
            HttpMethod.DELETE,
            null,
            GeneralMessage::class.java,
            saveAutor.id
        );

        Assertions.assertThat(responseEntity).isNotNull
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    @Order(5)
    fun `Atualizar id autor quando sucesso`() {

        val saveAutor = autorRepository.save(AutorBuilder.createAutor())


        saveAutor.nome = "novo teste"
        val responseEntity: ResponseEntity<GeneralMessage> = testRestTemplate.exchange(
            "/v1/api/autores/{id}", HttpMethod.PUT,
            HttpEntity(saveAutor), GeneralMessage::class.java, saveAutor.id
        );

        Assertions.assertThat(responseEntity).isNotNull
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    }

}