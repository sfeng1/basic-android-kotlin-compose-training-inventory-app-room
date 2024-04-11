/*
* Sheng Feng
* OSU
* CS 492
*/

package com.example.inventory.ui.home

import android.util.Log
import androidx.compose.material3.Text
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.inventory.data.Airport
import com.example.inventory.data.AirportRepo
import com.example.inventory.ui.home.HomeUiState
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.FavAir
import com.example.inventory.data.Flights
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

/**
 * ViewModel to retrieve all items in the Room database.
 */
class HomeViewModel(private val airportRepo: AirportRepo) : ViewModel() {

    private val _flightUiState = MutableStateFlow(HomeUiState())
    val flightUiState: StateFlow<HomeUiState> = _flightUiState.asStateFlow()

    // auto complete
    fun searchBarUpdate(query: String) {
        viewModelScope.launch {
            airportRepo.getAirports(query).collect { queryAirportList ->
                _flightUiState.update { currentState ->
                    currentState.copy(
                        filterAirportList = queryAirportList,
                    )
                }
            }
        }
    }

    // when user inputs search term
    fun searchFindFlights(query: String) {
        viewModelScope.launch {
            airportRepo.getAirport(query).collect { queryResult ->
                airportRepo.getFlights(queryResult.name).collect { queryFlightList ->
                    _flightUiState.update { currentState ->
                        currentState.copy(
                            screenTitle = queryResult.name,
                            searchQuery = queryResult,
                            filterAirportList = emptyList(),
                            filterFlightList = queryFlightList
                        )
                    }
                }
            }
        }
    }

    // when user clicks autocomplete button
    fun searchFindFlightsBtn(airport: Airport) {
        viewModelScope.launch {
                airportRepo.getFlights(airport.name).collect { queryFlightList ->
                    _flightUiState.update { currentState ->
                        currentState.copy(
                            screenTitle = airport.name,
                            searchQuery = airport,
                            filterAirportList = emptyList(),
                            filterFlightList = queryFlightList
                        )
                    }
                }
            }
        }

    // add a fav
    suspend fun addFav(flights: Flights) {
            airportRepo.insert(FavAir(departure_code = flights.iata_start, destination_code = flights.iata_end))
            airportRepo.getAllFavs().collect{ updateFavList ->
                    _flightUiState.update { currentState ->
                        currentState.copy(
                            favList = updateFavList,
                        )
                    }
                Log.d("mytag", _flightUiState.value.favList.toString())
                }
        }

    // return to home screen, reset all state vars
    fun returnHome() {
        viewModelScope.launch {
            _flightUiState.update { currentState ->
                currentState.copy(
                    screenTitle = "Flight Search",
                    searchQuery = null,
                    filterFlightList = emptyList(),
                    filterAirportList = emptyList(),
                    )
                }
            }
        }

    // return to home screen, reset all state vars
    fun favLookup(favAir: FavAir):Flights {
        lateinit var returnObj: Flights
        viewModelScope.launch {
            airportRepo.getFavDeets(favAir.departure_code, favAir.destination_code)
                .collect { favObj ->
                    returnObj = favObj
                }
        }
        return returnObj
    }


} // end


