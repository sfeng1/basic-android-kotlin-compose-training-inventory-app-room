/*
* Sheng Feng
* OSU
* CS 492
*/

package com.example.inventory.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "fav")
data class FavAir(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val departure_code: String,
    val destination_code: String,
)

