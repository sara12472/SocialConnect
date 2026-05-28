package com.example.socialconnect.Presentation.HomeScreen.Component


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.socialconnect.Data.Model.Story

@Composable
fun StoryRow(
    stories: List<Story>
) {

    LazyRow(
        modifier = Modifier.fillMaxWidth(),

        horizontalArrangement = Arrangement.spacedBy(12.dp),

        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {

        items(stories) { story ->

            StoryItem(
                story = story
            )
        }
    }
}