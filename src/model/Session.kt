package br.com.titoaesj.coreapi.model

data class Session(
    val id: Int,
    val email: String,
    val token: String
)