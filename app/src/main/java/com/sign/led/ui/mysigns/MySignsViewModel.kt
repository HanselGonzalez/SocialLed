package com.sign.led.ui.mysigns

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sign.led.domain.UseCase.CreateSignUseCase
import com.sign.led.domain.UseCase.GetAllSignsUseCase
import com.sign.led.domain.model.ItemViewFullModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MySignsViewModel @Inject constructor(
    private val getAllSignsUseCase: GetAllSignsUseCase,
    private val createSignUseCase: CreateSignUseCase
) :
    ViewModel() {

    private var _state = MutableStateFlow<MySignsState>(MySignsState.Initial)
    val state: StateFlow<MySignsState> = _state

    fun getSigns() {
        viewModelScope.launch {
            _state.value = MySignsState.Loading

            val result = withContext(Dispatchers.IO) { getAllSignsUseCase() }

            if (result.isNotEmpty()) {
                _state.value = MySignsState.Success(result)
            } else {
                _state.value = MySignsState.Error("Empty List")
            }
        }
    }

    fun createSign(itemsFull: ItemViewFullModel) {
        viewModelScope.launch {
            try {

                withContext(Dispatchers.IO) { createSignUseCase(itemsFull) }

                val result = withContext(Dispatchers.IO){getAllSignsUseCase()}

                _state.value = MySignsState.Success(result)
            } catch (e: Exception) {
                _state.value = MySignsState.Error("Hubo un problema")

            }

        }
    }

}