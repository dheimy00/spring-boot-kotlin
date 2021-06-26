package br.com.produto.converter

import br.com.produto.model.Categoria
import br.com.produto.request.categorias.CreateCategoriaRequest
import br.com.produto.request.categorias.UpdateCategoriaResquest
import br.com.produto.response.CategoriaResponse
import org.mapstruct.Mapper

@Mapper
interface CategoriaConverter {

    fun convertRequestToEntity(createCategoriaRequest: CreateCategoriaRequest ): Categoria

    fun convertEntityToUpdate(categoria: Categoria): UpdateCategoriaResquest

    fun convertEntityToResponse(categoria: Categoria): CategoriaResponse
}