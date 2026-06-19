package com.example.socialconnect.Presentation.SearchScreen

import com.example.socialconnect.Data.Model.User

data class SearchState(
    val query: String = "",
    val users: List<User> = emptyList()
)