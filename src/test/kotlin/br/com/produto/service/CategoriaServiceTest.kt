package br.com.produto.service

import br.com.produto.builders.CategoriaBuilder
import br.com.produto.builders.requests.CreateCategoriaRequestBuilder
import br.com.produto.exception.ResourceNotFoundException
import br.com.produto.model.Categoria
import br.com.produto.repository.CategoriaRepository
import br.com.produto.service.implementations.CategoriaServiceImpl
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@DisplayName("Categoria Service Test")
class CategoriaServiceTest {

    @InjectMocks
    lateinit var categoriaService: CategoriaServiceImpl

    @Mock
    lateinit var categoriaRepository: CategoriaRepository


    @Test
    @Order(1)
    fun `Criar categoria quando sucesso`() {

        // Given
        //
        val categoria = CreateCategoriaRequestBuilder.createCategoria()
        //Save
        BDDMockito.`when`(categoriaRepository.save(ArgumentMatchers.any(Categoria::class.java)))
            .thenReturn(CategoriaBuilder.createCategoria())

        // When
        //
        val saveCategoria = categoriaService.save(categoria)

        // Then
        //
        verify(categoriaRepository, Mockito.times(1)).save(CategoriaBuilder.createCategoria());
        Assertions.assertThat(saveCategoria).isNotNull
        Assertions.assertThat(saveCategoria.id).isNotNull
        Assertions.assertThat(saveCategoria.nome).isEqualTo(CreateCategoriaRequestBuilder.validCategoria().nome)

    }


    @Test
    @Order(2)
    fun `Atualizar categoria quando sucesso`() {

        // Given
        //
        val categoria = CreateCategoriaRequestBuilder.createCategoria()
        //Save
        BDDMockito.`when`(categoriaRepository.save(ArgumentMatchers.any(Categoria::class.java)))
            .thenReturn(CategoriaBuilder.createCategoria())
        //ExistsById
        BDDMockito.`when`(categoriaRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true)
        //FindById
        BDDMockito.`when`(categoriaRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.of(CategoriaBuilder.createCategoria()))

        // When
        //
        val saveCategoria = categoriaService.save(categoria)
        //Verificar id categoria
        val idCategoria = categoriaService.findById(saveCategoria.id)
        val saveUpdate = CreateCategoriaRequestBuilder.updateCategoria()
        val updateCategoria = categoriaService.update(idCategoria.id, saveUpdate)

        // Then
        //
        Assertions.assertThat(updateCategoria).isNotNull
        Assertions.assertThat(updateCategoria.nome).isEqualTo(saveCategoria.nome)
    }

    @Test
    @Order(3)
    fun `Excluir id categoria quando sucesso`() {

        // Given
        //
        val categoria = CreateCategoriaRequestBuilder.createCategoria()
        //Save
        BDDMockito.`when`(categoriaRepository.save(ArgumentMatchers.any(Categoria::class.java)))
            .thenReturn(CategoriaBuilder.createCategoria())
        //ExistsById
        BDDMockito.`when`(categoriaRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true)

        // When
        //
        val saveCategoria = categoriaService.save(categoria)
        categoriaService.delete(saveCategoria.id!!)

        // Then
        //
       verify(categoriaRepository).deleteById(saveCategoria.id!!);
    }

    @Test
    @Order(3)
    fun `Excluir nao encontro id categoria quando sucesso`() {

        // Given
        //
        //ExistsById
        BDDMockito.`when`(categoriaRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false)

        // When
        //
        val exception = assertThrows<ResourceNotFoundException> { categoriaService.delete(0) }

        // Then
        //
        Mockito.verify(categoriaRepository).existsById(0L)
        Assertions.assertThat("Categoria não encontro id 0").isEqualTo(exception.message)
    }

    @Test
    @Order(4)
    fun `Obter id categoria quando sucesso`() {

        // Given
        //
        val categoria = CreateCategoriaRequestBuilder.createCategoria()
        //Save
        BDDMockito.`when`(categoriaRepository.save(ArgumentMatchers.any(Categoria::class.java)))
            .thenReturn(CategoriaBuilder.createCategoria())
        //ExistsById
        BDDMockito.`when`(categoriaRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true)
        //FindById
        BDDMockito.`when`(categoriaRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.of(CategoriaBuilder.createCategoria()))

        // When
        //
        val saveCategoria = categoriaService.save(categoria)
        val idCategoria = categoriaService.findById(saveCategoria.id!!)

        // Then
        //
        Mockito.verify(categoriaRepository, Mockito.times(1)).findById(1L);
        Assertions.assertThat(idCategoria.nome).isEqualTo(categoria.nome)
    }


    @Test
    @Order(5)
    fun `Obter nao encontro id categoria quando sucesso`() {

        // Given
        //
        //ExistsById
        BDDMockito.`when`(categoriaRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false)

        // When
        //
        val exception = assertThrows<ResourceNotFoundException> { categoriaService.findById(0L) }

        // Then
        //
        Mockito.verify(categoriaRepository).existsById(0L)
        Assertions.assertThat("Categoria não encontro id 0").isEqualTo(exception.message)
    }


    @Test
    @Order(6)
    fun `Consultar categoria quando sucesso`() {

        // Given
        //
        val categoria = CreateCategoriaRequestBuilder.createCategoria()
        //Save
        BDDMockito.`when`(categoriaRepository.save(ArgumentMatchers.any(Categoria::class.java)))
            .thenReturn(CategoriaBuilder.createCategoria())
        //FindAll
        val listCategoria: List<Categoria> = listOf(CategoriaBuilder.createCategoria(), CategoriaBuilder.createCategoria())
        BDDMockito.`when`(categoriaRepository.findAll()).thenReturn(listCategoria)

        // When
        //
        categoriaService.save(categoria)
        val categorias = categoriaService.findAll()

        // Then
        //
        Mockito.verify(categoriaRepository).findAll();
        Assertions.assertThat(categorias).isNotEmpty
        Assertions.assertThat(categorias[0].nome).isEqualTo(categoria.nome)
    }
}