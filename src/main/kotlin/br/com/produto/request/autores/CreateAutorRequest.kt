package br.com.produto.request.autores

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class CreateAutorRequest(

    var id: Long,

    @field:NotBlank(message = "O campo nome é obrigatório")
    @field:Size(min = 4)
    var nome: String,

    @field:NotBlank(message = "O campo descricao é obrigatório")
    @field:Size(min = 4)
    var descricao: String,

    @field:NotBlank(message = "O campo email é obrigatório")
    @field:Email
    var email: String
) {
}