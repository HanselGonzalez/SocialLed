package com.sign.led.ui.signFullView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sign.led.data.providers.SignsProvider
import com.sign.led.domain.UseCase.GetSignByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignFullViewModel @Inject constructor(private val getSignByIdUseCase: GetSignByIdUseCase, private val getSignProviderById: SignsProvider) : ViewModel() {

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


    fun getSignProviderById(idItem:Long) {

        viewModelScope.launch {

            _state.value = SignFullState.Loading

            try {

                val response = withContext(Dispatchers.IO){getSignProviderById.getSignProviderById(idItem)}

                _state.value = SignFullState.SuccessProvider(response)

            }catch (e:Exception){
                _state.value = SignFullState.Error("Hubo un error")

            }


        }
    }

}
