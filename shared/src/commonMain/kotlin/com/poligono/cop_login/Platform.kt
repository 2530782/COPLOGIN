package com.poligono.cop_login

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform