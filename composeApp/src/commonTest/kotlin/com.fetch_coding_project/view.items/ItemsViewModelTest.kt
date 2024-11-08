package com.fetch_coding_project.view.items

import com.fetch_coding_project.model.FetchItem
import com.fetch_coding_project.repository.ApiResponse
import com.fetch_coding_project.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ItemsViewModelTest {

    private val fetchItem1 = FetchItem(
        id = 1,
        listId = 2,
        name = "123"
    )
    private val fetchItem2 = FetchItem(
        id = 2,
        listId = 2,
        name = ""
    )
    private val fetchItem3 = FetchItem(
        id = 3,
        listId = 1,
        name = "123"
    )
    private val dataRepositoryImpl = object : DataRepository {
        override suspend fun getItems(): ApiResponse<List<FetchItem>> {
            return ApiResponse.Success(listOf(
                fetchItem1,
                fetchItem2,
                fetchItem3,
            ))
        }
    }
    private val itemsViewModel = ItemsViewModel(dataRepositoryImpl)

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun shouldHandleUpdateItems() = runTest {
        itemsViewModel.updateItems()
        testDispatcher.scheduler.advanceUntilIdle()

        val valueAfter = itemsViewModel.uiState.value
        assertEquals(listOf(fetchItem3, fetchItem1), valueAfter.items)
    }
}