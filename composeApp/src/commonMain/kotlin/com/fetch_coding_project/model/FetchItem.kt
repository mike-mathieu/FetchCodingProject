package com.fetch_coding_project.model

import kotlinx.serialization.Serializable

@Serializable
data class FetchItem(
    val id: Int,
    val listId: Int,
    val name: String?
)
