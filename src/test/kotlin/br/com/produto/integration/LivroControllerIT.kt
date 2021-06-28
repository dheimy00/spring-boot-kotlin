package br.com.produto.integration

import br.com.produto.builders.AutorBuilder
import br.com.produto.builders.CategoriaBuilder
import br.com.produto.builders.requests.CreateAutorRequestBuilder
import br.com.produto.builders.requests.CreateCategoriaRequestBuilder
import br.com.produto.model.Autor
import br.com.produto.model.Categoria
import br.com.produto.model.Livro
import br.com.produto.repository.AutorRepository
import br.com.produto.repository.CategoriaRepository
import br.com.produto.repository.LivroRepository
import br.com.produto.request.livros.CreateLivroRequest
import br.com.produto.response.LivroResponse
import br.com.produto.utils.GeneralMessage
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import java.util.*


@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@DisplayName("Livro Controller Test Integration")
class LivroControllerIT {


    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @LocalServerPort
    private lateinit var port: java.lang.Integer

    @MockBean
    lateinit var livroRepository: LivroRepository

    @MockBean
    lateinit var categoriaRepository: CategoriaRepository

    @MockBean
    lateinit var autorRepository: AutorRepository

    @BeforeEach
    fun setUp() {

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Categoria
        //
        //Save
        BDDMockito.`when`(categoriaRepository.save(ArgumentMatchers.any(Categoria::class.java)))
            .thenReturn(CategoriaBuilder.createCategoria())
        //ExistsById
        BDDMockito.`when`(categoriaRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true)
        //FindById
        BDDMockito.`when`(categoriaRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.of(CategoriaBuilder.createCategoria()))


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Autor
        //
        //Save
        BDDMockito.`when`(autorRepository.save(ArgumentMatchers.any(Autor::class.java)))
            .thenReturn(AutorBuilder.createAutor())
        //ExistsById
        BDDMockito.`when`(autorRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true)
        //FindById
        BDDMockito.`when`(autorRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.of(AutorBuilder.createAutor()))

    }

    @Test
    @Order(1)
    fun `Consultar livro quando sucesso`() {

        categoriaRepository.existsById(CreateCategoriaRequestBuilder.createCategoria().id!!)
        val idCategoria = categoriaRepository.findById(CreateCategoriaRequestBuilder.createCategoria().id!!)

        autorRepository.existsById(CreateAutorRequestBuilder.createAutor().id)
        val idAutor = autorRepository.findById(CreateAutorRequestBuilder.createAutor().id)

        //Livro
        val livroBuilder = Livro(
            1L,
            "titulo",
            "resumo",
            "teste_isbn5",
            21.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            idCategoria.get(),
            idAutor.get()
        )

        //Save
        BDDMockito.`when`(livroRepository.save(ArgumentMatchers.any(Livro::class.java))).thenReturn(livroBuilder)
        //FindAll
        val listaLivros: List<Livro> = listOf(livroBuilder, livroBuilder)
        BDDMockito.given(livroRepository.findAll()).willReturn(listaLivros)

        val saveLivro = livroRepository.save(livroBuilder)
        val livros = livroRepository.findAll();

        val livrosEntity: List<Livro>? = testRestTemplate.exchange(
            "/v1/api/livros",
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<List<Livro>>() {}).body

        Assertions.assertThat(livrosEntity).isNotNull
        Assertions.assertThat(livrosEntity?.get(0)?.titulo).isEqualTo(livros.get(0)?.titulo)

    }

    @Test
    @Order(2)
    fun `Criar livro quando sucesso`() {

        categoriaRepository.existsById(CreateCategoriaRequestBuilder.createCategoria().id!!)
        val idCategoria = categoriaRepository.findById(CreateCategoriaRequestBuilder.createCategoria().id!!)

        autorRepository.existsById(CreateAutorRequestBuilder.createAutor().id)
        val idAutor = autorRepository.findById(CreateAutorRequestBuilder.createAutor().id)

        //Request livro
        val livroRequest = CreateLivroRequest(
            1L,
            "titulo",
            "resumo",
            "teste_isbn1",
            21.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            idCategoria.get().id!!,
            idAutor.get().id!!
        )

        //Entity livro
        val livroBuilder = Livro(
            1L,
            "titulo",
            "resumo",
            "teste_isbn5",
            21.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            idCategoria.get(),
            idAutor.get()
        )

        //Save
        BDDMockito.`when`(livroRepository.save(ArgumentMatchers.any(Livro::class.java))).thenReturn(livroBuilder)

        val responseEntity: ResponseEntity<LivroResponse> =
            testRestTemplate.postForEntity("/v1/api/livros", livroRequest, LivroResponse::class.java);

        Assertions.assertThat(responseEntity).isNotNull
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.CREATED)
        Assertions.assertThat(responseEntity.body).isNotNull
    }


    @Test
    @Order(3)
    fun `Obter id livro quando sucesso`() {

        categoriaRepository.existsById(CreateCategoriaRequestBuilder.createCategoria().id!!)
        val idCategoria = categoriaRepository.findById(CreateCategoriaRequestBuilder.createCategoria().id!!)

        autorRepository.existsById(CreateAutorRequestBuilder.createAutor().id)
        val idAutor = autorRepository.findById(CreateAutorRequestBuilder.createAutor().id)

        //Livro
        val livroBuilder = Livro(
            1L,
            "titulo",
            "resumo",
            "teste_isbn2",
            21.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            idCategoria.get(),
            idAutor.get()
        )
        //Save
        BDDMockito.`when`(livroRepository.save(ArgumentMatchers.any(Livro::class.java))).thenReturn(livroBuilder)
        //FindById
        BDDMockito.`when`(livroRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true)
        //FindById
        BDDMockito.`when`(livroRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(livroBuilder))

        val findLivro = livroRepository.findById(livroBuilder.id!!)
        val livro: Livro = testRestTemplate.getForObject("/v1/api/livros/{id}", Livro::class.java, findLivro.get().id);

        Assertions.assertThat(livro).isNotNull
        Assertions.assertThat(livro.id).isEqualTo(livro.id)
    }

    @Test
    @Order(4)
    fun `Excluir id livro quando sucesso`() {


        categoriaRepository.existsById(CreateCategoriaRequestBuilder.createCategoria().id!!)
        val idCategoria = categoriaRepository.findById(CreateCategoriaRequestBuilder.createCategoria().id!!)

        autorRepository.existsById(CreateAutorRequestBuilder.createAutor().id)
        val idAutor = autorRepository.findById(CreateAutorRequestBuilder.createAutor().id)

        //Livro
        val livroBuilder = Livro(
            1L,
            "titulo",
            "resumo",
            "teste_isbn3",
            21.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            idCategoria.get(),
            idAutor.get()
        )

        //Save
        BDDMockito.`when`(livroRepository.save(ArgumentMatchers.any(Livro::class.java))).thenReturn(livroBuilder)
        //ExistsById
        BDDMockito.`when`(livroRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true)
        //DeleteById
        BDDMockito.doNothing().`when`(livroRepository).deleteById(ArgumentMatchers.anyLong())

        val saveLivro = livroRepository.save(livroBuilder)


        val responseEntity: ResponseEntity<GeneralMessage> = testRestTemplate.exchange(
            "/v1/api/livros/{id}",
            HttpMethod.DELETE, null,
            GeneralMessage::class.java, saveLivro.id
        );

        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    @Order(5)
    fun `Atualizar id livro quando sucesso`() {

        categoriaRepository.existsById(CreateCategoriaRequestBuilder.createCategoria().id!!)
        val idCategoria = categoriaRepository.findById(CreateCategoriaRequestBuilder.createCategoria().id!!)

        autorRepository.existsById(CreateAutorRequestBuilder.createAutor().id)
        val idAutor = autorRepository.findById(CreateAutorRequestBuilder.createAutor().id)

        //Livro
        val livroBuilder = Livro(
            1L,
            "titulo",
            "resumo",
            "teste_isbn4",
            21.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            idCategoria.get(),
            idAutor.get()
        )

        //Save
        BDDMockito.`when`(livroRepository.save(ArgumentMatchers.any(Livro::class.java))).thenReturn(livroBuilder)
        //ExistsById
        BDDMockito.`when`(livroRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true)
        //FindById
        BDDMockito.`when`(livroRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(livroBuilder))

        val findLivro = livroRepository.findById(livroBuilder.id!!)

        findLivro.get().titulo = "novo teste"
        val responseEntity: ResponseEntity<GeneralMessage> = testRestTemplate.exchange(
            "/v1/api/livros/{id}",
            HttpMethod.PUT,
            HttpEntity(findLivro.get()),
            GeneralMessage::class.java, findLivro.get().id
        );

        Assertions.assertThat(responseEntity).isNotNull
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    @Order(6)
    fun `Criar categoria quando o campo título é obrigatorio - 400 Bad Request `() {

        categoriaRepository.existsById(CreateCategoriaRequestBuilder.createCategoria().id!!)
        val idCategoria = categoriaRepository.findById(CreateCategoriaRequestBuilder.createCategoria().id!!)

        autorRepository.existsById(CreateAutorRequestBuilder.createAutor().id)
        val idAutor = autorRepository.findById(CreateAutorRequestBuilder.createAutor().id)

        //Request livro
        val livroRequest = CreateLivroRequest(
            1L,
            " ",
            "resumo",
            "teste_isbn1",
            21.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            idCategoria.get().id!!,
            idAutor.get().id!!
        )

        val responseEntity: ResponseEntity<String> = testRestTemplate.postForEntity("/v1/api/livros/",livroRequest ,String::class.java );

        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(responseEntity.body).contains("clientMessage","O campo título é obrigatório")
    }



}