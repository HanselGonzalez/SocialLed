package com.sign.led.ui.signs

import androidx.lifecycle.ViewModel
import com.sign.led.data.providers.SignsProvider
import com.sign.led.domain.model.ItemViewFullModelInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignsViewModel @Inject constructor(signsProvider: SignsProvider) : ViewModel(){

    private var _signsP = MutableStateFlow<List<ItemViewFullModelInfo>>(emptyList())
    val signsP : StateFlow<List<ItemViewFullModelInfo>> = _signsP

    init{
        _signsP.value = signsProvider.getSignsProvider()
    }


}