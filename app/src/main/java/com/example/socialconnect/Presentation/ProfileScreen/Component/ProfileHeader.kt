package com.example.socialconnect.Presentation.ProfileScreen.Component


import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.socialconnect.Data.dummyPosts

import com.example.socialconnect.R


@Composable
fun ProfileHeader(
    profileImage: Int,
    userName: String,
    bio: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 🔵 PROFILE IMAGE
        Image(
            painter = painterResource(id = profileImage),
            contentDescription = userName,

            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(5.dp))

        // 🔵 USER NAME
        Text(
            text = userName,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(5.dp))

        // 🔵 BIO
        Text(
            text = bio,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun ShowProfileHeader(){
    ProfileHeader(profileImage = dummyPosts[0].profileImage, userName = "sara","android developer")
}