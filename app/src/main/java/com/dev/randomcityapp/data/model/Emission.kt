package com.dev.randomcityapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Emission(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val city: String,
    val color: String,
    val timestamp: Long
)