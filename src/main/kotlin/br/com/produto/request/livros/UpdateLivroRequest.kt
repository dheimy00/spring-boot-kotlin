package br.com.produto.request.livros

data class UpdateLivroRequest(

    var titulo: String,
    var resumo: String,
    var isbn: String,
    var preco: Double,
    var sumario: String,
    var pagina: Int,
)