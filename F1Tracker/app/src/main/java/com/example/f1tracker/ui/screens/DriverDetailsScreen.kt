package com.example.f1tracker.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.f1tracker.data.repository.F1Repository
import com.example.f1tracker.model.DriverDto
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriverDetailsScreen(driver_number: Int, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var driver: DriverDto? by remember { mutableStateOf<DriverDto?>(null) }

    LaunchedEffect(driver_number) {
        val drivers = F1Repository.getDrivers()
        driver = drivers.find { it.driver_number == driver_number }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Driver Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (driver == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(driver?.headshot_url),
                    contentDescription = driver?.full_name,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(16.dp),
                    contentScale = ContentScale.Crop
                )
                Text("Name: ${driver?.full_name}", fontSize = 20.sp)
                Text("Abbreviation: ${driver?.name_acronym}", fontSize = 16.sp)
                Text("Country: ${driver?.country_code}", fontSize = 16.sp)
                Text("Team: ${driver?.team_name ?: "Unknown"}", fontSize = 16.sp)
            }
        }
    }
}