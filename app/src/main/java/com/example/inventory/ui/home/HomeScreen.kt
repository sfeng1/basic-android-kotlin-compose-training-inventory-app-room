/*
* Sheng Feng
* OSU
* CS 492
*/

package com.example.inventory.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items


import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.inventory.ui.theme.InventoryTheme

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.data.Airport
import com.example.inventory.data.FavAir
import com.example.inventory.data.Flights
import com.example.inventory.ui.AppViewModelProvider
import kotlinx.coroutines.launch
import androidx.compose.foundation.verticalScroll


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeState: HomeUiState by viewModel.flightUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var query_text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            FlightAppBar(
                titleString = homeState.screenTitle,
                modifier = Modifier,
                canNavigateBack = if(homeState.searchQuery == null) {false} else{true},
                navigateUp = {
                    viewModel.returnHome()
                    active = true
                    query_text = ""
                             },
            )}
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 5.dp),
                query = query_text,
                onQueryChange = {
                    query_text = it
                    viewModel.searchBarUpdate(query_text)
                },
                onSearch = {
                            viewModel.searchFindFlights(query_text)
                            active = false
                           },
                active = active,
                onActiveChange = {
                    active = it
                },
                placeholder = {Text("Enter airport name or iata code")},
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = "Trailing Icon")},
            ) {
                Column(Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                ) {
                    if (active == true && homeState.searchQuery == null && query_text != "") {
                        // for auto complete
                        Spacer(modifier = Modifier.height(20.dp))
                        Text("Auto Complete Suggestions:", fontWeight = FontWeight.Bold)

                        homeState.filterAirportList.forEach { queryAirport ->
                            AirportCard(
                                airportContent = queryAirport,
                                onClick = {
                                    active = false
                                    viewModel.searchFindFlightsBtn(queryAirport)
                                }
                            )
                        }
                    } else {

                        // for favorite
                        Spacer(modifier = Modifier.height(20.dp))
                        Text("Favorites List:", fontWeight = FontWeight.Bold)
                        homeState.favList.forEach { queryFlight ->
                            FavCard(
                                flights = viewModel.favLookup(queryFlight),
                            )
                        }
                    }
                }
            }
            // once search has been input
            Column (modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
            ){
                // see flights for an airport
                if (active == false && homeState.searchQuery != null){

                    Spacer(modifier = Modifier.height(20.dp))
                    Text("Flights From Selection:", fontWeight = FontWeight.Bold )

                    homeState.filterFlightList.forEach{
                            queryFlight ->
                        FlightCard(
                            flightContent = queryFlight,
                            onClick = {
                                coroutineScope.launch {
                                    viewModel.run{addFav(queryFlight)}
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightAppBar(
    titleString: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {Text(titleString)},
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "back"
                    )
                }
            }
        }
    )
}

@Composable
fun AirportCard(
    airportContent: Airport,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .absolutePadding(top = 10.dp, bottom = 10.dp)
    ) {
        Column{
        Text("Name: " + airportContent.name)
        Spacer(modifier = modifier.height(6.dp))
        Text("IATA: " + airportContent.iata_code)
        }
    }
}

@Composable
fun FavCard(
    flights: Flights,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .absolutePadding(top = 10.dp, bottom = 10.dp)
    ) {
        Column {
            Spacer(modifier = modifier.height(20.dp))
            Row {
                Spacer(modifier = modifier.width(10.dp))
                Column {
                    Text("Depart: ", fontWeight = FontWeight.Bold)
                    Row {
                        Text(flights.iata_start + "  ")
                        Text(flights.flight_start)
                    }
                }
            }
            Spacer(modifier = modifier.height(15.dp))
            Row {
                Spacer(modifier = modifier.width(10.dp))
                Column {
                    Text("Arrive: ", fontWeight = FontWeight.Bold)
                    Row {
                        Text(flights.iata_end + "  ")
                        Text(flights.flight_end)
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightCard(
    flightContent: Flights,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var saved by remember { mutableStateOf(false) }

    Card(
        onClick = {onClick()
            saved = true
                  },
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .absolutePadding(top = 10.dp, bottom = 10.dp),

    )
    {
        Row {
            Spacer(modifier = modifier.width(10.dp))
            Column {
                Column {
                    Spacer(modifier = modifier.height(20.dp))
                    Text("Depart: ", fontWeight = FontWeight.Bold)
                    Text(flightContent.iata_start)
                    Text(flightContent.flight_start)
                }
                Spacer(modifier = modifier.height(6.dp))
                Column {
                    Text("Arrive: ", fontWeight = FontWeight.Bold)
                    Text(flightContent.iata_end)
                    Text(flightContent.flight_end)
                }
                Spacer(modifier = modifier.height(6.dp))
                if (saved == false) {
                    Icon(
                        imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription = "favorite"
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "favorite_filled"
                    )
                }
            } //column
        } //Row
    }
}

@Preview(showBackground = true)
@Composable
fun CityPreview() {
    InventoryTheme {
        FavCard(
            flights = Flights(iata_start = "MUC", iata_end  = "RUS", flight_start = "Munich", flight_end = "Russia")
        )
    }
}
