package com.example.socialconnect.Data

import androidx.annotation.DrawableRes
import com.example.socialconnect.R

data class Post(val id: Int,

                val userName: String,

                val time: String,


                val profileImage: Int,


                val postImage: Int,

                val likes: Int,
                 val comments: Int,
    val bio: String

)



val dummyPosts = listOf(
    Post(
        id = 1,
        userName = "Sara Ali",
        time = "2 min ago",
        profileImage = R.drawable.profile,
        postImage = R.drawable.post1,
        likes = 120,
        comments = 10,
        bio = "Android Developer"
    ),

    Post(
        id = 2,
        userName = "Ahmed Khan",
        time = "10 min ago",
        profileImage = R.drawable.profile,
        postImage = R.drawable.post1,
        likes = 340,
        comments = 10,
        bio = "Android Developer"


    ),

    Post(
        id = 3,
        userName = "Ayesha Noor",
        time = "1 hour ago",
        profileImage = R.drawable.profile,
        postImage = R.drawable.post1,
        likes = 89,
        comments = 10,
        bio = "Android Developer"


    ),

    Post(
        id = 4,
        userName = "Usman Tariq",
        time = "3 hours ago",
        profileImage = R.drawable.profile,
        postImage = R.drawable.post1,
        likes = 560,
        comments = 10,
        bio = "Android Developer"


    )
)