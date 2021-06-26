package br.com.produto.request.categorias

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class CreateCategoriaRequest(
    val id: Long,
    @field:NotBlank(message = "O campo nome é obrigatório")
    @field:Size(min = 4)
    val nome: String
) {
}