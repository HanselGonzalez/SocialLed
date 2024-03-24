package com.sign.led.ui.signFullView

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignFullViewModel @Inject constructor() : ViewModel() {

    private var _state = MutableStateFlow<SignFullState>(SignFullState.Loading)
    val state: StateFlow<SignFullState> = _state


}
