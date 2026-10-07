package com.upt.coplogin

import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val success: Boolean,
    val name: String? = null,
    val role: String? = null
)

@Serializable
data class UserRecord(
    val username: String,
    val password: String,
    val role: String,
    val name: String
)

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Post)
    }

    val usersJson = object {}.javaClass
        .getResource("/users.json")!!
        .readText()

    val users = Json.decodeFromString<List<UserRecord>>(usersJson)

    routing {
        get("/") {
            call.respondText("Servidor Logue funcionando")
        }

        post("/login") {
            val request = call.receive<LoginRequest>()

            val user = users.find {
                it.username == request.username &&
                        it.password == request.password
            }

            if (user != null) {
                call.respond(
                    LoginResponse(
                        success = true,
                        name = user.name,
                        role = user.role
                    )
                )
            } else {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    LoginResponse(success = false)
                )
            }
        }
    }
}