package br.com.produto.service

import br.com.produto.response.CategoriaResponse
import br.com.produto.request.categorias.CreateCategoriaRequest
import br.com.produto.request.categorias.UpdateCategoriaResquest

interface CategoriaService {

    fun findAll(): List<CategoriaResponse>

    fun save(createCategoriaRequest: CreateCategoriaRequest): CategoriaResponse

    fun update(id: Long,updateCategoriaResquest: UpdateCategoriaResquest): UpdateCategoriaResquest

    fun findById(id: Long):CategoriaResponse

    fun delete(id: Long)
}