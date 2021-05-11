package com.hoangson.xavier.presentation.view.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hoangson.xavier.core.bases.BaseFragment
import com.hoangson.xavier.core.models.Command
import com.hoangson.xavier.core.models.Result
import com.hoangson.xavier.core.util.navigateSafely
import com.hoangson.xavier.presentation.compose.layout.*
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    @Composable
    override fun setContent() {
        LoginView(
            onLoginComplete = {
                navController.navigateSafely(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            }
        )

    }

    @Composable
    fun LoginView(
        onLoginComplete: () -> Unit
    ) {
        val stateLoading = remember { mutableStateOf(false) }
        val viewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        Box(Modifier.fillMaxSize()) {
            LoadingContent(stateLoading.value){}
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    GradientText(name = "Xavier", 110F)
                    columnSpacer(value = 40)
                    H6(text = "Login to Xavier")
                    columnSpacer(value = 20)
                    CenteredCaption(
                        text = "By Logging in to Xavier, you will be able to access your events.",
                        Modifier.padding(20.dp, 0.dp)
                    )
                    columnSpacer(value = 30)
                }
                val userNameErrorState = viewModel.userNameErrorLiveData.observeAsState()
                val passwordErrorState = viewModel.passwordErrorLiveData.observeAsState()
                val username = remember { mutableStateOf(TextFieldValue("hoangson@gmail.com")) }

                OutlinedTextField(
                    value = username.value,
                    onValueChange = { text ->
                        username.value = text
                    },
                    label = { H6("Username") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = userNameErrorState.value != null,
                    colors = TextFieldDefaults.outlinedTextFieldColors(MaterialTheme.colors.error)
                )
                Text(
                    text = userNameErrorState.value ?: "",
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                    modifier = Modifier.height(userNameErrorState.value.let { 20.dp } ?: 0.dp),
                    color = MaterialTheme.colors.error)
                columnSpacer(value = 16)

                val password = remember { mutableStateOf(TextFieldValue("123456")) }
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { text -> password.value = text },
                    label = { H6("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    isError = passwordErrorState.value != null,
                    colors = TextFieldDefaults.outlinedTextFieldColors()
                )
                Text(
                    text = passwordErrorState.value ?: "",
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                    modifier = Modifier.height(passwordErrorState.value?.let { 20.dp } ?: 0.dp),
                    color = MaterialTheme.colors.error)
                columnSpacer(value = 30)

                CardButton(text = "Login", onClick = {
                    stateLoading.value = true
                    viewModel.login(username = username.value.text, password = password.value.text)
                })
            }

        }
        viewModel.loginResultLiveData.observe(viewLifecycleOwner) {
            when(it){
                is Command.Loading -> {
                    stateLoading.value = true
                    Timber.d("HSLogin Loading")
                }
                is Command.NoLoading -> {
                    stateLoading.value = false
                    Timber.d("HSLogin No loading")
                }
                is Command.Success -> {
                    //onLoginComplete()
                }
                is Command.Error -> {
                    Timber.d("HSLogin failded")
                }
            }
        }
    }
}