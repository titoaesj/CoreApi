package br.com.titoaesj.coreapi.exceptions

import java.lang.RuntimeException

class NotFoundException(message: String) : RuntimeException(message)