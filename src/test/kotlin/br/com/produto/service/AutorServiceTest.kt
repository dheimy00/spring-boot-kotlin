package br.com.produto.service

import br.com.produto.builders.AutorBuilder
import br.com.produto.builders.requests.CreateAutorRequestBuilder
import br.com.produto.exception.ResourceNotFoundException
import br.com.produto.model.Autor
import br.com.produto.repository.AutorRepository
import br.com.produto.service.implementations.AutorServiceImpl
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@DisplayName("Autor Service Test")
class AutorServiceTest {


    @InjectMocks
    lateinit var autorService: AutorServiceImpl

    @Mock
    lateinit var autorRepository: AutorRepository


    @Test
    @Order(1)
    fun `Criar autor quando sucesso`() {

        // Given
        //
        val autorBuilder = CreateAutorRequestBuilder.createAutor()
        //Save
        BDDMockito.`when`(autorRepository.save(ArgumentMatchers.any(Autor::class.java)))
            .thenReturn(AutorBuilder.createAutor())

        // When
        //
        val autor = autorService.save(autorBuilder)

        // Then
        //
        verify(autorRepository, times(1)).save(AutorBuilder.createAutor());
        Assertions.assertThat(autor).isNotNull
        Assertions.assertThat(autor.id).isNotNull
        Assertions.assertThat(autor.nome).isEqualTo(CreateAutorRequestBuilder.validAutor().nome)

    }


    @Test
    @Order(2)
    fun `Atualizar autor quando sucesso`() {

        // Given
        //
        val autor = CreateAutorRequestBuilder.createAutor()
        //Save
        BDDMockito.`when`(autorRepository.save(ArgumentMatchers.any(Autor::class.java)))
            .thenReturn(AutorBuilder.createAutor())
        //ExistsById
        BDDMockito.`when`(autorRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true)
        //FindById
        BDDMockito.`when`(autorRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.of(AutorBuilder.createAutor()))

        // When
        //
        val saveAutor = autorService.save(autor)
        //Verificar id autor
        val idAutor = autorService.findById(saveAutor.id)
        val saveUpdate = CreateAutorRequestBuilder.updateAutor()
        val updateAutor = autorService.update(idAutor.id, saveUpdate)

        // Then
        //
        Assertions.assertThat(updateAutor).isNotNull
        Assertions.assertThat(updateAutor.nome).isEqualTo(saveAutor.nome)
    }

    @Test
    @Order(3)
    fun `Excluir id autor quando sucesso`() {

        // Given
        //
        val autor = CreateAutorRequestBuilder.createAutor()
        //Save
        BDDMockito.`when`(autorRepository.save(ArgumentMatchers.any(Autor::class.java)))
            .thenReturn(AutorBuilder.createAutor())
        //ExistsById
        BDDMockito.`when`(autorRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true)
        //FindById
        BDDMockito.`when`(autorRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.of(AutorBuilder.createAutor()))

        // When
        //
        val saveAutor = autorService.save(autor)
        val idAutor = autorService.findById(saveAutor.id)
        autorService.delete(idAutor.id!!)

        // Then
        //
        verify(autorRepository).deleteById(saveAutor.id!!);
    }

    @Test
    @Order(3)
    fun `Excluir nao encontro id autor quando sucesso`() {

        // Given
        //
        //ExistsById
        BDDMockito.`when`(autorRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false)

        // When
        //
        val exception = assertThrows<ResourceNotFoundException> { autorService.delete(0) }

        // Then
        //
        verify(autorRepository).existsById(0L)
        Assertions.assertThat("Autor não encontro id 0").isEqualTo(exception.message)
    }

    @Test
    @Order(4)
    fun `Obter id autor quando sucesso`() {

        // Given
        //
        val autor = CreateAutorRequestBuilder.createAutor()
        //Save
        BDDMockito.`when`(autorRepository.save(ArgumentMatchers.any(Autor::class.java)))
            .thenReturn(AutorBuilder.createAutor())
        //ExistsById
        BDDMockito.`when`(autorRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true)
        //FindById
        BDDMockito.`when`(autorRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.of(AutorBuilder.createAutor()))

        // When
        //
        val saveAutor = autorService.save(autor)
        val idAutor = autorService.findById(saveAutor.id!!)

        // Then
        //
        verify(autorRepository, times(1)).findById(1L);
        Assertions.assertThat(idAutor.nome).isEqualTo(autor.nome)
    }


    @Test
    @Order(5)
    fun `Obter nao encontro id autor quando sucesso`() {

        // Given
        //
        //ExistsById
        BDDMockito.`when`(autorRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false)

        // When
        //
        val exception = assertThrows<ResourceNotFoundException> { autorService.findById(0L) }

        // Then
        //
        verify(autorRepository).existsById(0L)
        Assertions.assertThat("Autor não encontro id 0").isEqualTo(exception.message)
    }


    @Test
    @Order(6)
    fun `Consultar autor quando sucesso`() {

        // Given
        //
        val autor = CreateAutorRequestBuilder.createAutor()
        //Save
        BDDMockito.`when`(autorRepository.save(ArgumentMatchers.any(Autor::class.java)))
            .thenReturn(AutorBuilder.createAutor())
        //FindAll
        val listAutores: List<Autor> = listOf(AutorBuilder.createAutor(), AutorBuilder.createAutor())
        BDDMockito.`when`(autorRepository.findAll()).thenReturn(listAutores)

        // When
        //
        val saveAutor = autorService.save(autor)
        val autores = autorService.findAll()

        // Then
        //
        verify(autorRepository).findAll();
        Assertions.assertThat(autores).isNotEmpty
        Assertions.assertThat(autores[0].nome).isEqualTo(autor.nome)
    }



}

