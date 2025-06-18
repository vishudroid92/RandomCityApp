package com.dev.randomcityapp.usecase

import com.dev.randomcityapp.data.repository.EmissionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EmissionGenerator(
     val repo: EmissionRepository,
    private val coroutineScope: CoroutineScope
) {
    private var emissionJob: Job? = null

    fun startGenerating() {
        if (emissionJob == null) {
            emissionJob = coroutineScope.launch {
                while (true) {
                    repo.generateRandomEmission()
                    delay(5000)
                }
            }
        }
    }

    fun stopGenerating() {
        emissionJob?.cancel()
        emissionJob = null
    }
}
