package com.fetch_coding_project.repository

import com.fetch_coding_project.model.FetchItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json

interface DataRepository {
    suspend fun getItems(): ApiResponse<List<FetchItem>>
}

class DataRepositoryImpl: DataRepository {
    private val httpClient: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    override suspend fun getItems(): ApiResponse<List<FetchItem>> =
        try {
            val response = httpClient
                .get("https://fetch-hiring.s3.amazonaws.com/hiring.json")
                .body<List<FetchItem>>()
            ApiResponse.Success(response)
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }
}