package com.poligono.cop_login

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiService {

    // 10.0.2.2 es la forma en que el emulador de Android ve el localhost de tu PC
    private const val BASE_URL = "http://10.0.2.2:8080"

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    // Devuelve null si las credenciales son incorrectas (401).
    // Si no hay conexión con el servidor, lanza una excepción.
    suspend fun login(
        username: String,
        password: String
    ): LoginResponse? {
        val response = client.post("$BASE_URL/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(username, password))
        }

        return if (response.status == HttpStatusCode.OK) {
            response.body<LoginResponse>()
        } else {
            null
        }
    }
}