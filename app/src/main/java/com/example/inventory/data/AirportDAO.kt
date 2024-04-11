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
interface AirportDAO {

    // get airports like search
    @Query("SELECT * FROM airport WHERE iata_code LIKE '%' || :query || '%' OR name like '%' || :query || '%' ORDER BY passengers DESC")
    fun getAirports(query: String): Flow<List<Airport>>

    // get one airport from search
    @Query("SELECT * FROM airport WHERE iata_code = :query OR name = :query limit 1")
    fun getAirport(query: String): Flow<Airport>

    // get all airports
    @Query("SELECT * FROM airport ORDER BY passengers DESC")
    fun getAllAirports(): Flow<List<Airport>>

    // get flights related to the selected airport
    @Query("SELECT DISTINCT t1.name as flight_start, t1.iata_code as iata_start, t2.name as flight_end, t2.iata_code as iata_end FROM airport as t1 JOIN airport as t2 WHERE t1.name <> t2.name AND t1.name = :query")
    fun getFlights(query: String): Flow<List<Flights>>

    // look up a fav entry and return a flight
    @Query("SELECT DISTINCT t1.name as flight_start, t1.iata_code as iata_start, t2.name as flight_end, t2.iata_code as iata_end FROM airport as t1 JOIN airport as t2 WHERE t1.iata_code = :depart  AND t2.iata_code = :dest")
    fun getFavDeets(depart:String, dest:String): Flow<Flights>

}