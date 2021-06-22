package br.com.produto.response

import br.com.produto.model.Autor
import br.com.produto.model.Categoria
import java.time.LocalDateTime

data class LivroResponse(

    var id: Long,
    var titulo: String,
    var resumo: String,
    var isbn: String,
    var preco: Double,
    var sumario: String,
    var pagina: Int,
    var dataPublicacao: LocalDateTime,
    var categoria: Categoria,
    var autor: Autor

) {
}