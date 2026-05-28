package com.example.socialconnect.Component



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.socialconnect.ui.theme.ElectricBlue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false
) {

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,

        modifier = modifier,

        label = {
            Text(text = hint)
        },


        shape = RoundedCornerShape(14.dp),

        singleLine = true,

        visualTransformation =
            if (isPassword && !passwordVisible)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,

        trailingIcon = {

            if (isPassword) {

                IconButton(
                    onClick = {
                        passwordVisible = !passwordVisible
                    }
                ) {

                    Icon(
                        imageVector =
                            if (passwordVisible)
                                Icons.Default.Visibility
                            else
                                Icons.Default.VisibilityOff,

                        contentDescription = "Password Toggle",

                        tint = ElectricBlue
                    )
                }
            }
        },

        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ElectricBlue,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            cursorColor = ElectricBlue
        )
    )
}

@Preview
@Composable
fun ShowTextField(){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column() {
        AppTextField(
            value = email,
            onValueChange = { email = it },
            hint = "Enter email"
        )
        Spacer(modifier = Modifier.height(20.dp))
        AppTextField(
            value = password,
            onValueChange = { password = it },
            hint = "Enter password",
            isPassword = true
        )
    }



}