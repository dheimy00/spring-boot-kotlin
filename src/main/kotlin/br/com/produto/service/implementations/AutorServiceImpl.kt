package br.com.produto.service.implementations

import br.com.produto.converter.AutorConverter
import br.com.produto.exception.ResourceNotFoundException
import br.com.produto.repository.AutorRepository
import br.com.produto.request.autores.CreateAutorRequest
import br.com.produto.request.autores.UpdateAutorRequest
import br.com.produto.response.AutorResponse
import br.com.produto.service.AutorService
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Service

@Service
class AutorServiceImpl(private val autorRepository: AutorRepository) : AutorService {

    val converter: AutorConverter = Mappers.getMapper(AutorConverter::class.java)

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun findAll(): List<AutorResponse> {
        return autorRepository.findAll().map { converter.convertEntityToResponse(it)}
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun save(createAutorRequest: CreateAutorRequest): AutorResponse {

        val email = autorRepository.findByEmail(createAutorRequest.email)
        if (email.isPresent) {
            throw ResourceNotFoundException("Autor já existe email ${email.get().email}")
        }
        var autor = converter.convertRequestToEntity(createAutorRequest)
        autor = autorRepository.save(autor)
        return AutorResponse(autor.id!!,autor.nome,autor.descricao,autor.email)
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun update(id: Long, updateAutorRequest: UpdateAutorRequest): UpdateAutorRequest {

        if (autorRepository.existsById(id)) {
            val autorId = autorRepository.findById(id)
            autorId.get().nome = updateAutorRequest.nome
            autorId.get().descricao = updateAutorRequest.descricao
            autorId.get().email = updateAutorRequest.email
            return converter.convertEntityToUpdate(autorRepository.save(autorId.get()))
        }
        throw ResourceNotFoundException("Autor não encontro id $id")
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun findById(id: Long): AutorResponse {

        if (autorRepository.existsById(id)) {
            val autorId = autorRepository.findById(id)
            return converter.convertEntityToResponse(autorId.get())
        }
        throw ResourceNotFoundException("Autor não encontro id $id")
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun delete(id: Long) {
        if (autorRepository.existsById(id)) {
            autorRepository.deleteById(id)
            return
        }
        throw ResourceNotFoundException("Autor não encontro id $id")
    }

}