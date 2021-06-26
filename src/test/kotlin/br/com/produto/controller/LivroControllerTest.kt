package br.com.produto.controller

import br.com.produto.builders.requests.CreateLivroRequestBuilder
import br.com.produto.response.LivroResponse
import br.com.produto.service.LivroService
import br.com.produto.utils.GeneralMessage
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest

@ExtendWith(SpringExtension::class)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@DisplayName("Livro Controller Test")
class LivroControllerTest {


    @InjectMocks
    lateinit var livroController: LivroController

    @Mock
    lateinit var livroService: LivroService

    @AfterEach
    fun tearDown() {
        RequestContextHolder.resetRequestAttributes()
    }

    @BeforeEach
    fun setUp() {

        val httpServletRequest: HttpServletRequest
        httpServletRequest = MockHttpServletRequest()
        val servletRequestAttributes = ServletRequestAttributes(httpServletRequest)
        RequestContextHolder.setRequestAttributes(servletRequestAttributes)
    }

    @Test
    @Order(1)
    fun `Criar livro quando sucesso`() {

        // Given
        //
        //Save
        BDDMockito.`when`(livroService.save(CreateLivroRequestBuilder.createLivro()))
            .thenReturn(CreateLivroRequestBuilder.responseLivro())

        // When
        //
        val responseEntity: ResponseEntity<LivroResponse> = livroController.save(CreateLivroRequestBuilder.createLivro())

        // Then
        //
        Assertions.assertThat(responseEntity).isNotNull
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.CREATED)
    }

    @Test
    @Order(2)
    fun `Atualizar livro quando sucesso`() {

        // Given
        //
        //Update
        BDDMockito.`when`(livroService.update(CreateLivroRequestBuilder.createLivro().id,CreateLivroRequestBuilder.updateLivro()))
            .thenReturn(CreateLivroRequestBuilder.updateLivro())

        // When
        //
        val responseEntity: ResponseEntity<GeneralMessage> = livroController.update(CreateLivroRequestBuilder.createLivro().id,CreateLivroRequestBuilder.updateLivro())

        // Then
        //
        Assertions.assertThat(responseEntity).isNotNull
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    }


    @Test
    @Order(3)
    fun `Obter id livro quando sucesso`() {

        // Given
        //
        //Delete
        BDDMockito.doNothing().`when`(livroService).delete(ArgumentMatchers.anyLong())

        // When
        //
        val responseEntity: ResponseEntity<LivroResponse> = livroController.findById(CreateLivroRequestBuilder.createLivro().id)

        // Then
        //
        Assertions.assertThat(responseEntity).isNotNull
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    }


    @Test
    @Order(4)
    fun `Excluir id livro quando sucesso`() {

        // Given
        //
        //FindById
        BDDMockito.doNothing().`when`(livroService).delete(ArgumentMatchers.anyLong())

        // When
        //
        val responseEntity: ResponseEntity<GeneralMessage> = livroController.deleteById(CreateLivroRequestBuilder.createLivro().id)

        // Then
        //
        Assertions.assertThat(responseEntity).isNotNull
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    }


    @Test
    @Order(5)
    fun `Consultar livro quando sucesso`() {

        // Given
        //
        //FindById
        val listLivros: List<LivroResponse> = listOf(CreateLivroRequestBuilder.responseLivro(), CreateLivroRequestBuilder.responseLivro())
        BDDMockito.`when`(livroService.findAll()).thenReturn(listLivros)

        // When
        //
        val livros = livroController.findAll().body

        // Then
        //
        Assertions.assertThat(livros).isNotNull.isNotEmpty.hasSize(2)
        Assertions.assertThat(livros?.get(0)?.titulo).isEqualTo(CreateLivroRequestBuilder.validLivro().titulo)
    }




}