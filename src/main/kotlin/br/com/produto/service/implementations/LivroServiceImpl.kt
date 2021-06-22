package br.com.produto.service.implementations

import br.com.produto.converter.LivroConverter
import br.com.produto.exception.ResourceNotFoundException
import br.com.produto.repository.AutorRepository
import br.com.produto.repository.LivroRepository
import br.com.produto.service.LivroService
import br.com.produto.repository.CategoriaRepository
import br.com.produto.request.livros.CreateLivroRequest
import br.com.produto.request.livros.UpdateLivroRequest
import br.com.produto.response.LivroResponse
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Service

@Service
class LivroServiceImpl(
    private val livroRepository: LivroRepository,
    private val categoriaRepository: CategoriaRepository,
    private val autorRepository: AutorRepository
) : LivroService {

    val converter: LivroConverter = Mappers.getMapper(LivroConverter::class.java)

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun findAll(): List<LivroResponse> {
        return livroRepository.findAll().map { converter.convertEntityToResponse(it) }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun save(createLivroRequest: CreateLivroRequest): LivroResponse {
        /*
        * Verifier se tem título
        */
        var titulo = livroRepository.findByTitulo(createLivroRequest.titulo)
        if (titulo.isPresent) {
            throw ResourceNotFoundException("Livro já existe título ${titulo.get().titulo}")
        }
        /*
         * Verificar categoria id
         */
        if (!categoriaRepository.existsById(createLivroRequest.id_categoria)) {
            throw ResourceNotFoundException("Categoria não encontro id ${createLivroRequest.id_categoria}")
        }
        /*
        * Verificar autor id
        */
        if (!autorRepository.existsById(createLivroRequest.id_autor)) {
            throw ResourceNotFoundException("Autor não encontro id ${createLivroRequest.id_autor}")
        }
        val categoria = categoriaRepository.findById(createLivroRequest.id_categoria)
        val autor = autorRepository.findById(createLivroRequest.id_autor)


        var livro = converter.convertRequestToEntity(createLivroRequest)
        livro.categoria = categoria.get()
        livro.autor = autor.get()
        livro = livroRepository.save(livro)

        return LivroResponse(
            livro.id!!,
            livro.titulo,
            livro.resumo,
            livro.isbn,
            livro.preco,
            livro.sumario,
            livro.pagina,
            livro.dataPublicacao,
            livro.categoria!!,
            livro.autor!!
        )
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun update(id: Long, updateLivroRequest: UpdateLivroRequest): UpdateLivroRequest {

        if (livroRepository.existsById(id)) {
            val livroId = livroRepository.findById(id)

            livroId.get().titulo = updateLivroRequest.titulo
            livroId.get().resumo = updateLivroRequest.resumo
            livroId.get().isbn = updateLivroRequest.isbn
            livroId.get().preco = updateLivroRequest.preco
            livroId.get().sumario = updateLivroRequest.sumario
            livroId.get().pagina = updateLivroRequest.pagina

            return converter.convertEntityToUpdate(livroRepository.save(livroId.get()))
        }
        throw ResourceNotFoundException("Categoria não encontro id $id")
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun findById(id: Long): LivroResponse {

        if (livroRepository.existsById(id)) {
            return converter.convertEntityToResponse(livroRepository.findById(id).get())
        }
        throw ResourceNotFoundException("Categoria não encontro id $id")

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun delete(id: Long) {
        if (livroRepository.existsById(id)) {
            livroRepository.deleteById(id)
            return
        }
        throw ResourceNotFoundException("Categoria não encontro id $id")
    }

}