package com.dev.randomcityapp.data.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dev.randomcityapp.R
import com.dev.randomcityapp.data.model.Emission

@Database(entities = [Emission::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun emissionDao(): EmissionDao

    companion object {
        fun getInstance(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java,
                "random_city_db")
                .build()
    }
}

