/*
* Sheng Feng
* OSU
* CS 492
*/

package com.example.inventory.ui.home

import com.example.inventory.data.Airport
import com.example.inventory.data.FavAir
import com.example.inventory.data.Flights
import kotlinx.coroutines.flow.Flow

data class HomeUiState(
    var screenTitle: String = "Flight Search",
    var filterAirportList: List<Airport> = emptyList(),
    var filterFlightList: List<Flights> = emptyList(),
    var favList: List<FavAir> = emptyList(),
    var searchQuery: Airport? = null,
    )
