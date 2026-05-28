package com.example.socialconnect.Presentation.AuthScreen

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.example.socialconnect.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.socialconnect.Component.AppButton
import com.example.socialconnect.Navigation.Screen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

@Composable
fun AuthScreen(navController: NavController,
    viewModel: AuthViewModel= hiltViewModel()
){
    val state = viewModel.state.collectAsState().value

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode == Activity.RESULT_OK) {

            try {

                val task = GoogleSignIn
                    .getSignedInAccountFromIntent(result.data)

                val account =
                    task.getResult(ApiException::class.java)

                val idToken = account.idToken

                if (idToken != null) {
                    viewModel.handleGoogleSignIn(idToken)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    LaunchedEffect(Unit) {

        viewModel.googleSignInIntent.collect { intent ->

            if (intent != null) {
                launcher.launch(intent)
            }
        }
    }
    LaunchedEffect(state.success) {

        if (state.success) {

            navController.navigate(Screen.HomeScreen.route) {

                popUpTo(Screen.AuthScreen.route) {
                    inclusive = true
                }
            }
        }
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {

        val screenHeight = maxHeight
        val logoSize = screenHeight * 0.15f
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .navigationBarsPadding()
                .imePadding(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {


                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(logoSize)
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AppButton(
                    text = "Continue with Google",
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    onClick = { viewModel.signInWithGoogle() }
                )

                Spacer(modifier = Modifier.height(12.dp))

                AppButton(
                    text = "SignUp",
                    onClick = {
                        navController.navigate(Screen.SignUpScreen.route)
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text="Already have an account? ",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelSmall
                    )

                    Text(
                        text = " Login",
                        color = MaterialTheme.colorScheme.primary,
                        style= MaterialTheme.typography.labelSmall,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.LoginScreen.route)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ShowAuthPreview(){
    val navController= rememberNavController()
    val viewMode :AuthViewModel=hiltViewModel()
    AuthScreen(navController,viewMode)
}