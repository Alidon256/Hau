package org.hau.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform