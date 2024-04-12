Flight Search App
==================================
This is my solution code for the **Create a Flight Search app** code lab in the **Store and access data using keys with DataStore** pathway on developer.android.com.

A link to the project brief can be found here: https://developer.android.com/codelabs/basic-android-kotlin-compose-flight-search#0

You can find a video demo of the app here: https://youtu.be/goO5_6GT_ks

Overview
------------
This app ingests a provided database of airports and uses it to construct a list of flights (each airport to every other airport).

Users can then search for a specific airport by name or IATA code, the search bar will offer autocomplete suggestions based on the current input.

Once a specific airport is selected, all flights originating from that airport are displayed.
Individual flights can then be favorited. Favorite flights will appear on the main screen whenever no search query is active. 


Implementation Details
--------------
All UI elements were built using Jetpack Compose 
The database and associated queries were created using the Room database framework, including DAOs and entities 
The UI is driven by a ViewModel that additionally makes use of Flow, StateFlow, and StateUi
Coroutines are used for long-running queries
Airports and favorited flights are stored in SQLite databases


Running the App
---------------
1. Download all files into a folder
2. Download and install Android Studio
3. Open the app folder in Android Studio and wait for all the initial setup to be complete
4. Ensure you have an Android device configured in the device manager to emulate the app (the default device from the Android Studio installation will work)
5. Run the app and it will appear on the emulated device 
