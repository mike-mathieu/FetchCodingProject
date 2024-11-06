package com.fetch_coding_project

import kotlinx.serialization.Serializable

@Serializable
data class FetchItem(
    val id: Int,
    val listId: Int,
    val name: String?
)
