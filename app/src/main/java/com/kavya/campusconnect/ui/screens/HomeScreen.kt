package com.kavya.campusconnect.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kavya.campusconnect.model.UserSchedule
import com.kavya.campusconnect.ui.viewmodel.HomeUiState

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onRetry: () -> Unit,
    onEventsClick: () -> Unit,
    onCampusServicesClick: () -> Unit,
    onPaymentClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Quick Links",
            style = MaterialTheme.typography.titleLarge
        )

        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val columns = if (maxWidth >= 600.dp) 4 else 2

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                maxItemsInEachRow = columns,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickLinkCard("Register for classes", Modifier.weight(1f))
                QuickLinkCard("Announcements", Modifier.weight(1f))
                QuickLinkCard(
                    label = "Campus map",
                    modifier = Modifier.weight(1f),
                    onClick = onCampusServicesClick
                )
                QuickLinkCard("My Grades", Modifier.weight(1f))
            }
        }

        Text(
            text = "My Schedule",
            style = MaterialTheme.typography.titleLarge
        )


        when (uiState) {
            is HomeUiState.Error -> {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = uiState.message,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(onClick = onRetry) {
                        Text("Try again")
                    }
                }
            }

            HomeUiState.Loading -> {
                Text("Loading events…", style = MaterialTheme.typography.bodyLarge)
            }

            is HomeUiState.Success -> {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    uiState.userProfile.userSchedule.forEachIndexed { index, schedule ->
                        ScheduleRow(schedule = schedule)
                        if (index < uiState.userProfile.userSchedule.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.padding(start = 96.dp),
                                color = MaterialTheme.colorScheme.outlineVariant
                            )
                        }
                    }
                }
            }
        }

        Text(
            text = "Explore",
            style = MaterialTheme.typography.titleLarge
        )

        FeatureCard(
            title = "Events",
            description = "Find activities and events happening on campus",
            onClick = onEventsClick
        )
        FeatureCard(
            title = "Payments",
            description = "See pending payment details",
            onClick = onPaymentClick
        )
        FeatureCard(
            title = "Campus",
            description = "Explore the campus map and cafeteria menu",
            onClick = onCampusServicesClick
        )

    }
}

@Composable
private fun QuickLinkCard(
    label: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val cardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    )
    if (onClick == null) {
        Card(
            modifier = modifier,
            shape = MaterialTheme.shapes.large,
            colors = cardColors,
        ) {
            QuickLinkCardContent(label)
        }
    } else {
        Card(
            onClick = onClick,
            modifier = modifier,
            shape = MaterialTheme.shapes.large,
            colors = cardColors,
        ) {
            QuickLinkCardContent(label)
        }
    }
}

@Composable
private fun QuickLinkCardContent(label: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 88.dp)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun FeatureCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow
    )

    if (onClick == null) {
        Card(
            modifier = modifier.fillMaxWidth(),
            colors = colors
        ) {
            FeatureCardContent(title = title, description = description)
        }
    } else {
        Card(
            onClick = onClick,
            modifier = modifier.fillMaxWidth(),
            colors = colors
        ) {
            FeatureCardContent(title = title, description = description)
        }
    }
}

@Composable
private fun FeatureCardContent(title: String, description: String) {
    Column(modifier = Modifier.padding(20.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
