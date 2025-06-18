package com.dev.randomcityapp.data.repository


import com.dev.randomcityapp.data.local.EmissionDao
import com.dev.randomcityapp.data.model.Emission
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmissionRepository(private val dao: EmissionDao) {
    val emissions: Flow<List<Emission>> = dao.allEmissions()

    companion object {
        val cities = listOf("New York", "Los Angeles", "Scranton", "Philadelphia", "Nashville", "Saint Louis", "Miami")
        val colors = listOf("Yellow", "White", "Green", "Blue", "Red", "Black")
    }
    suspend fun getEmissionById(id: Long): Emission? = dao.getEmissionById(id)

    suspend fun generateRandomEmission() = withContext(Dispatchers.Default) {
        val emission = Emission(
            city = cities.random(),
            color = colors.random(),
            timestamp = System.currentTimeMillis()
        )
        dao.insert(emission)
    }
}

