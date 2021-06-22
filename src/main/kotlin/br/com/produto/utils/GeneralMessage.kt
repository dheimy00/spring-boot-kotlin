package br.com.produto.utils

import java.lang.RuntimeException

class GeneralMessage(message: String?) : RuntimeException(message) {
}