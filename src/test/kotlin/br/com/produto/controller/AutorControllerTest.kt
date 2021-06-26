package br.com.produto.controller

import br.com.produto.builders.requests.CreateAutorRequestBuilder
import br.com.produto.response.AutorResponse
import br.com.produto.service.AutorService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest

@ExtendWith(SpringExtension::class)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@DisplayName("Autor Controller Test")
class AutorControllerTest {


    @InjectMocks
    lateinit var autorController: AutorController

    @Mock
    lateinit var autorService: AutorService

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
    fun `Criar autor quando sucesso`() {

        // Given
        //
        val createAutorRequestrBuilder = CreateAutorRequestBuilder.createAutor()
        //Save
        BDDMockito.`when`(autorService.save(CreateAutorRequestBuilder.createAutor()))
            .thenReturn(CreateAutorRequestBuilder.responseAutor())

        // When
        //
        val autor = autorController.save(createAutorRequestrBuilder).body

        // Then
        //
        Assertions.assertThat(autor).isNotNull
        Assertions.assertThat(autor?.nome).isEqualTo(CreateAutorRequestBuilder.validAutor().nome)
    }


    @Test
    @Order(2)
    fun `Atualizar autor quando sucesso`() {

        // Given
        //
        //Update
        BDDMockito.`when`(
            autorService.update(
                CreateAutorRequestBuilder.updateAutor().id,
                CreateAutorRequestBuilder.updateAutor()
            )
        )
            .thenReturn(CreateAutorRequestBuilder.updateAutor())

        val updateAutorRequest = CreateAutorRequestBuilder.updateAutor()

        // When
        //
        val updateAutor = autorController.update(CreateAutorRequestBuilder.createAutor().id, updateAutorRequest).body

        // Then
        //
        Assertions.assertThat(updateAutor).isNotNull
    }

    @Test
    @Order(3)
    fun `Obter id autor quando sucesso`() {

        // Given
        //
        //FindById
        BDDMockito.`when`(autorService.findById(ArgumentMatchers.anyLong()))
            .thenReturn(CreateAutorRequestBuilder.responseAutor())

        // When
        //
        val idAutor  = autorController.findById(CreateAutorRequestBuilder.createAutor().id).body

        // Then
        //
        Assertions.assertThat(idAutor).isNotNull
        Assertions.assertThat(idAutor?.id).isEqualTo(CreateAutorRequestBuilder.validAutor().id)
        Assertions.assertThat(idAutor?.nome).isEqualTo(CreateAutorRequestBuilder.validAutor().nome)
    }

    @Test
    @Order(4)
    fun `Excluir id autor quando sucesso`() {

        // Given
        //
        //Delete
        BDDMockito.doNothing().`when`(autorService).delete(ArgumentMatchers.anyLong())

        // When
        //
        val idAutor  = autorController.deleteById(CreateAutorRequestBuilder.createAutor().id).body

        // Then
        //
        Assertions.assertThat(idAutor).isNotNull
    }


    @Test
    @Order(5)
    fun `Consultar autor quando sucesso`() {

        // Given
        //
        //FindAll
        val list: List<AutorResponse> =
            listOf(CreateAutorRequestBuilder.responseAutor(), CreateAutorRequestBuilder.responseAutor())
        BDDMockito.`when`(autorService.findAll()).thenReturn(list)

        // When
        //
        val listAutores: List<AutorResponse>? = autorController.findAll().body

        // Then
        //
        Assertions.assertThat(listAutores).isNotNull

        Assertions.assertThat(listAutores?.get(0)?.nome).isEqualTo(CreateAutorRequestBuilder.validAutor().nome)

    }


}