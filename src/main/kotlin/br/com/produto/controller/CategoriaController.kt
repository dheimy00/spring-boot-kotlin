package br.com.produto.controller

import br.com.produto.request.categorias.CreateCategoriaRequest
import br.com.produto.request.categorias.UpdateCategoriaResquest
import br.com.produto.response.CategoriaResponse
import br.com.produto.service.CategoriaService
import br.com.produto.utils.GeneralMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("v1/api/categorias")
class CategoriaController(private val categoriaService: CategoriaService) {


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(method = [RequestMethod.GET])
    fun findAll(): ResponseEntity<List<CategoriaResponse>> {
        val categorias = categoriaService.findAll()
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.ok(categorias)
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(method = [RequestMethod.POST])
    fun save(@Valid @RequestBody createCategoriaRequest: CreateCategoriaRequest): ResponseEntity<CategoriaResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.save(createCategoriaRequest))
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.PUT])
    fun update(
        @PathVariable("id") id: Long,
        @Valid @RequestBody updateCategoriaResquest: UpdateCategoriaResquest
    ): ResponseEntity<GeneralMessage> {
        categoriaService.update(id,updateCategoriaResquest)
        return ResponseEntity.status(HttpStatus.OK).body(GeneralMessage("Atualizo categoria com sucesso"))
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun findById(@PathVariable("id") id: Long): ResponseEntity<CategoriaResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.findById(id))
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    fun deleteById(@PathVariable("id") id: Long): ResponseEntity<GeneralMessage> {
        categoriaService.delete(id)
        return ResponseEntity.status(HttpStatus.OK).body(GeneralMessage("Deletando categoria com sucesso"))
    }


}