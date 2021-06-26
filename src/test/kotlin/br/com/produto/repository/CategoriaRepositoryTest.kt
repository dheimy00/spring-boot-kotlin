package br.com.produto.repository

import br.com.produto.builders.CategoriaBuilder
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@DisplayName("Categoria Repository Test")
class CategoriaRepositoryTest {

    @Autowired
    lateinit var categoriaRepository: CategoriaRepository

    @Test
    fun `Criar categoria quando sucesso`() {

        val categoriaBuilder = CategoriaBuilder.createCategoria()
        val categoria = categoriaRepository.save(categoriaBuilder)

        Assertions.assertThat(categoria).isNotNull
        Assertions.assertThat(categoria.id).isNotNull
        Assertions.assertThat(categoria.nome).isEqualTo(categoriaBuilder.nome)

    }


    @Test
    fun `Atualizar categoria quando sucesso`() {

        val categoriaBuilder = CategoriaBuilder.createCategoria()

        val saveCategoria = categoriaRepository.save(categoriaBuilder)
        saveCategoria.nome = "computacao"
        val updateCategoria = categoriaRepository.save(saveCategoria)

        Assertions.assertThat(updateCategoria.id).isNotNull
        Assertions.assertThat(updateCategoria.nome).isEqualTo(saveCategoria.nome)
    }

    @Test
    fun `Excluir categoria quando sucesso`() {

        val categoriaBuilder = CategoriaBuilder.createCategoria()

        val saveCategoria = categoriaRepository.save(categoriaBuilder)
        categoriaRepository.deleteById(saveCategoria.id!!)
        val categoria = categoriaRepository.findById(saveCategoria.id!!)

        Assertions.assertThat(categoria.isEmpty).isTrue()
    }


    @Test
    fun `Obter id categoria quando sucesso`() {

        val categoriaBuilder = CategoriaBuilder.createCategoria()
        val saveCategoria = categoriaRepository.save(categoriaBuilder)
        val categoria = categoriaRepository.findById(saveCategoria.id!!)

        Assertions.assertThat(categoria.get().nome).isEqualTo(categoriaBuilder.nome)
    }

    @Test
    fun `Consultar categoria quando sucesso`() {

        val categoriaBuilder = CategoriaBuilder.createCategoria()
        val saveCategoria =  categoriaRepository.save(categoriaBuilder)
        val categorias = categoriaRepository.findAll()

        Assertions.assertThat(categorias).isNotEmpty
        Assertions.assertThat(categorias[0].nome).isEqualTo(saveCategoria.nome)
    }
}