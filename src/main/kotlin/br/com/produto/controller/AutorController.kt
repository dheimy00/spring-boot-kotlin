package br.com.produto.controller

import br.com.produto.request.autores.CreateAutorRequest
import br.com.produto.request.autores.UpdateAutorRequest
import br.com.produto.response.AutorResponse
import br.com.produto.service.AutorService
import br.com.produto.utils.GeneralMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("v1/api/autores")
class AutorController(private val autorService: AutorService) {

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(method = [RequestMethod.GET])
    fun findAll(): ResponseEntity<List<AutorResponse>> {
        val autores = autorService.findAll()
        if (autores.isEmpty()) {
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.ok(autores)
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(method = [RequestMethod.POST])
    fun save(@Valid @RequestBody createAutorRequest: CreateAutorRequest): ResponseEntity<AutorResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(autorService.save(createAutorRequest))
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.PUT])
    fun update(@PathVariable("id") id: Long,@Valid @RequestBody updateAutorRequest: UpdateAutorRequest ): ResponseEntity<GeneralMessage> {
        autorService.update(id, updateAutorRequest)
        return ResponseEntity.status(HttpStatus.OK).body(GeneralMessage("Atualizo autor com sucesso"))
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun findById(@PathVariable("id") id: Long): ResponseEntity<AutorResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(autorService.findById(id))
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    fun deleteById(@PathVariable("id") id: Long): ResponseEntity<GeneralMessage> {
        autorService.delete(id)
        return ResponseEntity.status(HttpStatus.OK).body(GeneralMessage("Deletando autor com sucesso"))
    }

}