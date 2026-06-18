import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import com.example.socialconnect.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.socialconnect.Data.Model.AppNotification
import com.example.socialconnect.Presentation.Notification.NotificationViewModel
import java.util.concurrent.TimeUnit

@Composable
fun NotificationScreen(
    navController: NavController,
    viewModel: NotificationViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState().value



        Column(
            modifier = Modifier
                .fillMaxSize()

        ) {

            CustomAppBar(
                title = stringResource(R.string.Notification_text),
                onBackClick = {
                    navController.popBackStack()
                }
            )

            if (state.notifications.isEmpty()) {

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.NoNotification_text))
                }

            } else {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 4.dp)
                ) {

                    items(state.notifications) { notification ->
                        NotificationItem(notification)
                    }
                }
            }
        }
    }

@Composable
fun NotificationItem(notification: AppNotification) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp,
                top = 8.dp,
                bottom = 12.dp,
                end = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // PROFILE IMAGE
        AsyncImage(
            model = notification.senderProfile,
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        // TEXT
        Column {

            Text(
                text = notification.senderName,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "${getNotificationText(notification.type)} • ${getTimeAgo(notification.timestamp)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
fun getNotificationText(type: String): String {
    return when (type) {
        "like" -> "liked your post ❤️"
        "comment" -> "commented on your post 💬"
        "reply" -> "replied to your comment 🔁"
        else -> "interacted with your post"
    }
}
fun getTimeAgo(time: Long): String {

    val now = System.currentTimeMillis()
    val diff = now - time

    return when {
        diff < TimeUnit.MINUTES.toMillis(1) -> "just now"
        diff < TimeUnit.HOURS.toMillis(1) ->
            "${TimeUnit.MILLISECONDS.toMinutes(diff)}m ago"

        diff < TimeUnit.DAYS.toMillis(1) ->
            "${TimeUnit.MILLISECONDS.toHours(diff)}h ago"

        else ->
            "${TimeUnit.MILLISECONDS.toDays(diff)}d ago"
    }
}