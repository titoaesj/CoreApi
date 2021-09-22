import br.com.titoaesj.coreapi.main
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import junit.framework.TestCase
import org.junit.Test
import kotlin.test.assertEquals

class AppKtTest {

    @Test
    fun getAllUserTest() = withTestApplication(Application::main) {
        with(handleRequest(HttpMethod.Get, "/user"){
//            addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
//            setBody(listOf("username" to "JetBrains", "email" to "example@jetbrains.com", "password" to "foobar", "confirmation" to "foobar").formUrlEncode())
        }) {
            assertEquals("The 'JetBrains' account is created", response.content)
        }

    }

    @Test
    fun createUserTest() {
        assertEquals(1,1)
    }

    @Test
    fun signinTest() {
        assertEquals(1,1)
    }

    @Test
    fun signoutTest() {
        assertEquals(1,1)
    }

}