package br.com.produto.controller

import br.com.produto.request.livros.CreateLivroRequest
import br.com.produto.request.livros.UpdateLivroRequest
import br.com.produto.response.LivroResponse
import br.com.produto.service.LivroService
import br.com.produto.utils.GeneralMessage
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/api/livros")
class LivroController(private val livroService: LivroService) {

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(method = [RequestMethod.GET])
    fun findAll(): ResponseEntity<List<LivroResponse>> {
        val livros = livroService.findAll()
        if (livros.isEmpty()) {
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.ok(livros)
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(method = [RequestMethod.POST],produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun save(@RequestBody @Valid createLivroRequest: CreateLivroRequest): ResponseEntity<LivroResponse> {

        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.save(createLivroRequest))
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.PUT])
    fun update(
        @PathVariable("id") id: Long,
        @Valid @RequestBody updateLivroRequest: UpdateLivroRequest): ResponseEntity<GeneralMessage> {
        livroService.update(id, updateLivroRequest)
        return ResponseEntity.status(HttpStatus.OK).body(GeneralMessage("Atualizo livro com sucesso"))
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun findById(@PathVariable("id") id: Long): ResponseEntity<LivroResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.findById(id))
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    fun deleteById(@PathVariable("id") id: Long): ResponseEntity<GeneralMessage> {
        livroService.delete(id)
        return ResponseEntity.status(HttpStatus.OK).body(GeneralMessage("Deletando livro com sucesso"))
    }


}