package br.com.produto.request.livros

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import javax.validation.constraints.*

data class CreateLivroRequest (

    @field:NotBlank(message = "O campo título é obrigatório")
    @field:Size(min = 4)
    var titulo: String,

    @field:NotBlank(message = "O campo título é obrigatório")
    @field:Size(min = 4,max = 500,message = "O máximo é 500 caracteres")
    var resumo: String,

    @field:NotBlank(message = "O campo isbn é obrigatório")
    @field:Size(min = 4)
    var isbn: String,

    @field:NotNull(message = "O campo preço é obrigatório",)
    @field:DecimalMin(value = "20.0", inclusive = false,message ="O mínimo é 20 " )
    @field:Digits(integer=3, fraction=2)
    var preco: Double,

    @field:NotBlank(message = "O campo sumário é obrigatório")
    var sumario: String,

    @field:NotNull(message = "O campo página é obrigatório")
    @field:Min(value = 100, message = "O mínimo é de 100")
    var pagina: Int,

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd H:mm:ss")
    var dataPublicacao: LocalDateTime,

    var id_categoria: Long,

    var id_autor: Long,
)
{}