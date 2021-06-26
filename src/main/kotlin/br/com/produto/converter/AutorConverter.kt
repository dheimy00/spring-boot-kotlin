package br.com.produto.converter

import br.com.produto.model.Autor
import br.com.produto.request.autores.CreateAutorRequest
import br.com.produto.request.autores.UpdateAutorRequest
import br.com.produto.response.AutorResponse
import org.mapstruct.Mapper

@Mapper
interface AutorConverter {

    fun convertRequestToEntity(createAutorRequest: CreateAutorRequest ): Autor

    fun convertEntityToUpdate(autor: Autor): UpdateAutorRequest

    fun convertEntityToResponse(autor: Autor): AutorResponse
}