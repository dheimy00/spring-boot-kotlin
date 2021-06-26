package br.com.produto.builders.requests

import br.com.produto.builders.AutorBuilder
import br.com.produto.builders.CategoriaBuilder
import br.com.produto.request.autores.CreateAutorRequest
import br.com.produto.request.categorias.CreateCategoriaRequest
import br.com.produto.request.livros.CreateLivroRequest
import br.com.produto.request.livros.UpdateLivroRequest
import br.com.produto.response.LivroResponse
import java.time.LocalDateTime

class CreateLivroRequestBuilder {

    companion object {

        fun createLivro(): CreateLivroRequest {
            val createAutorRequest = CreateAutorRequest(1L, "Teste", "teste_descricao", "teste@gmail.com")
            val createCategoriaRequest = CreateCategoriaRequest(1L, "computacao")
            return CreateLivroRequest(
                1L,
                "Teste",
                "resumo",
                "teste_isbn",
                20.0,
                "teste_sumario",
                100,
                LocalDateTime.now(),
                createCategoriaRequest.id,
                createAutorRequest.id
            )
        }


        fun validLivro(): CreateLivroRequest {
            val createAutorRequest = CreateAutorRequest(1L, "Teste", "teste_descricao", "teste@gmail.com")
            val createCategoriaRequest = CreateCategoriaRequest(1L, "computacao")
            return CreateLivroRequest(
                1L,
                "Teste",
                "resumo",
                "teste_isbn",
                20.0,
                "teste_sumario",
                100,
                LocalDateTime.now(),
                createCategoriaRequest.id,
                createAutorRequest.id
            )
        }


        fun updateLivro(): UpdateLivroRequest {

            return UpdateLivroRequest(
                1L,
                "Teste",
                "resumo",
                "teste_isbn",
                20.0,
                "teste_sumario",
                100,
            )
        }

        fun responseLivro(): LivroResponse {
            return LivroResponse(
                1L,
                "Teste",
                "resumo",
                "teste_isbn",
                20.0,
                "teste_sumario",
                100,
                LocalDateTime.now(),
                CategoriaBuilder.createCategoria(),
                AutorBuilder.createAutor()
            )
        }
    }
}