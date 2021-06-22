package br.com.produto.service

import br.com.produto.response.LivroResponse
import br.com.produto.request.livros.CreateLivroRequest
import br.com.produto.request.livros.UpdateLivroRequest

interface LivroService {

    fun findAll(): List<LivroResponse>

    fun save(createLivroRequest: CreateLivroRequest): LivroResponse

    fun update(id: Long,updateLivroRequest: UpdateLivroRequest): UpdateLivroRequest

    fun findById(id: Long):LivroResponse

    fun delete(id: Long)

}