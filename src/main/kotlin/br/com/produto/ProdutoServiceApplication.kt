package br.com.produto

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProdutoServiceApplication

fun main(args: Array<String>) {
	runApplication<ProdutoServiceApplication>(*args)
}
