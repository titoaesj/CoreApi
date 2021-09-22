package service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import dao.generatieUniqueId
import java.util.*

class JwtService(secret: String) {

    private val algorithm = Algorithm.HMAC256(secret)
    lateinit var date: Date
    val verifier: JWTVerifier = JWT.require(algorithm).build()

    fun createToken(username: String): String {
        date = Calendar.getInstance().apply {
            this.time = Date()
            this.roll(Calendar.MINUTE, 1)
        }.time

        return JWT.create()
            .withIssuer(username)
            .withExpiresAt(date)
            .withClaim("key", username)
            .withClaim("uniqueId", generatieUniqueId())
            .sign(algorithm)
    }

}


