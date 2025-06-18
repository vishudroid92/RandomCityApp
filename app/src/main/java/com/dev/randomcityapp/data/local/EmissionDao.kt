package com.dev.randomcityapp.data.local


import androidx.room.*
import com.dev.randomcityapp.data.model.Emission
import kotlinx.coroutines.flow.Flow

@Dao
interface EmissionDao {
    @Query("SELECT * FROM Emission ORDER BY city ASC")
    fun allEmissions(): Flow<List<Emission>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(emission: Emission)

    @Query("SELECT * FROM Emission WHERE id = :id")
    suspend fun getEmissionById(id: Long): Emission?

}

