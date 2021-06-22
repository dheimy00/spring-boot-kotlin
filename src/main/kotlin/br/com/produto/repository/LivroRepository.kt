package br.com.produto.repository

import br.com.produto.model.Livro
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LivroRepository: JpaRepository<Livro,Long> {

    fun findByTitulo(titulo: String): Optional<Livro>
}