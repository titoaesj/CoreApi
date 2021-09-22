package service

import br.com.titoaesj.coreapi.dao.UsersTable
import br.com.titoaesj.coreapi.model.NewUser
import dao.DatabaseFactory.dbQuery
import model.User
import org.jetbrains.exposed.sql.*
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.mindrot.jbcrypt.BCrypt

class UserService {

    suspend fun getAllUsers(): List<User> = dbQuery {
        UsersTable.selectAll().map { toUser(it) }
    }

    suspend fun getUserByEmail(email: String): User? = dbQuery {
        UsersTable.select {
            (UsersTable.email eq email)
        }.mapNotNull { toUser(it) }
            .singleOrNull()
    }

    private fun toUser(row: ResultRow): User =
        User(
            id = row[UsersTable.id],
            email = row[UsersTable.email],
            active = row[UsersTable.active],
            password = row[UsersTable.password],
            createdAt = row[UsersTable.createdAt].toString(),
            updatedAt = row[UsersTable.updateddAt].toString()
        )

    suspend fun createUser(newUser: NewUser) = dbQuery {
        UsersTable.insert {
            it[email] = newUser.email
            it[password] = BCrypt.hashpw(
                newUser.password, BCrypt.gensalt()
            )
            it[active] = false
            it[createdAt] = DateTime.now()
            it[updateddAt] = DateTime.now()
        }
    }
}