package com.example.f1tracker.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.f1tracker.data.repository.F1Repository
import com.example.f1tracker.model.DriverDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class Driver(
    val number: Int,
    val name: String,
    val abbreviation: String,
    val country: String,
    val score: Int
)

val sampleDrivers = List(20) {
    Driver(
        number = it + 1,
        name = "Driver${it + 1}",
        abbreviation = "DRV",
        country = "Country${it + 1}",
        score = (100 - it * 2)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreBoard(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var drivers by remember { mutableStateOf<List<DriverDto>?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val mockScores = mapOf(
        "PIA" to 161, // Oscar Piastri
        "NOR" to 158, // Lando Norris
        "VER" to 136, // Max Verstappen
        "RUS" to 99,  // George Russell
        "LEC" to 79,  // Charles Leclerc
        "HAM" to 63,  // Lewis Hamilton
        "ANT" to 48,  // Andrea Kimi Antonelli
        "ALB" to 42,  // Alex Albon
        "OCO" to 20,  // Esteban Ocon
        "HAD" to 15,  // Isack Hadjar
        "STR" to 14,  // Lance Stroll
        "SAI" to 12,  // Carlos Sainz
        "TSU" to 10,  // Yuki Tsunoda
        "GAS" to 7,   // Pierre Gasly
        "HUL" to 6,   // Nico Hulkenberg
        "BEA" to 6,   // Oliver Bearman
        "LAW" to 4,   // Liam Lawson
        "ALO" to 0,   // Fernando Alonso
        "DOO" to 0,   // Jack Doohan
        "BOR" to 0    // Gabriel Bortoleto
    )


    // Load drivers on first composition
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                drivers = F1Repository.getDrivers()
                isLoading = false
            } catch (e: Exception) {
                errorMessage = "Failed to load drivers"
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("F1 Season 2025 Tracker") },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("settings")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    TextButton(onClick = {
                        navController.navigate("auth") {
                            popUpTo("home") { inclusive = true }
                        }
                    }) {
                        Text("Logout", color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator()
                }
                errorMessage != null -> {
                    Text(errorMessage ?: "Unknown error", color = MaterialTheme.colorScheme.error)
                }
                drivers != null -> {
                    val displayDrivers = drivers!!.map {
                        val score = mockScores[it.name_acronym] ?: 0

                        Driver(
                            number = it.driver_number ?: 0,
                            name = it.full_name ?: "Unknown",
                            abbreviation = it.name_acronym ?: "---",
                            country = it.country_code ?: "---",
                            score = score // No score in API yet
                        )
                    }.sortedByDescending { it.score }
                    ScoreboardTable(drivers = displayDrivers, navController = navController)
                }
            }
        }
    }
}

@Composable
fun ScoreboardTable(drivers: List<Driver>, navController: NavController) {
    // Table Header
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(8.dp)
    ) {
        HeaderCell("No.")
        HeaderCell("Name")
        HeaderCell("Abbr.")
        HeaderCell("Country")
        HeaderCell("Score")
    }

    // Table Body
    LazyColumn {
        itemsIndexed(drivers) { index, driver ->
            val backgroundColor: Color = if (isSystemInDarkTheme()) {
                if (index % 2 == 0) Color(0xFFF5F5F5) else Color.White
            } else {
                if (index % 2 == 0) Color(0xFF121318) else Color.Black
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundColor)
                    .padding(vertical = 8.dp, horizontal = 4.dp)
                    .clickable {
                        navController.navigate("driver/${driver.number}")
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TableCell(driver.number.toString())
                TableCell(driver.name)
                TableCell(driver.abbreviation)
                TableCell(driver.country)
                TableCell(driver.score.toString())
            }
        }
    }
}

@Composable
fun HeaderCell(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        modifier = Modifier
            .padding(horizontal = 4.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun TableCell(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        modifier = Modifier
            .padding(horizontal = 4.dp),
        textAlign = TextAlign.Center
    )
}

