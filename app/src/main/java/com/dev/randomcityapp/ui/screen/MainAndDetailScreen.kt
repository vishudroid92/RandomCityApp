package com.dev.randomcityapp.ui.screen


import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dev.randomcityapp.viewmodel.DetailViewModel
import com.dev.randomcityapp.viewmodel.MainViewModel
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import com.dev.randomcityapp.R

@Composable
fun MainAndDetailScreen(
    navController: NavController,
    backStackEntry: NavBackStackEntry,
    mainViewModel: MainViewModel = viewModel(),
    detailViewModel: DetailViewModel = viewModel()
) {
    val configuration = LocalConfiguration.current
    val isTabletLandscape = configuration.smallestScreenWidthDp >= 600 &&
            configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val emissions by mainViewModel.emissions.collectAsState(emptyList())
    val detailId = backStackEntry.arguments?.getString(stringResource(R.string.id))?.toLongOrNull()

    var selectedId by rememberSaveable { mutableStateOf<Long?>(null) }

    LaunchedEffect(emissions) {
        if (isTabletLandscape && emissions.isNotEmpty() && selectedId == null) {
            selectedId = emissions.first().id
        }
    }

    if (isTabletLandscape) {
        Row(modifier = Modifier.fillMaxWidth()) {
            MainScreen(
                navController = navController,
                vm = mainViewModel,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                onItemSelected = { id ->
                    selectedId = id
                },
                selectedItemId = selectedId
            )

            selectedId?.let { id ->
                DetailScreenTabletOnly(
                    id = id,
                    viewModel = detailViewModel,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
            }
        }
    } else {
        if (detailId != null) {
            // If phone and detail ID passed, show detail screen
            DetailScreen(navController, backStackEntry, viewModel = detailViewModel)
        } else {
            // If phone and no detail ID, show main screen
            MainScreen(
                navController = navController,
                vm = mainViewModel,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


