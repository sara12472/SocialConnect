package com.example.socialconnect
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.socialconnect.Navigation.AppNavigation
import com.example.socialconnect.ui.theme.SocialConnectTheme
import dagger.hilt.android.AndroidEntryPoint
import android.Manifest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestNotificationPermission(this)
        setContent {
            SocialConnectTheme {
                AppNavigation()
            }
        }
    }
}
private fun requestNotificationPermission(activity: Activity) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1001
            )
        }
    }
}

