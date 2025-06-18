package com.dev.randomcityapp.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.dev.randomcityapp.util.getLatLng
import com.dev.randomcityapp.viewmodel.DetailViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import com.dev.randomcityapp.R
import com.dev.randomcityapp.util.colorFromName
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    backStackEntry: NavBackStackEntry,
    viewModel: DetailViewModel = viewModel()
) {
    val id = backStackEntry.arguments?.getString("id")?.toLongOrNull() ?: return

    val emission by viewModel.emission.observeAsState()

    LaunchedEffect(id) {
        viewModel.loadEmission(id)
    }
    Log.d("Test", "DetailScreen: ${emission}")
    emission?.let { emissionData ->
        LaunchedEffect(emissionData.id) {
            viewModel.enqueueWelcomeWorker(emissionData.city)
        }

        Scaffold(topBar = {
            TopAppBar(
                title = { Text(emissionData.city) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.content_desc_back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorFromName(emissionData.color)
                )
            )
        }) { padding ->
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(getLatLng(emissionData.city), 10f)
                }
            ) {
                Marker(
                    state = MarkerState(position = getLatLng(emissionData.city)),
                    title = emissionData.city
                )
            }

        }
    } ?:
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

    }
}



