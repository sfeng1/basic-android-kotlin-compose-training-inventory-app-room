/*
* Sheng Feng
* OSU
* CS 492
*/

package com.example.inventory.data

import kotlinx.coroutines.flow.Flow

interface AirportRepo {

    fun getAirports(query: String): Flow<List<Airport>>
    fun getAirport(query: String): Flow<Airport>
    fun getAllAirports(): Flow<List<Airport>>
    fun getFlights(query: String): Flow<List<Flights>>
    fun getFavDeets(depart:String, dest:String): Flow<Flights>

    fun getAllFavs(): Flow<List<FavAir>>
    suspend fun insert(favAir: FavAir)

}