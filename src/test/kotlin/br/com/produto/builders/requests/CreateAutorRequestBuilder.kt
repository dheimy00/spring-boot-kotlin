package br.com.produto.builders.requests

import br.com.produto.model.Autor
import br.com.produto.request.autores.CreateAutorRequest
import br.com.produto.request.autores.UpdateAutorRequest
import br.com.produto.response.AutorResponse

class CreateAutorRequestBuilder {

    companion object {
        fun createAutor(): CreateAutorRequest {
            return CreateAutorRequest(1L, "Teste", "teste_descricao", "teste@gmail.com")
        }

        fun validAutor(): CreateAutorRequest {
            return CreateAutorRequest(1L, "Teste", "teste_descricao", "teste@gmail.com")
        }

        fun updateAutor(): UpdateAutorRequest {
            return UpdateAutorRequest(1L, "Teste", "teste_descricao", "teste@gmail.com")
        }

        fun responseAutor(): AutorResponse {
            return AutorResponse(1L, "Teste", "teste_descricao", "teste@gmail.com")
        }
    }

}