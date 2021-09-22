package br.com.titoaesj.coreapi.service

import br.com.titoaesj.coreapi.dao.SessionsTable
import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTDecodeException
import dao.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.util.*

class SessionService {

    fun checkExpire(token: String): String? {
        return try {
            val decodedJWT = JWT.decode(token)
            if (Date() > decodedJWT.expiresAt) "Please Sign In" else null
        } catch (e: JWTDecodeException) {
            "Token Failure"
        }
    }

    suspend fun isTokenActive(token: String): String? = dbQuery {
        SessionsTable.select {
            (SessionsTable.token eq token)
        }.map { row -> row[SessionsTable.token] }.singleOrNull()
    }

    suspend fun addSession(token: String, email: String) = dbQuery {
        SessionsTable.insert {
            it[SessionsTable.email] = email
            it[SessionsTable.token] = token
        }
    }

}