package com.example.socialconnect.Presentation.SignUpScreen

import android.R.attr.text
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import com.example.socialconnect.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
 LaunchedEffect(state.success) {
  if (state.success) {
   navController.navigate(Screen.LoginScreen.route) {
    popUpTo(Screen.SignUpScreen.route) {
     inclusive = true
    }
   }
  }
 }
 Scaffold(
  modifier = Modifier.fillMaxSize()
 ) { paddingValues ->

  Column(
   modifier = Modifier
    .fillMaxSize()
    .padding(paddingValues)
    .padding(horizontal = 16.dp)
  ) {

   //  TOP BAR
   Row(
    modifier = Modifier
     .fillMaxWidth()
     .padding(top = 12.dp),
    verticalAlignment = Alignment.CenterVertically
   ) {

    Icon(
     imageVector = Icons.Default.ArrowBack,
     contentDescription = "Back",
     modifier = Modifier.clickable {
      if (state.step > 1) {
       viewModel.previousStep()
      } else {
       navController.popBackStack()
      }
     }
    )

    Spacer(modifier = Modifier.weight(1f))

    Text(
     text = stringResource(R.string.create_account),
     style = MaterialTheme.typography.titleLarge
    )

    Spacer(modifier = Modifier.weight(1f))
   }

   // STEP INDICATOR
   Text(
    text = "Step ${state.step} of 4",
    modifier = Modifier
     .align(Alignment.CenterHorizontally)
     .padding(top = 8.dp),
    color = MaterialTheme.colorScheme.primary
   )

   Spacer(modifier = Modifier.height(20.dp))

   //  CENTER CONTENT
   Box(
    modifier = Modifier
     .fillMaxWidth()
     .weight(1f),
    contentAlignment = Alignment.Center
   ) {

    Column(
     modifier = Modifier.fillMaxWidth(),
     horizontalAlignment = Alignment.CenterHorizontally
    ) {

     when (state.step) {

      1 -> {
       AppTextField(
        value = state.name,
        onValueChange = viewModel::onNameChange,
        hint = "Enter your name",
        modifier = Modifier.fillMaxWidth()
       )
      }

      2 -> {
       AppTextField(
        value = state.userName,
        onValueChange = viewModel::onUserNameChange,
        hint = "Enter username",
        modifier = Modifier.fillMaxWidth()
       )
      }

      3 -> {
       AppTextField(
        value = state.email,
        onValueChange = viewModel::onEmailChange,
        hint = "Enter email",
        modifier = Modifier.fillMaxWidth()
       )
      }

      4 -> {

       Column {

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
       }
      }
     }

   // ERROR
     state.error?.let {
      Spacer(modifier = Modifier.height(8.dp))

      Text(
       text = it,
       color = MaterialTheme.colorScheme.error,
       modifier = Modifier
        .fillMaxWidth()
        .padding(start = 4.dp)
      )
     }

     // LOADING BELOW ERROR
     if (state.isLoading) {

      Spacer(modifier = Modifier.height(12.dp))

      CircularProgressIndicator()
     }
    }
   }

   AppButton(
    onClick = {
     when (state.step) {

      1 -> {
       if (state.name.isBlank()) {
        viewModel.setError("Name is required")
       } else {
        viewModel.clearError()
        viewModel.nextStep()
       }
      }

      2 -> {
       if (state.userName.isBlank()) {
        viewModel.setError("Username is required")
       } else {
        viewModel.clearError()
        viewModel.nextStep()
       }
      }

      3 -> {
       if (state.email.isBlank()) {
        viewModel.setError("Email is required")
       } else {
        viewModel.clearError()
        viewModel.nextStep()
       }
      }

      4 -> {
       viewModel.signup()
      }
     }
    },
    enabled = !state.isLoading,
    modifier = Modifier
     .fillMaxWidth()
     .padding(bottom = 20.dp),
    text = if (state.step == 4)
     stringResource(R.string.create_account)
    else
     stringResource(R.string.continue_text)

   )



   
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