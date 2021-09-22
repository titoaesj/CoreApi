package br.com.titoaesj.coreapi

import br.com.titoaesj.coreapi.service.SessionService
import dao.DatabaseFactory
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.locations.*
import io.ktor.routing.*
import route.createUser
import route.getAllUsers
import route.signin
import route.signout
import service.JwtService
import service.UserService


fun Application.main() {

    val simpleJwt = JwtService("samplesecretekey")
    val userService = UserService()
    val sessionService = SessionService()

    DatabaseFactory.startDB()

    install(CallLogging)

    install(Locations)

    install(ContentNegotiation) { gson { setPrettyPrinting() } }

    install(Authentication) {
        jwt {
            verifier(simpleJwt.verifier)
            validate {
                UserIdPrincipal(it.payload.getClaim("key").asString())
            }
        }
    }

    routing {
        signin(service = userService, jwt = simpleJwt)
        getAllUsers(service = userService, session = sessionService)
        createUser(service = userService)
        signout(session = sessionService)
    }
}