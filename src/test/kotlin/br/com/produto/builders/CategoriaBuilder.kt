package br.com.produto.builders

import br.com.produto.model.Categoria


class CategoriaBuilder {

    companion object {
        fun createCategoria(): Categoria {
            return Categoria(1L, "computacao")
        }

        fun vaildCategoria(): Categoria {
            return Categoria(1L, "computacao")
        }

        fun updateCategoria(): Categoria {
            return Categoria(1L, "computacao")
        }

        fun fieldCategoria() : Categoria{
            return Categoria(1L, " ")
        }
    }
}