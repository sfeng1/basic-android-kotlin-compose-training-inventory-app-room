/*
* Sheng Feng
* OSU
* CS 492
*/

package com.example.inventory.data

import android.content.Context

interface FlightAppContainer {
    val airportRepo: AirportRepo
}

class FlightAppDataContainer(private val context: Context) : FlightAppContainer {
    override val airportRepo: AirportRepo by lazy {
        AirportRepoOffline(
            AirportDatabase.getDatabase(context).airportDao(),
            AirportDatabase.getDatabase(context).favAirDao()
        )
    }
}
