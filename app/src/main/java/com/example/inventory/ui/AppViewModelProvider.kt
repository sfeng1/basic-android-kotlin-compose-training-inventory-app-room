/*
* Sheng Feng
* OSU
* CS 492
*/

package com.example.inventory.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.inventory.ui.home.HomeViewModel
import com.example.inventory.AirportApplication

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(airportApplication().container.airportRepo)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.airportApplication(): AirportApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as AirportApplication)