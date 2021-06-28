package br.com.produto.integration

import br.com.produto.builders.AutorBuilder
import br.com.produto.builders.requests.CreateAutorRequestBuilder
import br.com.produto.model.Autor
import br.com.produto.repository.AutorRepository
import br.com.produto.utils.GeneralMessage
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
    @Order(2)
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

    @Test
    @Order(6)
    fun `Criar autor quando o campo nome é obrigatorio e email é obrigatorio - 400 Bad Request `() {

        val saveAutor = autorRepository.save(AutorBuilder.fieldAutor())

        val responseEntity: ResponseEntity<String> = testRestTemplate.postForEntity("/v1/api/autores/",saveAutor ,String::class.java );

        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(responseEntity.body).contains("clientMessage","O campo nome é obrigatório");
        Assertions.assertThat(responseEntity.body).contains("clientMessage","O campo email é obrigatório");
    }



}