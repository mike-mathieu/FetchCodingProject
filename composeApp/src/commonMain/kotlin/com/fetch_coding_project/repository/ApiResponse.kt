package com.fetch_coding_project.repository


// This could be expanded upon, but for simplicity it just represents a success and failure from an API request
sealed class ApiResponse<out T> {

    data class Success<T>(val body: T) : ApiResponse<T>()

    data class Error(val exception: Exception) : ApiResponse<Nothing>()
}
