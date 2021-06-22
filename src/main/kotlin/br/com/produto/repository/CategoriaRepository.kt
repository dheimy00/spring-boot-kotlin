package br.com.produto.repository

import br.com.produto.model.Categoria
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CategoriaRepository:JpaRepository<Categoria,Long>{

    fun findByNome(nome: String): Optional<Categoria>
}