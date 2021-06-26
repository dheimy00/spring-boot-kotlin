package br.com.produto.controller


import br.com.produto.builders.requests.CreateCategoriaRequestBuilder
import br.com.produto.response.CategoriaResponse
import br.com.produto.service.CategoriaService
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
@DisplayName("Categoria Controller Test")
class CategoriaControllerTest {

    @InjectMocks
    lateinit var categoriaController: CategoriaController

    @Mock
    lateinit var categoriaService: CategoriaService

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
    fun `Criar categoria quando sucesso`() {

        // Given
        //
        val createCategoriaRequestrBuilder = CreateCategoriaRequestBuilder.createCategoria()
        //Save
        BDDMockito.`when`(categoriaService.save(createCategoriaRequestrBuilder))
            .thenReturn(CreateCategoriaRequestBuilder.responseCategoria())

        // When
        //
        val categoria = categoriaController.save(createCategoriaRequestrBuilder).body

        // Then
        //
        Assertions.assertThat(categoria).isNotNull
        Assertions.assertThat(categoria?.nome).isEqualTo(CreateCategoriaRequestBuilder.validCategoria().nome)
    }


    @Test
    @Order(2)
    fun `Atualizar categoria quando sucesso`() {

        // Given
        //
        //Update
        BDDMockito.`when`(categoriaService.update(CreateCategoriaRequestBuilder.updateCategoria().id, CreateCategoriaRequestBuilder.updateCategoria()))
            .thenReturn(CreateCategoriaRequestBuilder.updateCategoria())
        // When
        //
        val idCategoria = categoriaController.update( CreateCategoriaRequestBuilder.updateCategoria().id,CreateCategoriaRequestBuilder.updateCategoria()).body

        // Then
        //
        Assertions.assertThat(idCategoria).isNotNull
    }

    @Test
    @Order(3)
    fun `Obter id categoria quando sucesso`() {

        // Given
        //
        //FindById
        BDDMockito.`when`(categoriaService.findById(ArgumentMatchers.anyLong())).thenReturn(CreateCategoriaRequestBuilder.responseCategoria())

        // When
        //
        val idCategoria = categoriaController.findById( CreateCategoriaRequestBuilder.createCategoria().id).body

        // Then
        //
        Assertions.assertThat(idCategoria).isNotNull
        Assertions.assertThat(idCategoria?.nome).isEqualTo(CreateCategoriaRequestBuilder.validCategoria().nome)
    }

    @Test
    @Order(3)
    fun `Excluir id categoria quando sucesso`() {

        // Given
        //
        //Delete
        BDDMockito.doNothing().`when`(categoriaService).delete(ArgumentMatchers.anyLong())

        // When
        //
        val response: ResponseEntity<GeneralMessage> = categoriaController.deleteById( CreateCategoriaRequestBuilder.createCategoria().id)

        // Then
        //
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

    }


    @Test
    @Order(3)
    fun `Consultar categoria quando sucesso`() {

        // Given
        //
        //FindAll
        val listCategoria: List<CategoriaResponse> = listOf(CreateCategoriaRequestBuilder.responseCategoria(), CreateCategoriaRequestBuilder.responseCategoria())
        BDDMockito.`when`(categoriaService.findAll()).thenReturn(listCategoria)

        // When
        //
        val categorias = categoriaController.findAll().body

        // Then
        //
        Assertions.assertThat(categorias).isNotNull
        Assertions.assertThat(categorias?.get(0)?.nome).isEqualTo(CreateCategoriaRequestBuilder.validCategoria().nome)
    }

}