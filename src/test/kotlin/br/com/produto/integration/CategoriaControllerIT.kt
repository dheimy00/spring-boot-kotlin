package br.com.produto.integration

import br.com.produto.builders.CategoriaBuilder
import br.com.produto.builders.requests.CreateCategoriaRequestBuilder
import br.com.produto.model.Categoria
import br.com.produto.repository.CategoriaRepository
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
@DisplayName("Categoria Controller Test Integration")
class CategoriaControllerIT {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @LocalServerPort
    private lateinit var port: java.lang.Integer

    @Autowired
    lateinit var categoriaRepository: CategoriaRepository

    @Test
    @Order(1)
    fun `Criar categoria quando sucesso`() {


        val categoriaRequestBuilder = CreateCategoriaRequestBuilder.createCategoria()

        val responseEntity: ResponseEntity<Categoria> = testRestTemplate.postForEntity("/v1/api/categorias", categoriaRequestBuilder, Categoria::class);

        Assertions.assertThat(responseEntity).isNotNull
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.CREATED)
        Assertions.assertThat(responseEntity.body).isNotNull
        Assertions.assertThat(responseEntity.body?.nome).isEqualTo(CreateCategoriaRequestBuilder.validCategoria().nome)
    }

    @Test
    @Order(2)
    fun `Consultar categoria quando sucesso`() {

        val saveCategoria = categoriaRepository.save(CategoriaBuilder.createCategoria())

        val listCategorias: List<Categoria>? = testRestTemplate.exchange(
            "/v1/api/categorias",
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<List<Categoria>>() {}).body

        Assertions.assertThat(listCategorias).isNotNull
        Assertions.assertThat(listCategorias?.get(0)?.nome).isEqualTo(saveCategoria.nome)

    }


    @Test
    @Order(3)
    fun `Obter id categoria quando sucesso`() {


        val saveCategoria = categoriaRepository.save(CategoriaBuilder.createCategoria())

        val categoria: Categoria = testRestTemplate.getForObject("/v1/api/categorias/{id}", Categoria::class.java,saveCategoria.id);

        Assertions.assertThat(categoria).isNotNull
        Assertions.assertThat(categoria.id).isEqualTo(saveCategoria.id)
    }

    @Test
    @Order(4)
    fun `Excluir id categoria quando sucesso`() {

        val saveCategoria = categoriaRepository.save(CategoriaBuilder.createCategoria())
        val responseEntity: ResponseEntity<GeneralMessage> = testRestTemplate.exchange("/v1/api/categorias/{id}",
            HttpMethod.DELETE ,null,
            GeneralMessage::class.java,saveCategoria.id);

        Assertions.assertThat(responseEntity).isNotNull
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    @Order(5)
    fun `Atualizar id categoria quando sucesso`() {

        val saveCategoria = categoriaRepository.save(CategoriaBuilder.createCategoria())
        saveCategoria.nome = "novo teste"
        val responseEntity: ResponseEntity<GeneralMessage> = testRestTemplate.exchange("/v1/api/categorias/{id}",
            HttpMethod.PUT ,
            HttpEntity(saveCategoria),
            GeneralMessage::class.java,saveCategoria.id);

        Assertions.assertThat(responseEntity).isNotNull
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    @Order(6)
    fun `Criar categoria quando o campo nome é obrigatorio - 400 Bad Request `() {

        val saveCategoria = categoriaRepository.save(CategoriaBuilder.fieldCategoria())

        val responseEntity: ResponseEntity<String> = testRestTemplate.postForEntity("/v1/api/categorias/",saveCategoria ,String::class.java );

        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(responseEntity.body).contains("clientMessage","O campo nome é obrigatório")
    }


}