package com.fetch_coding_project.view.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fetch_coding_project.model.FetchItem
import fetchcodingproject.composeapp.generated.resources.Res
import fetchcodingproject.composeapp.generated.resources.app_bar_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun ItemsPage() {
    MaterialTheme {
        val itemsViewModel = koinViewModel<ItemsViewModel>()
        val uiState by itemsViewModel.uiState.collectAsState()
        LaunchedEffect(itemsViewModel) {
            itemsViewModel.updateItems()
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(stringResource(Res.string.app_bar_title))
                    }
                )
            },
        ) {

            Column {
                // TODO add error state
                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator(
                            Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center)
                        )
                    }

                    uiState.isError -> {
                        // Basic error message, would obviously be expanded upon in production
                        Text(
                            text = "An error has occurred. Please try again.",
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center)
                        )
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
                        ) {
                            itemsIndexed(uiState.items) { index, item ->
                                if (index == 0) {
                                    ListIdHeader(item.listId)
                                }
                                if (index != 0 && item.listId != uiState.items[index - 1].listId) {
                                    Divider(
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        color = Color.Black,
                                        thickness = 8.dp
                                    )
                                    ListIdHeader(item.listId)
                                }
                                ItemCell(item)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListIdHeader(listId: Int) {
    Text(
        text = "List ID: $listId",
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun ItemCell(item: FetchItem) {
    Text(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        text = item.name ?: "No Item Name"
    )
}