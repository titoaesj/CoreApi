package dao

import br.com.titoaesj.coreapi.dao.SessionsTable
import br.com.titoaesj.coreapi.dao.UsersTable
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val dbUrl = appConfig.config("ktor.db").propertyOrNull("url")?.getString()
    private val dbUser = appConfig.config("ktor.db").propertyOrNull("user")?.getString()
    private val dbPassword = appConfig.config("ktor.db").propertyOrNull("password")?.getString()

    fun startDB() {
        Database.connect(hikari())

        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(UsersTable, SessionsTable)
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.jdbcUrl = dbUrl
        config.driverClassName = "org.postgresql.Driver"
        config.username = dbUser
        config.password = dbPassword
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }

}