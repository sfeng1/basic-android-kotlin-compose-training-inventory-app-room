/*
* Sheng Feng
* OSU
* CS 492
*/

package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FavAirDAO {
    // get all favs
    @Query("SELECT * FROM fav")
    fun getAllFavs(): Flow<List<FavAir>>

    // add to favs table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favAir: FavAir)
}