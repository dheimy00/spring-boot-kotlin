package br.com.produto.service.implementations

import br.com.produto.converter.CategoriaConverter
import br.com.produto.exception.ResourceNotFoundException
import br.com.produto.repository.CategoriaRepository
import br.com.produto.request.categorias.CreateCategoriaRequest
import br.com.produto.request.categorias.UpdateCategoriaResquest
import br.com.produto.response.CategoriaResponse
import br.com.produto.service.CategoriaService
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Service

@Service
class CategoriaServiceImpl(private val categoriaRepository: CategoriaRepository) : CategoriaService {

    val converter: CategoriaConverter = Mappers.getMapper(CategoriaConverter::class.java)


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun findAll(): List<CategoriaResponse> {
        return categoriaRepository.findAll().map { converter.convertEntityToResponse(it) }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun save(createCategoriaRequest: CreateCategoriaRequest): CategoriaResponse {

        var nome = categoriaRepository.findByNome(createCategoriaRequest.nome)
        if (nome.isPresent) {
            throw ResourceNotFoundException("Categoria já existe nome ${createCategoriaRequest.nome}")
        }
        var categoria = converter.convertRequestToEntity(createCategoriaRequest)
        categoria = categoriaRepository.save(categoria)
        return CategoriaResponse(categoria.id!!,categoria.nome)
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun update(id: Long, updateCategoriaResquest: UpdateCategoriaResquest): UpdateCategoriaResquest {

        if (categoriaRepository.existsById(id)) {
            val categoriaId = categoriaRepository.findById(id)
            categoriaId.get().nome = updateCategoriaResquest.nome
            return converter.convertEntityToUpdate(categoriaRepository.save(categoriaId.get()))
        }
        throw ResourceNotFoundException("Categoria não encontro id $id")

    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun findById(id: Long): CategoriaResponse {

        if (categoriaRepository.existsById(id)) {
            return converter.convertEntityToResponse(categoriaRepository.findById(id).get())
        }
        throw ResourceNotFoundException("Categoria não encontro id $id")
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun delete(id: Long) {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id)
            return
        }
        throw ResourceNotFoundException("Categoria não encontro id $id")

    }

}