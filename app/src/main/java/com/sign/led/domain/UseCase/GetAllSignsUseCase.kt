package com.sign.led.domain.UseCase

import com.sign.led.domain.Repository
import javax.inject.Inject

class GetAllSignsUseCase @Inject constructor(private val repository: Repository) {

        suspend operator fun invoke() = repository.getSigns()

}