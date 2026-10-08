package com.kavya.campusconnect.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.kavya.campusconnect.data.sampleCafeteriaMeals
import com.kavya.campusconnect.model.CafeteriaMeal

@Composable
fun CampusServicesScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Campus map", style = MaterialTheme.typography.titleLarge)
        CampusMapPreview()

        Text("Cafeteria menu", style = MaterialTheme.typography.titleLarge)

        sampleCafeteriaMeals.forEach { meal ->
            MealCard(meal)
        }
    }
}

@Composable
private fun CampusMapPreview() {
    val mapShape = RoundedCornerShape(20.dp)
    val roadColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .clip(mapShape)
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawLine(
                color = roadColor,
                start = Offset(0f, size.height * 0.72f),
                end = Offset(size.width, size.height * 0.3f),
                strokeWidth = 24.dp.toPx(),
                cap = StrokeCap.Round
            )
            drawLine(
                color = roadColor,
                start = Offset(size.width * 0.48f, 0f),
                end = Offset(size.width * 0.58f, size.height),
                strokeWidth = 20.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        MapMarker("Library", Modifier
            .align(Alignment.TopStart)
            .padding(12.dp))
        MapMarker("Science Hall", Modifier
            .align(Alignment.TopEnd)
            .padding(12.dp))
        MapMarker("Innovation Hub", Modifier
            .align(Alignment.Center)
            .padding(4.dp))
        MapMarker("Cafeteria", Modifier
            .align(Alignment.BottomStart)
            .padding(12.dp))
        MapMarker("HCC", Modifier
            .align(Alignment.BottomEnd)
            .padding(12.dp))
    }
}

@Composable
private fun MapMarker(label: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shadowElevation = 2.dp
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
private fun MealCard(meal: CafeteriaMeal) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(meal.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    meal.servingHours,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            meal.items.forEach { item ->
                Text(
                    text = "•  $item",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
