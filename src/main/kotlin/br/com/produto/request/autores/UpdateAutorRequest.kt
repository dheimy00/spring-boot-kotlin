package br.com.produto.request.autores

data class UpdateAutorRequest(
    var id: Long,
    var nome: String,
    var descricao: String,
    var email: String
    )
