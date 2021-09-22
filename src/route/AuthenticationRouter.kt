package route

import br.com.titoaesj.coreapi.model.NewUser
import br.com.titoaesj.coreapi.service.SessionService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.mindrot.jbcrypt.BCrypt
import service.JwtService
import service.UserService

data class LoginRegister(val email: String, val password: String)

fun Route.signin(service: UserService, jwt: JwtService) {
    post("/login") {
        val post = call.receive<LoginRegister>()
        val hasMatch = service.getUserByEmail(post.email)
        if (hasMatch == null || !BCrypt.checkpw(post.password, hasMatch.password)) {
            call.respond(HttpStatusCode.BadRequest, "Account does not exist.")
        } else call.respond(
            mapOf(
                "token" to jwt.createToken(hasMatch.email),
                "id" to hasMatch.id,
                "email" to hasMatch.email,
                "expireIn" to jwt.date
            )
        )
    }
}

fun Route.signout(session : SessionService) {
    post("/logout") {
        call.request.header("Authorization")?.replace("Bearer ", "")?.let { token ->
            try {
                val params = call.receive<NewUser>()
                session.addSession(token = token, email = params.email)
                call.respondText { "Usuário ${params.email} deslogado com sucesso!" }
            } catch (e: Exception) {
                call.respondText { e.message.toString() }
                call.respond(HttpStatusCode.InternalServerError)
            }
        } 
        call.respondText { "Token não encontrado" }
    }
}