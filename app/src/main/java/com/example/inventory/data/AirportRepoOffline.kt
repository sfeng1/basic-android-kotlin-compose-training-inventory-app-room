/*
* Sheng Feng
* OSU
* CS 492
*/

package com.example.inventory.data

import kotlinx.coroutines.flow.Flow

class AirportRepoOffline(private val airportDAO: AirportDAO, private val favAirDAO: FavAirDAO) : AirportRepo {

    override fun getAirports(query: String): Flow<List<Airport>> = airportDAO.getAirports(query)
    override fun getAirport(query: String): Flow<Airport> = airportDAO.getAirport(query)
    override fun getAllAirports(): Flow<List<Airport>> = airportDAO.getAllAirports()
    override fun getFlights(query: String): Flow<List<Flights>> = airportDAO.getFlights(query)
    override fun getFavDeets(depart:String, dest:String): Flow<Flights> = airportDAO.getFavDeets(depart, dest)

    override fun getAllFavs(): Flow<List<FavAir>> = favAirDAO.getAllFavs()
    override suspend fun insert(favAir: FavAir) = favAirDAO.insert(favAir)
}