package com.example.socialconnect.Presentation.ForgotPassword

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.socialconnect.Component.AppButton
import com.example.socialconnect.Component.AppTextField
import com.example.socialconnect.ui.theme.ElectricBlue

@Composable
fun ForgetPasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewmodel = hiltViewModel()
){
    val state = viewModel.state.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .navigationBarsPadding()
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 🔵 TITLE
        Text(
            text = "Forgot Password",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Enter your email to reset password",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 📧 EMAIL FIELD
        AppTextField(
            value = state.email,
            onValueChange = viewModel::onEmailChange,
            hint = "Email",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔵 ERROR MESSAGE
        state.errorMessage?.let {
            Text(
                text = it,
                color = Color.Red
            )
        }

        // 🔵 SUCCESS MESSAGE
        state.successMessage?.let {
            Text(
                text = it,
                color = Color.Green
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔵 BUTTON
        AppButton(
            text = if (state.isLoading) "Sending..." else "Send Reset Link",
            onClick = viewModel::onSendClick,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔙 BACK TO LOGIN
        Text(
            text = "Back to Login",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                navController.popBackStack()
            }
        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShowForgetPasswordPreview(){
    val navController= rememberNavController()
    val viewModel: ForgotPasswordViewmodel=hiltViewModel()
    ForgetPasswordScreen(navController,viewModel)

}