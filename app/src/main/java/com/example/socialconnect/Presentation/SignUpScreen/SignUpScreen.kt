package com.example.socialconnect.Presentation.SignUpScreen

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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.example.socialconnect.R
import androidx.compose.runtime.Composable
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
import com.example.socialconnect.Component.AppTextField
import com.example.socialconnect.Navigation.Screen
import com.example.socialconnect.ui.theme.ElectricBlue

@Composable
 fun SignUpScreen(
 navController: NavController,
 viewModel: SignUpViewModel = hiltViewModel()
 ){
 val state = viewModel.state.collectAsState().value

 BoxWithConstraints(
  modifier = Modifier.fillMaxSize()
 ) {

  val isTablet = maxWidth >= 600.dp

  val horizontalPadding = if (isTablet) 64.dp else 24.dp
  val formWidth = if (isTablet) 420.dp else maxWidth

  Column(
   modifier = Modifier
    .fillMaxSize()
    .padding(horizontalPadding)
    .navigationBarsPadding()
    .imePadding(),
   verticalArrangement = Arrangement.Center,
   horizontalAlignment = Alignment.CenterHorizontally
  ) {

   // 🔵 TITLE
   Text(
    text = "Create Account",
    style = MaterialTheme.typography.headlineMedium,
    color = MaterialTheme.colorScheme.onPrimary
   )

   Spacer(modifier = Modifier.height(4.dp))

   Text(
    text = "Join SocialConnect Today!",
    color = MaterialTheme.colorScheme.primary,
    style = MaterialTheme.typography.bodyMedium
   )

   Spacer(modifier = Modifier.height(20.dp))

   // 🔵 FORM CONTAINER (IMPORTANT FOR TABLETS)
   Column(
    modifier = Modifier.width(formWidth),
    verticalArrangement = Arrangement.Center
   ) {

    AppTextField(
     value = state.firstName,
     onValueChange = viewModel::onFirstNameChange,
     hint = "First Name",
     modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(12.dp))

    AppTextField(
     value = state.email,
     onValueChange = viewModel::onEmailChange,
     hint = "Email",
     modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(12.dp))

    AppTextField(
     value = state.password,
     onValueChange = viewModel::onPasswordChange,
     hint = "Password",
     isPassword = true,
     modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(12.dp))

    AppTextField(
     value = state.confirmPassword,
     onValueChange = viewModel::onConfirmPasswordChange,
     hint = "Confirm Password",
     isPassword = true,
     modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(22.dp))

    AppButton(
     text = "SignUp",
     onClick = viewModel::onSignUpClick,
     modifier = Modifier.fillMaxWidth(),
    )

    Spacer(modifier = Modifier.height(16.dp))

    Row(
     horizontalArrangement = Arrangement.Center,
     modifier = Modifier.fillMaxWidth()
    ) {
     Text(text="Already have an account? ",
      color = MaterialTheme.colorScheme.onPrimary,
      style = MaterialTheme.typography.labelSmall
      )

     Text(
      text = "Login",
      color = MaterialTheme.colorScheme.primary,
      style = MaterialTheme.typography.labelSmall,
      modifier = Modifier.clickable {
       navController.navigate(Screen.LoginScreen.route)
      }
     )
    }
   }
  }
 }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShowSignUpPreview(){
 val navController= rememberNavController()
 val viewModel: SignUpViewModel = hiltViewModel()
 SignUpScreen(navController, viewModel)
}