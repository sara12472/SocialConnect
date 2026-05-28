package com.example.socialconnect.Data.Model
import com.example.socialconnect.R

data class Story(

    val id: Int,

    val userName: String,


    val image: Int
)
val dummyStories = listOf(
    Story(
        id = 1,
        userName = "Sara",
        image = R.drawable.profile
    ),

    Story(
        id = 2,
        userName = "Ahmed",
        image = R.drawable.profile
    ),

    Story(
        id = 3,
        userName = "Ayesha",
        image = R.drawable.profile
    ),

    Story(
        id = 4,
        userName = "Usman",
        image = R.drawable.profile
    ),

    Story(
        id = 5,
        userName = "Ali",
        image = R.drawable.profile
    )
)