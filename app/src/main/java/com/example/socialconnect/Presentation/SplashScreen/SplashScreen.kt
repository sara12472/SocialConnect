package com.example.socialconnect.Presentation.SplashScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.socialconnect.R
import androidx.navigation.NavController
import com.example.socialconnect.Navigation.Screen

@Composable
fun SplashScreen(navController: NavController,
                 viewModel: SplashViewModel = hiltViewModel()
){

    val state by viewModel.state.collectAsState()


    // Navigate when ViewModel says ready
    LaunchedEffect(state.isReadyToNavigate) {
        if (state.isReadyToNavigate) {
            navController.navigate(Screen.AuthScreen.route) {
                popUpTo(Screen.SplashScreen.route) { inclusive = true }
            }
        }
    }

    // Animation (UI only concern)
    val scale = remember { Animatable(0.7f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // start animation
        alpha.animateTo(1f, tween(600))
        scale.animateTo(1f, tween(900))

        // small delay for smooth feel
        kotlinx.coroutines.delay(1200)


    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(150.dp)
                .scale(scale.value)
                .graphicsLayer {
                    this.alpha = alpha.value
                }
        )
    }

}