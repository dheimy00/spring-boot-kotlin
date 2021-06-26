package br.com.produto.repository

import br.com.produto.builders.AutorBuilder
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@DisplayName("Autor Repository Test")
class AutorRepositoryTest {

    @Autowired
    lateinit var autorRepository: AutorRepository

    @Test
    @Order(1)
    fun `Criar autor quando sucesso` () {

        val autorBuilder = AutorBuilder.createAutor()
        val autor = autorRepository.save(autorBuilder)

        Assertions.assertThat(autor).isNotNull
        Assertions.assertThat(autor.id).isNotNull
        Assertions.assertThat(autor.nome).isEqualTo(autorBuilder.nome)

    }

    @Test
    @Order(2)
    fun `Atualizar autor quando sucesso`() {

        val autorBuilder = AutorBuilder.createAutor()

        val saveAutor = autorRepository.save(autorBuilder)
        saveAutor.nome = "Teste"
        val updateAutor = autorRepository.save(saveAutor)

        Assertions.assertThat(updateAutor.id).isNotNull
        Assertions.assertThat(updateAutor.nome).isEqualTo(saveAutor.nome)
    }

    @Test
    @Order(3)
    fun `Excluir autor quando sucesso`() {

        val autorBuilder = AutorBuilder.createAutor()

        val saveAutor = autorRepository.save(autorBuilder)
        autorRepository.deleteById(saveAutor.id!!)
        val autor = autorRepository.findById(saveAutor.id!!)

        Assertions.assertThat(autor.isEmpty).isTrue()
    }


    @Test
    @Order(4)
    fun `Obter id autor quando sucesso`() {

        val autorBuilder = AutorBuilder.createAutor()
        val saveAutor = autorRepository.save(autorBuilder)
        val autor = autorRepository.findById(saveAutor.id!!)

        Assertions.assertThat(autor.get().nome).isEqualTo(autorBuilder.nome)
    }

    @Test
    @Order(5)
    fun `Consultar autor quando sucesso`() {

        val autorBuilder = AutorBuilder.createAutor()
        val saveAutor =  autorRepository.save(autorBuilder)
        val autores = autorRepository.findAll()

        Assertions.assertThat(autores).isNotEmpty
        Assertions.assertThat(autores[0].nome).isEqualTo(saveAutor.nome)
    }
}