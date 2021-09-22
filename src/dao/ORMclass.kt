package br.com.titoaesj.coreapi.dao

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object SessionsTable : Table() {
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val email: Column<String> = varchar("email", 100)
    val token: Column<String> = text("token")

}

object UsersTable : Table() {
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val email: Column<String> = varchar("email", 100).uniqueIndex()
    val password: Column<String> = text("password")
    val active: Column<Boolean> = bool("active")
    val createdAt: Column<DateTime> = datetime("createdAt")
    val updateddAt: Column<DateTime> = datetime("updateddAt")
}