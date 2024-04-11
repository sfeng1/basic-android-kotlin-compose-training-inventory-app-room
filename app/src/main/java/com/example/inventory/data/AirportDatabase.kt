/*
* Sheng Feng
* OSU
* CS 492
*/

package com.example.inventory.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Airport::class, FavAir::class), version = 1, exportSchema = false)
abstract class AirportDatabase : RoomDatabase() {
    abstract fun airportDao(): AirportDAO
    abstract fun favAirDao(): FavAirDAO

    companion object {
        @Volatile
        private var Instance: AirportDatabase? = null

        fun getDatabase(context: Context): AirportDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AirportDatabase::class.java,
                    "airport_database"
                )
                    .createFromAsset("Database/flight_search.db")
                    .build()
                    .also {
                        Instance = it
                    }
            }
        }
    }
}