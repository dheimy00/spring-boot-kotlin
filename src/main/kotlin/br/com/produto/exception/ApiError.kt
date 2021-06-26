package br.com.produto.exception

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ApiError(
    var title:String,
    var status: HttpStatus,
    var clientMessage: String,
    var errors: List<String>,
    var developerMessage: String? = null
){
    constructor(title: String,status: HttpStatus, clientMessage: String, error: String, developerMessage: String? = null) :
            this(title,status, clientMessage, arrayListOf<String>(error), developerMessage)
}