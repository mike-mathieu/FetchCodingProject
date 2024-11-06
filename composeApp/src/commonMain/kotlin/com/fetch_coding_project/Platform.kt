package com.fetch_coding_project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform