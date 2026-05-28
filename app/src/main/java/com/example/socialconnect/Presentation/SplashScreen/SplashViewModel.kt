package com.example.socialconnect.Presentation.SplashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialconnect.Presentation.SplashScreen.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel  @Inject constructor() :  ViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state

    init {
        startSplash()
    }

    private fun startSplash() {
        viewModelScope.launch {
            delay(2000)
            _state.value = SplashState(isReadyToNavigate = true)
        }
    }
}