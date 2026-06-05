package com.example.socialconnect.Presentation.HomeScreen


import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.example.socialconnect.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.socialconnect.Component.AppTextField
import com.example.socialconnect.Data.Model.Comment
import com.example.socialconnect.Data.Model.CommentReply
import com.example.socialconnect.Data.Model.dummyStories
import com.example.socialconnect.Navigation.Screen
import com.example.socialconnect.Presentation.HomeScreen.Component.BottomBar
import com.example.socialconnect.Presentation.HomeScreen.Component.HomeTopBar
import com.example.socialconnect.Presentation.HomeScreen.Component.PostCard
import com.example.socialconnect.Presentation.HomeScreen.Component.ProfileFloatingButton
import com.example.socialconnect.Presentation.HomeScreen.Component.StoryRow

@Composable
fun HomeScreen( navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),

) {

    val state = viewModel.state.collectAsState().value


    val context = LocalContext.current
    val listState = rememberLazyListState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->

        uri?.let {

            val mimeType = context.contentResolver.getType(it)

            val type =
                if (mimeType?.startsWith("video") == true) "video"
                else "image"

            navController.navigate(
                Screen.CreatePostScreen.route +
                        "?mediaUri=${Uri.encode(it.toString())}" +
                        "&mediaType=$type"
            )
        }
    }
    LaunchedEffect(Unit) {
        viewModel.refreshUser()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Scaffold(
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),


                ) {


                item {
                    HomeTopBar(
                        onAddClick = { launcher.launch("*/*") },
                        onHeartClick = {
                            navController.navigate(Screen.NotificationScreen.route)
                        }
                    )
                }

                // Stories
               /* item {
                    StoryRow(stories = dummyStories)
                }*/

                // Posts
                itemsIndexed(state.posts) { index, post ->

                    val layoutInfo = listState.layoutInfo

                    val centerIndex =
                        listState.layoutInfo.visibleItemsInfo
                            .minByOrNull { item ->
                                kotlin.math.abs(item.offset)
                            }
                            ?.index

                    val isVisible = centerIndex == index + 2

                    PostCard(
                        post = post,
                        currentUserId = state.currentUserId,
                        isVisible = isVisible,
                        savedPostIds = state.savedPostIds,
                        onUserClick = {
                            navController.navigate(Screen.ProfileScreen.createRoute(post.userId))
                        },
                        onSaveClick = {viewModel.onSaveClick(it)},
                        onLikeClick = { viewModel.onLikeClick(it) },
                        onCommentClick = { viewModel.openComments(it.postId) },
                        onShareClick = { sharedPost ->

                            val shareText = buildString {

                                append(sharedPost.userName)

                                if (sharedPost.caption.isNotBlank()) {
                                    append("\n\n")
                                    append(sharedPost.caption)
                                }

                                if (sharedPost.mediaUrl.isNotBlank()) {
                                    append("\n\n")
                                    append(sharedPost.mediaUrl)
                                }
                            }

                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, shareText)
                            }

                            context.startActivity(
                                Intent.createChooser(intent, "Share Post")
                            )
                        }
                    )
                }

            }

        }

        // FLOATING BOTTOM BAR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 25.dp, vertical = 12.dp),

            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row() {
                BottomBar(
                    selectedIndex = state.selectedTab,
                    onItemSelected = { index ->

                        viewModel.onTabSelected(index)

                        when(index) {

                            0 -> {
                                // Home
                            }

                            1 -> {
                                // Chat Screen
                            }

                            2 -> {
                                // Reels/Video Screen
                            }

                            3 -> {
                                navController.navigate(Screen.SettingScreen.route)
                            }
                        }
                    },
                    modifier = Modifier.weight(1f, fill = false)


                )
                Spacer(modifier = Modifier.width(5.dp))

                Row() {
                    ProfileFloatingButton(
                        image = state.profileImage,
                        onClick = {
                            navController.navigate(
                                Screen.ProfileScreen.createRoute(
                                    state.currentUserId
                                )
                            )
                        }
                    )
                }


            }

            if (state.showCommentsSheet) {

                CommentBottomSheet(
                    comments = state.comments,
                    commentText = state.commentText,
                    currentUserProfile = state.profileImage,
                    onTextChange = viewModel::onCommentTextChange,
                    onSend = { viewModel.sendComment() },
                    onDismiss = { viewModel.closeComments() },
                    onReplyClick = { viewModel.startReply(it) },
                    replyingToCommentId = state.replyingToCommentId,
                    replyingToUserName = state.replyingToUserName,
                    repliesMap = state.repliesMap,   // 👈 ADD THIS
                    onClearReply = { viewModel.clearReply() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentBottomSheet(
    comments: List<Comment>,
    commentText: String,
    onTextChange: (String) -> Unit,
    onSend: () -> Unit,
    onDismiss: () -> Unit,
    currentUserProfile: String,
    onReplyClick: (Comment) -> Unit,
    replyingToCommentId: String?,
    replyingToUserName: String?,
    repliesMap: Map<String, List<CommentReply>>,
    onClearReply: () -> Unit
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.65f)
        ) {

            // ================= TITLE =================
            Text(
                text = "Comments",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))


            if (comments.isEmpty()) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No comments yet",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

            } else {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 12.dp)
                ) {

                    comments.forEach { comment ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {

                            AsyncImage(
                                model = comment.userProfile,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(modifier = Modifier.weight(1f)) {

                                Text(
                                    text = comment.userName,
                                    style = MaterialTheme.typography.labelSmall
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = comment.text,
                                    style = MaterialTheme.typography.labelSmall
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "Reply",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.clickable {
                                        onReplyClick(comment)
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        repliesMap[comment.commentId]?.forEach { reply ->

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 50.dp, top = 4.dp, bottom = 4.dp)
                            ) {

                                AsyncImage(
                                    model = reply.userProfile,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Column {

                                    Text(
                                        text = reply.userName,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )

                                    Text(
                                        text = reply.text,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }

                        if (comment.commentId == replyingToCommentId) {
                            Text(
                                text = "Replying to @${replyingToUserName}",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(start = 50.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                AsyncImage(
                    model = currentUserProfile,
                    contentDescription = null,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(8.dp))

                AppTextField(
                    value = commentText,
                    onValueChange = onTextChange,
                    hint = if (replyingToCommentId != null)
                        "Write a reply..."
                    else
                        "Write a comment...",
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = onSend) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send"
                    )
                }
            }
        }
    }
}
@Preview()
@Composable
fun ShowHomeScreen(){
    val viewModel: HomeViewModel = hiltViewModel()
    val navController= rememberNavController()
    HomeScreen(navController,viewModel)

}

