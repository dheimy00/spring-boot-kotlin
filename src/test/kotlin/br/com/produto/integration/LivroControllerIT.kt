package br.com.produto.integration

import br.com.produto.builders.AutorBuilder
import br.com.produto.builders.CategoriaBuilder
import br.com.produto.builders.requests.CreateAutorRequestBuilder
import br.com.produto.builders.requests.CreateCategoriaRequestBuilder
import br.com.produto.model.Livro
import br.com.produto.repository.AutorRepository
import br.com.produto.repository.CategoriaRepository
import br.com.produto.repository.LivroRepository
import br.com.produto.request.livros.CreateLivroRequest
import br.com.produto.response.LivroResponse
import br.com.produto.utils.GeneralMessage
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*
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
import java.time.LocalDateTime


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@DisplayName("Livro Controller Test Integration")
class LivroControllerIT {


    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @LocalServerPort
    private lateinit var port: java.lang.Integer

    @Autowired
    lateinit var livroRepository: LivroRepository

    @Autowired
    lateinit var categoriaRepository: CategoriaRepository

    @Autowired
    lateinit var autorRepository: AutorRepository


    @Test
    @Order(1)
    fun `Criar livro quando sucesso`() {

        val saveCategoria = categoriaRepository.save(CategoriaBuilder.createCategoria())
        categoriaRepository.existsById(saveCategoria.id!!)
        val categoria =categoriaRepository.findById(saveCategoria.id!!)

        val saveAutor = autorRepository.save(AutorBuilder.createAutor())
        autorRepository.existsById(saveAutor.id!!)
        val autor = autorRepository.findById(saveAutor.id!!)

        val livroRequest = CreateLivroRequest(
            1L,
            "titulo",
            "resumo",
            "teste_isbn1",
            21.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            categoria.get().id!!,
            autor.get().id!!
        )

        val responseEntity: ResponseEntity<LivroResponse> =
            testRestTemplate.postForEntity("/v1/api/livros", livroRequest, LivroResponse::class.java);

        Assertions.assertThat(responseEntity).isNotNull
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.CREATED)
        Assertions.assertThat(responseEntity.body).isNotNull
    }


    @Test
    @Order(2)
    fun `Obter id livro quando sucesso`() {

        val saveCategoria = categoriaRepository.save(CategoriaBuilder.createCategoria())
        categoriaRepository.existsById(saveCategoria.id!!)
        val idCategoria = categoriaRepository.findById(saveCategoria.id!!)

        val autor = AutorBuilder.createAutor()
        autor.email = "teste2@gmail.com"
        val saveAutor = autorRepository.save(autor)
        autorRepository.existsById(saveAutor.id!!)
        val idAutor = autorRepository.findById(saveAutor.id!!)

        //Livro
        val livroBuilder = Livro(
            1L,
            "teste_titulo2",
            "resumo",
            "teste_isbn2",
            21.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            idCategoria.get(),
            idAutor.get()
        )

        val saveLivro = livroRepository.save(livroBuilder)

        val livro: Livro = testRestTemplate.getForObject("/v1/api/livros/{id}", Livro::class.java, saveLivro.id);

        Assertions.assertThat(livro).isNotNull
        Assertions.assertThat(livro.id).isEqualTo(livro.id)
    }

    @Test
    @Order(3)
    fun `Excluir id livro quando sucesso`() {


        val saveCategoria = categoriaRepository.save(CategoriaBuilder.createCategoria())
        categoriaRepository.existsById(saveCategoria.id!!)
        val idCategoria = categoriaRepository.findById(saveCategoria.id!!)


        val autor = AutorBuilder.createAutor()
        autor.email = "teste3@gmail.com"
        val saveAutor = autorRepository.save(autor)
        autorRepository.existsById(saveAutor.id!!)
        val idAutor = autorRepository.findById(saveAutor.id!!)

        //Livro
        val livroBuilder = Livro(
            1L,
            "teste_titulo3",
            "resumo",
            "teste_isbn3",
            21.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            idCategoria.get(),
            idAutor.get()
        )

        val saveLivro = livroRepository.save(livroBuilder)

        val responseEntity: ResponseEntity<GeneralMessage> = testRestTemplate.exchange(
            "/v1/api/livros/{id}",
            HttpMethod.DELETE, null,
            GeneralMessage::class.java, saveLivro.id
        );

        Assertions.assertThat(responseEntity).isNotNull
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    @Order(4)
    fun `Atualizar id livro quando sucesso`() {

        val saveCategoria = categoriaRepository.save(CategoriaBuilder.createCategoria())
        categoriaRepository.existsById(saveCategoria.id!!)
        val idCategoria = categoriaRepository.findById(saveCategoria.id!!)

        val autor = AutorBuilder.createAutor()
        autor.email = "teste4@gmail.com"
        val saveAutor = autorRepository.save(autor)
        autorRepository.existsById(saveAutor.id!!)
        val idAutor = autorRepository.findById(saveAutor.id!!)

        //Livro
        val livroBuilder = Livro(
            1L,
            "teste_titulo4",
            "resumo",
            "teste_isbn4",
            21.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            idCategoria.get(),
            idAutor.get()
        )

        val saveLivro = livroRepository.save(livroBuilder)

        saveLivro.titulo = "novo teste"
        val responseEntity: ResponseEntity<GeneralMessage> = testRestTemplate.exchange(
            "/v1/api/livros/{id}",
            HttpMethod.PUT,
            HttpEntity(saveLivro),
            GeneralMessage::class.java, saveLivro.id
        );

        Assertions.assertThat(responseEntity).isNotNull
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    @Order(5)
    fun `Consultar livro quando sucesso`() {

        val saveCategoria = categoriaRepository.save(CategoriaBuilder.createCategoria())
        categoriaRepository.existsById(saveCategoria.id!!)
        val idCategoria = categoriaRepository.findById(saveCategoria.id!!)

        val autor = AutorBuilder.createAutor()
        autor.email = "teste5@gmail.com"
        val saveAutor = autorRepository.save(autor)
        autorRepository.existsById(saveAutor.id!!)
        val idAutor = autorRepository.findById(saveAutor.id!!)

        //Livro
        val livroBuilder = Livro(
            1L,
            "teste_titulo5",
            "resumo",
            "teste_isbn5",
            21.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            idCategoria.get(),
            idAutor.get()
        )


        val saveLivro = livroRepository.save(livroBuilder)
        val livros = livroRepository.findAll();

        val listLivros: List<Livro>? = testRestTemplate.exchange(
            "/v1/api/livros",
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<List<Livro>>() {}).body

        Assertions.assertThat(listLivros).isNotNull
        Assertions.assertThat(listLivros?.get(0)?.titulo).isEqualTo(livros.get(0)?.titulo)

    }

}