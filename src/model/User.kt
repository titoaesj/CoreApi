package model

data class User(
    val id: Int,
    val email: String,
    val active: Boolean,
    val password: String,
    val createdAt: String,
    val updatedAt: String
)

