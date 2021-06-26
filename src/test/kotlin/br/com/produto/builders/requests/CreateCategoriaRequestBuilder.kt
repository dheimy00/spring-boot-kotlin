package br.com.produto.builders.requests

import br.com.produto.model.Categoria
import br.com.produto.request.categorias.CreateCategoriaRequest
import br.com.produto.request.categorias.UpdateCategoriaResquest
import br.com.produto.response.CategoriaResponse

class CreateCategoriaRequestBuilder {

    companion object {
        fun createCategoria(): CreateCategoriaRequest {
            return CreateCategoriaRequest(1L, "computacao")
        }

        fun validCategoria(): CreateCategoriaRequest {
            return CreateCategoriaRequest(1L, "computacao")
        }


        fun updateCategoria(): UpdateCategoriaResquest {
            return UpdateCategoriaResquest(1L, "computacao")
        }


        fun responseCategoria(): CategoriaResponse {
            return CategoriaResponse(1L, "computacao")
        }
    }
}