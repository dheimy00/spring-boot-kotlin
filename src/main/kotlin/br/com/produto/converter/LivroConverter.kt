package br.com.produto.converter

import br.com.produto.model.Livro
import br.com.produto.request.livros.CreateLivroRequest
import br.com.produto.request.livros.UpdateLivroRequest
import br.com.produto.response.LivroResponse
import org.mapstruct.Mapper

@Mapper
interface LivroConverter {

    fun convertRequestToEntity(createLivroRequest: CreateLivroRequest):Livro

    fun convertEntityToUpdate(livro: Livro): UpdateLivroRequest

    fun convertEntityToResponse(livro: Livro): LivroResponse

}