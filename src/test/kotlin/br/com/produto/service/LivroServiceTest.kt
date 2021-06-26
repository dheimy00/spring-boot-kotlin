package br.com.produto.service

import br.com.produto.builders.AutorBuilder
import br.com.produto.builders.CategoriaBuilder
import br.com.produto.builders.requests.CreateAutorRequestBuilder
import br.com.produto.builders.requests.CreateCategoriaRequestBuilder
import br.com.produto.exception.ResourceNotFoundException
import br.com.produto.model.Autor
import br.com.produto.model.Categoria
import br.com.produto.model.Livro
import br.com.produto.repository.AutorRepository
import br.com.produto.repository.CategoriaRepository
import br.com.produto.repository.LivroRepository
import br.com.produto.request.livros.CreateLivroRequest
import br.com.produto.request.livros.UpdateLivroRequest
import br.com.produto.service.implementations.LivroServiceImpl
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.util.*


@ExtendWith(MockitoExtension::class)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@DisplayName("Livro Service Test")
class LivroServiceTest {

    @InjectMocks
    lateinit var livroService: LivroServiceImpl

    @Mock
    lateinit var livroRepository: LivroRepository

    @Mock
    lateinit var autorRepository: AutorRepository

    @Mock
    lateinit var categoriaRepository: CategoriaRepository

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

        /* Entity */
        //Categoria
        var categoria = CategoriaBuilder.createCategoria()
        categoria = categoriaRepository.save(categoria)
        categoriaRepository.existsById(categoria.id!!)
        val idCategoria = categoriaRepository.findById(categoria.id!!)


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

        /* Entity */
        //Autor
        var autor = AutorBuilder.createAutor()
        autor = autorRepository.save(autor)
        autorRepository.existsById(autor.id!!)
        val idAutor = autorRepository.findById(autor.id!!)

    }


    @Test
    @Order(1)
    fun `Criar livro quando sucesso`() {


        // Given
        //
        /*Request*/
        val autorRequest = CreateAutorRequestBuilder.createAutor()
        val categoriaRequest = CreateCategoriaRequestBuilder.createCategoria()
        val livroRequest = CreateLivroRequest(
            1L,
            "Teste",
            "resumo",
            "teste_isbn",
            20.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            categoriaRequest.id,
            autorRequest.id
        )

        //Livro
        val livroBuilder = Livro(
            1L,
            "Teste",
            "resumo",
            "teste_isbn",
            20.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            CategoriaBuilder.createCategoria(),
            AutorBuilder.createAutor()
        )
        //Save
        BDDMockito.`when`(livroRepository.save(ArgumentMatchers.any(Livro::class.java))).thenReturn(livroBuilder)

        // When
        //
        val saveLivro = livroService.save(livroRequest)

        // Then
        //
        Assertions.assertThat(saveLivro).isNotNull
        Assertions.assertThat(saveLivro.id).isNotNull
        Assertions.assertThat(saveLivro.titulo).isEqualTo(livroRequest.titulo)

    }


    @Test
    @Order(2)
    fun `Verifique o título já existe no livro  quando sucesso`() {

        // Given
        //
        /*Request*/
        val livroRequest = CreateLivroRequest(
            1L,
            "Teste",
            "resumo",
            "teste_isbn",
            20.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            0L,
            0L
        )


        //Livro
        val livroBuilder = Livro(
            1L,
            "Teste",
            "resumo",
            "teste_isbn",
            20.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            CategoriaBuilder.createCategoria(),
            AutorBuilder.createAutor()
        )
          //FindByTitulo
        BDDMockito.`when`(livroRepository.findByTitulo(ArgumentMatchers.anyString())).thenReturn(Optional.of(livroBuilder))

        // When
        //
        val exception = assertThrows<ResourceNotFoundException> { livroService.save(livroRequest) }

        // Then
        //
        Assertions.assertThat("Livro já existe título Teste").isEqualTo(exception.message)

    }


    @Test
    @Order(3)
    fun `Atualizar livro quando sucesso`() {


        //Livro
        val updatelivroRequest = UpdateLivroRequest(
            1L,
            "Teste",
            "resumo",
            "teste_isbn",
            20.0,
            "teste_sumario",
            100
        )

        val livroBuilder = Livro(
            1L,
            "Teste",
            "resumo",
            "teste_isbn",
            20.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            CategoriaBuilder.createCategoria(),
            AutorBuilder.createAutor()
        )
        //Save
        BDDMockito.`when`(livroRepository.save(ArgumentMatchers.any(Livro::class.java))).thenReturn(livroBuilder)

        //ExistsById
        BDDMockito.`when`(livroRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true)
        //FindById
        BDDMockito.`when`(livroRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.of(livroBuilder))

        // When
        //
        val Updatelivro = livroService.update(updatelivroRequest.id,updatelivroRequest)

        // Then
        //
        Assertions.assertThat(Updatelivro.id).isNotNull
        Assertions.assertThat(Updatelivro.titulo).isEqualTo("Teste")
    }

    @Test
    @Order(4)
    fun `Excluir livro quando sucesso`() {

        // Given
        //
        /*Request*/
        val autorRequest = CreateAutorRequestBuilder.createAutor()
        val categoriaRequest = CreateCategoriaRequestBuilder.createCategoria()
        val livroRequest = CreateLivroRequest(
            1L,
            "Teste",
            "resumo",
            "teste_isbn",
            20.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            categoriaRequest.id,
            autorRequest.id
        )
        /* Entity */
        // Livro
        val livroBuilder = Livro(
            1L,
            "Teste",
            "resumo",
            "teste_isbn",
            20.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            CategoriaBuilder.createCategoria(),
            AutorBuilder.createAutor()
        )
        //Save
        BDDMockito.`when`(livroRepository.save(ArgumentMatchers.any(Livro::class.java))).thenReturn(livroBuilder)
        //ExistsById
        BDDMockito.`when`(livroRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true)

        // When
        //
        val saveLivro = livroService.save(livroRequest)
        livroService.delete(saveLivro.id)

        // Then
        //
        verify(livroRepository).deleteById(saveLivro.id!!);
    }

    @Test
    @Order(5)
    fun `Obter id livro quando sucesso`() {

        // Given
        //
        /*Request*/
        val autorRequest = CreateAutorRequestBuilder.createAutor()
        val categoriaRequest = CreateCategoriaRequestBuilder.createCategoria()
        val livroRequest = CreateLivroRequest(
            1L,
            "Teste",
            "resumo",
            "teste_isbn",
            20.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            categoriaRequest.id,
            autorRequest.id
        )

        /* Entity */
        // Livro
        val livroBuilder = Livro(
            1L,
            "Teste",
            "resumo",
            "teste_isbn",
            20.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            CategoriaBuilder.createCategoria(),
            AutorBuilder.createAutor()
        )
        //Save
        BDDMockito.`when`(livroRepository.save(ArgumentMatchers.any(Livro::class.java))).thenReturn(livroBuilder)
        //ExistsById
        BDDMockito.`when`(livroRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true)
        //FindById
        BDDMockito.`when`(livroRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.of(livroBuilder))

        // When
        //
        val saveLivro = livroService.save(livroRequest)
        val livro = livroService.findById(saveLivro.id)

        // Then
        //
        Assertions.assertThat(livro.titulo).isEqualTo("Teste")
    }

    @Test
    @Order(6)
    fun `Consultar livro quando sucesso`() {

        // Given
        //
        /*Request*/
        val autorRequest = CreateAutorRequestBuilder.createAutor()
        val categoriaRequest = CreateCategoriaRequestBuilder.createCategoria()
        val livroRequest = CreateLivroRequest(
            1L,
            "Teste",
            "resumo",
            "teste_isbn",
            20.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            categoriaRequest.id,
            autorRequest.id
        )

        /* Entity */
        // Livro
        val livroBuilder = Livro(
            1L,
            "Teste",
            "resumo",
            "teste_isbn",
            20.0,
            "teste_sumario",
            100,
            LocalDateTime.now(),
            CategoriaBuilder.createCategoria(),
            AutorBuilder.createAutor()
        )
        //Save
        BDDMockito.`when`(livroRepository.save(ArgumentMatchers.any(Livro::class.java))).thenReturn(livroBuilder)
        //FindAll
        val listLivros: List<Livro> = listOf(livroBuilder, livroBuilder)
        BDDMockito.given(livroRepository.findAll()).willReturn(listLivros)

        // When
        //
        livroService.save(livroRequest)
        val livros = livroService.findAll()

        // Then
        //
        Assertions.assertThat(livros).isNotEmpty
    }

}