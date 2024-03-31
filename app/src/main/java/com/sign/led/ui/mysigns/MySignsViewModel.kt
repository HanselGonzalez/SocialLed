package com.sign.led.ui.mysigns

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sign.led.domain.UseCase.CreateSignUseCase
import com.sign.led.domain.UseCase.DeleteSignUseCase
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
    private val createSignUseCase: CreateSignUseCase,
    private val deleteSignUseCase: DeleteSignUseCase
) :
    ViewModel() {

    private var _state = MutableStateFlow<MySignsState>(MySignsState.Initial)
    val state: StateFlow<MySignsState> = _state

    private var _insertionCompleted = MutableStateFlow<Boolean?>(null)
    val insertionCompleted: StateFlow<Boolean?> = _insertionCompleted

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
                Log.i("listFinalBd","primero: $itemsFull")
                withContext(Dispatchers.IO) { createSignUseCase(itemsFull) }
                Log.i("listFinalBd","segundo $itemsFull")

                val result = withContext(Dispatchers.IO){getAllSignsUseCase()}

                _state.value = MySignsState.Success(result)

                _insertionCompleted.value = true
            } catch (e: Exception) {
                _state.value = MySignsState.Error("Hubo un problema")
                _insertionCompleted.value = false

            }

        }
    }

    fun deleteSign(itemsFull: ItemViewFullModel){

        viewModelScope.launch {

            _state.value = MySignsState.Loading

            try {

                withContext(Dispatchers.IO){deleteSignUseCase(itemsFull)}

                val result = withContext(Dispatchers.IO){getAllSignsUseCase()}

                _state.value = MySignsState.Success(result)

            }catch (e:Exception){

                _state.value = MySignsState.Error("Hubo un error")

            }
        }

    }


}