package com.sign.led.domain.UseCase

import com.sign.led.domain.Repository
import com.sign.led.domain.model.ItemViewFullModel
import javax.inject.Inject

class CreateSignUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(itemsFull:ItemViewFullModel) = repository.createSign(itemsFull)

}