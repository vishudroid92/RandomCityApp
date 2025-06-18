package com.dev.randomcityapp.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dev.randomcityapp.util.colorFromName
import com.dev.randomcityapp.util.getLatLng
import com.dev.randomcityapp.viewmodel.DetailViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenTabletOnly(
    id: Long,
    viewModel: DetailViewModel,
    modifier: Modifier = Modifier
) {
    val emission by viewModel.emission.observeAsState()
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(id) {
        viewModel.loadEmission(id)
    }

    emission?.let { emissionData ->
        Log.d("test", "Emission loaded: ${emissionData.city}")

        // Update camera when emission changes
        LaunchedEffect(emissionData.city) {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(getLatLng(emissionData.city), 10f)
        }
        LaunchedEffect(emissionData.id) {
            viewModel.enqueueWelcomeWorker(emissionData.city)
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(emissionData.city) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorFromName(emissionData.color)
                    )
                )
            },
            modifier = modifier
        ) { padding ->
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = MarkerState(position = getLatLng(emissionData.city)),
                    title = emissionData.city
                )
            }
        }
    } ?: Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}


