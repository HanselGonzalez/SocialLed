package com.sign.led.domain.UseCase

import com.sign.led.domain.Repository
import com.sign.led.domain.model.ItemViewFullModel
import javax.inject.Inject

class DeleteSignUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(itemsFull:ItemViewFullModel) = repository.deleteSign(itemsFull)

}