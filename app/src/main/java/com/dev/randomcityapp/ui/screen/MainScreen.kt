package com.dev.randomcityapp.ui.screen



import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dev.randomcityapp.data.model.Emission
import com.dev.randomcityapp.ui.navigation.Screen
import com.dev.randomcityapp.viewmodel.MainViewModel
import java.text.DateFormat
import java.util.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dev.randomcityapp.util.colorFromName


@Composable
fun MainScreen(
    navController: NavController,
    vm: MainViewModel = viewModel(),
    modifier: Modifier = Modifier,
    onItemSelected: ((Long) -> Unit)? = null,
    selectedItemId: Long? = null
) {
    val items by vm.emissions.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { emission ->
            EmissionRow(
                emission = emission,
                isSelected = selectedItemId == emission.id,
                onClick = {
                    onItemSelected?.invoke(emission.id)
                        ?: navController.navigate(Screen.Detail.createRoute(emission.id))
                }
            )
        }
    }
}



@Composable
fun EmissionRow(
    emission: Emission,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
    } else {
        MaterialTheme.colorScheme.surface
    }

    val borderColor = if (isSelected) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
    } else {
        Color.Transparent
    }

    Card(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        ListItem(
            leadingContent = {
                // Colored circle
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(colorFromName(emission.color), shape = CircleShape)
                )
            },
            headlineContent = {
                Text(
                    emission.city,
                    color = colorFromName(emission.color),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            supportingContent = {
                Text(
                    DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
                        .format(Date(emission.timestamp)),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmissionRowPreview() {
    EmissionRow(
        emission = Emission(1, "Paris", "Red", System.currentTimeMillis()),
        onClick = {}
    )
}


