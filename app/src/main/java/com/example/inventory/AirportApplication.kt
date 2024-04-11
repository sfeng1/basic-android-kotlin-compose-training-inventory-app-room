/*
* Sheng Feng
* OSU
* CS 492
*/

package com.example.inventory

import android.app.Application
import com.example.inventory.data.FlightAppContainer
import com.example.inventory.data.FlightAppDataContainer

class AirportApplication : Application() {

    lateinit var container: FlightAppContainer

    override fun onCreate() {
        super.onCreate()
        container = FlightAppDataContainer(this)
    }
}
