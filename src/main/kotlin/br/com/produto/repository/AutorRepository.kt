package br.com.produto.repository

import br.com.produto.model.Autor
import br.com.produto.model.Categoria
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AutorRepository: JpaRepository<Autor, Long> {

    fun findByEmail(email:String) : Optional<Autor>
}