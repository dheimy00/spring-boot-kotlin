package br.com.produto.service


import br.com.produto.request.autores.CreateAutorRequest
import br.com.produto.request.autores.UpdateAutorRequest
import br.com.produto.response.AutorResponse

interface AutorService {

    fun findAll(): List<AutorResponse>

    fun save(createAutorRequest: CreateAutorRequest): AutorResponse

    fun update(id: Long,updateAutorRequest: UpdateAutorRequest): UpdateAutorRequest

    fun findById(id: Long): AutorResponse

    fun delete(id: Long)
}