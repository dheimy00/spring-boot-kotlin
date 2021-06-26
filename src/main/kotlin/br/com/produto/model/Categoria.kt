package br.com.produto.model

import javax.persistence.*


@Entity
@Table(name="Categoria")
data class Categoria (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    @Column(name = "nome",nullable = false)
    var nome: String



)


