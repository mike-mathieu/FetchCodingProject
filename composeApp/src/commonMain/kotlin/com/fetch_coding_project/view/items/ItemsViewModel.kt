package com.fetch_coding_project.view.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fetch_coding_project.model.FetchItem
import com.fetch_coding_project.repository.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ItemsUiState(val items: List<FetchItem>, val isLoading: Boolean)

class ItemsViewModel(private val dataRepository: DataRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ItemsUiState(items = emptyList(), isLoading = false))
    val uiState: StateFlow<ItemsUiState> = _uiState.asStateFlow()

    // Adding unit tests here would be pretty straightforward
    fun updateItems() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            val items = dataRepository.getItems()
                .filter { !it.name.isNullOrBlank() }
                .sortedWith(
                    compareBy(
                        { it.listId },
                        { it.name?.length }, // could sort in a different manner if desired (e.g. by ID)
                        { it.name })
                )
            _uiState.update {
                it.copy(items = items, isLoading = false)
            }
        }
    }
}