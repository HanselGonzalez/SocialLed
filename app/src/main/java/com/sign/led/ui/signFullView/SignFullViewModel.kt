package com.sign.led.ui.signFullView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sign.led.domain.UseCase.GetSignByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignFullViewModel @Inject constructor(private val getSignByIdUseCase: GetSignByIdUseCase) : ViewModel() {

    private var _state = MutableStateFlow<SignFullState>(SignFullState.Loading)
    val state: StateFlow<SignFullState> = _state


    fun getSignById(idItem:Long) {

        viewModelScope.launch {

            _state.value = SignFullState.Loading

            try {

                val response = withContext(Dispatchers.IO){getSignByIdUseCase(idItem)}

                _state.value = SignFullState.Success(response!!)

            }catch (e:Exception){
                _state.value = SignFullState.Error("Hubo un error")

            }


        }
    }

}
