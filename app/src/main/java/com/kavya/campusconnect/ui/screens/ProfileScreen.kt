package com.kavya.campusconnect.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import com.kavya.campusconnect.R
import com.kavya.campusconnect.model.UserProfile
import com.kavya.campusconnect.ui.viewmodel.ProfileUiState
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Profile", style = MaterialTheme.typography.headlineSmall)

        when (uiState) {
            ProfileUiState.Loading -> Text(
                "Loading profile…",
                style = MaterialTheme.typography.bodyLarge
            )

            is ProfileUiState.Error -> {
                Text(
                    uiState.message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
                Button(onClick = onRetry) { Text("Try again") }
            }

            is ProfileUiState.Success -> ProfileDetails(uiState.userProfile)
        }
    }
}

@Composable
private fun ProfileDetails(userProfile: UserProfile) {
    ProfileItem(title = "About") {
        ProfileValuesGrid(
            listOf(
                "Name" to userProfile.name,
                "Age" to userProfile.age.toString()
            )
        )
    }

    ProfileItem(title = "Academic profile") {
        ProfileValuesGrid(
            listOf(
                "GPA" to userProfile.academicProfile.gpa.toString(),
                "Hours" to userProfile.academicProfile.hours.toString(),
                "Attendance" to userProfile.academicProfile.attendance,
                "Advisor" to userProfile.academicProfile.advisor
            )
        )
    }

    ProfileItem(title = "Financials") {
        val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)
        ProfileValuesGrid(
            userProfile.financials.map { (key, value) -> key to currencyFormatter.format(value) }
        )
    }

    ProfileItem(title = "My Schedule") {
        if (userProfile.userSchedule.isEmpty()) {
            Text(
                "No classes or schedule items yet.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            userProfile.userSchedule.forEachIndexed { index, schedule ->
                ScheduleRow(schedule)
                if (index < userProfile.userSchedule.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 96.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileValuesGrid(values: List<Pair<String, String>>) {
    BoxWithConstraints {
        val columnCount = if (maxWidth >= 300.dp) 2 else 1
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            values.chunked(columnCount).forEach { rowValues ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowValues.forEach { (label, value) ->
                        ProfileValue(
                            label = label,
                            value = value,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileItem(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(title, style = MaterialTheme.typography.titleLarge)
            content()
        }
    }
}

@Composable
private fun ProfileValue(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(profileIconFor(label)),
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            label,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

private fun profileIconFor(label: String): Int = when (label.lowercase()) {
    "name" -> R.drawable.ic_person
    "age" -> R.drawable.ic_calendar
    "gpa" -> R.drawable.ic_school
    "hours" -> R.drawable.ic_schedule
    "attendance" -> R.drawable.ic_check
    "advisor" -> R.drawable.ic_person
    else -> R.drawable.ic_wallet
}
