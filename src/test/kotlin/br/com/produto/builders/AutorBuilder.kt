package br.com.produto.builders

import br.com.produto.model.Autor

class AutorBuilder {

    companion object {

        fun createAutor(): Autor {
            return Autor(1L, "Teste", "teste_descricao", "teste@gmail.com")
        }


        fun vaildAutor(): Autor {
            return Autor(1L, "Teste", "teste_descricao", "teste@gmail.com")
        }


        fun updateAutor(): Autor {
            return Autor(1L, "Teste", "teste_descricao", "teste@gmail.com")
        }

        fun fieldAutor(): Autor {
            return Autor(1L, " ", "teste_descricao", "  ")
        }
    }
}