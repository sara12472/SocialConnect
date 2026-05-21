package com.example.socialconnect.Presentation.ProfileScreen.Component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProfileStatsSection(
    posts: Int,
    followers: Int,
    following: Int
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        StatItem(
            title = "Followers",
            number = followers
        )

        StatItem(
            title = "Following",
            number = following
        )

        StatItem(
            title = "Posts",
            number = posts
        )
    }
}

@Composable
fun StatItem(
    title: String,
    number: Int
) {

    Column(
        horizontalAlignment = Alignment.Start
    ) {



        // NUMBER
        Text(
            text = number.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        // TITLE
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,

            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShowProfileStats(){
    ProfileStatsSection(posts = 3, followers = 4, following = 6)
}