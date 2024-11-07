package com.fetch_coding_project

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MainUiState(val items: List<FetchItem>)

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState(emptyList()))
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val httpClient: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    fun updateItems() {
        viewModelScope.launch {
            val items = getItems()
                .filter { !it.name.isNullOrBlank() }
                .sortedWith(
                    compareBy(
                        { it.listId },
                        { it.name?.length }, // could sort in a different manner if desired
                        { it.name })
                )
            _uiState.update {
                it.copy(items = items)
            }
        }
    }

    override fun onCleared() {
        httpClient.close()
        super.onCleared()
    }

    private suspend fun getItems(): List<FetchItem> =
        httpClient
            .get("https://fetch-hiring.s3.amazonaws.com/hiring.json")
            .body<List<FetchItem>>()

}