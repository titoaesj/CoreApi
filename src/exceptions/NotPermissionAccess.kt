package br.com.titoaesj.coreapi.exceptions

import io.ktor.auth.*

class NotPermissionAccess(message : String) : AuthenticationFailedCause.Error(message) {
}