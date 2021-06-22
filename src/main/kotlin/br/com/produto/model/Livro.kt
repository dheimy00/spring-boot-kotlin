package br.com.produto.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*


@Entity
@Table(name="Livro")
data class Livro(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long?,

    @Column(name = "titulo", unique = true, nullable = false)
    var titulo: String,

    @Column(name = "resumo", unique = true, nullable = false)
    var resumo: String,

    @Column(name = "isbn", unique = true, nullable = false)
    var isbn: String,

    @Column(name = "preco", nullable = false)
    var preco: Double,

    @Column(name = "sumario",nullable = false)
    var sumario: String,

    @Column(name = "pagina", nullable = false)
    var pagina: Int,

    @Column(name = "dataPublicacao", nullable = false)
    var dataPublicacao: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    var categoria: Categoria?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    var autor: Autor?


)