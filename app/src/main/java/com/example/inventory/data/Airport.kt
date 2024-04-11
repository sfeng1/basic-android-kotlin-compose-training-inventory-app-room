/*
* Sheng Feng
* OSU
* CS 492
*/

package com.example.inventory.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "airport")
data class Airport(
    @PrimaryKey
    val id: Int,
    val iata_code: String,
    val name: String,
    val passengers: Int
)

data class Flights(
    val flight_start: String,
    val iata_start: String,
    val flight_end: String,
    val iata_end: String,
)
