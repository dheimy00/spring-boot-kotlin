package br.com.produto.model

import javax.persistence.*

@Entity
@Table(name="Autor")
data class Autor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long?,

    @Column(name = "nome", nullable = false)
    var nome: String,

    @Column(name = "descricao",nullable = false)
    var descricao: String,

    @Column(name = "email", unique = true, nullable = false)
    var email: String,

) {

}