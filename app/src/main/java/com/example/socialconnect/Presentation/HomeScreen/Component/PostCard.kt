package com.example.socialconnect.Presentation.HomeScreen.Component


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import com.example.socialconnect.R
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.socialconnect.Data.Post


@Composable
fun PostCard(
    post: Post
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )

    ) {


        Column {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = post.profileImage),
                    contentDescription = post.userName,

                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.size(10.dp))

                Column {

                    Text(
                        text = post.userName,
                        style = MaterialTheme.typography.titleSmall
                    )

                    Text(
                        text = post.time,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            // 🔵 POST IMAGE
            Image(
                painter = painterResource(id = post.postImage),
                contentDescription = "Post Image",

                modifier = Modifier.fillMaxWidth()
            )

            // 🔵 ACTIONS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),

                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row {

                    IconButton(onClick = {}) {

                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "Like"
                        )
                    }
                    // 🔵 LIKES
                    Text(
                        text = "${post.likes} ",

                        modifier = Modifier.padding(

                            vertical = 14.dp
                            ),

                        style = MaterialTheme.typography.bodyMedium
                    )

                    IconButton(onClick = {}) {

                        Icon(
                            imageVector = Icons.Outlined.ChatBubbleOutline,
                            contentDescription = "Comment"
                        )
                    }
                    Text(
                        text = "${post.comments} ",

                        modifier = Modifier.padding(

                            vertical = 14.dp
                            ),

                        style = MaterialTheme.typography.bodyMedium
                    )

                    IconButton(onClick = {}) {

                        Icon(
                            imageVector = Icons.Outlined.Send,
                            contentDescription = "Share"
                        )
                    }
                }

                IconButton(onClick = {}) {

                    Icon(
                        imageVector = Icons.Outlined.BookmarkBorder,
                        contentDescription = "Save"
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Preview
@Composable
fun ShowPostCard(){
    PostCard(post = Post(
        id = 1,
        userName = "Sara",
        time = "2 min ago",
        profileImage = R.drawable.profile,
        postImage = R.drawable.post1,
        likes = 120,
        comments = 10,
       bio = "android developer"
    ))
}