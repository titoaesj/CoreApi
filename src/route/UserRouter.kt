package route

import br.com.titoaesj.coreapi.model.NewUser
import br.com.titoaesj.coreapi.service.SessionService
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.request.*
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import service.UserService

fun Route.getAllUsers(service : UserService, session : SessionService) {
        authenticate {
            get("/user") {

                call.request.header("Authorization")?.replace("Bearer ", "")?.let { token ->
                    if (session.checkExpire(token) == null) {
                        if (session.isTokenActive(token) == null) {
                            call.respond(service.getAllUsers())
                        } else call.respondText { "This Session is expired. Sign In Again" }
                    } else call.respondText { session.checkExpire(token).toString() }
                }

                call.respondText { "Token invalido" }

            }
        }
}

fun Route.createUser(service: UserService) {
    post("/user") {
        val params = call.receive<NewUser>()
        try {
            service.createUser(NewUser(params.email, params.password))
            service.getUserByEmail(params.email)?.let {
                call.respond(HttpStatusCode.OK, it)
            }
            throw IllegalStateException("usuário não encontrado")
        } catch (e: Exception) {
            call.respondText { e.message.toString() }
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}