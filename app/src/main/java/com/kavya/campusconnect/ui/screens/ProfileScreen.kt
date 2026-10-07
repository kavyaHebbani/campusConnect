package com.kavya.campusconnect.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kavya.campusconnect.R
import com.kavya.campusconnect.model.UserProfile
import com.kavya.campusconnect.ui.viewmodel.ProfileUiState

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
        ProfileValuesRow {
            ProfileValue("Name", userProfile.name)
            ProfileValue("Age", userProfile.age.toString())
        }
    }

    ProfileItem(title = "Academic profile") {
        ProfileValuesRow {
            ProfileValue("GPA", userProfile.academicProfile.gpa.toString())
            ProfileValue("Hours", userProfile.academicProfile.hours.toString())
            ProfileValue("Attendance", userProfile.academicProfile.attendance)
            ProfileValue("Advisor", userProfile.academicProfile.advisor)
        }
    }

    ProfileItem(title = "Financials") {
        ProfileValuesRow {
            userProfile.financials.forEach { (key, value) ->
                ProfileValue(key, value.toString())
            }
        }
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
private fun ProfileValuesRow(content: @Composable () -> Unit) {
    val scrollState = rememberScrollState()
    Column {
        Row(
            modifier = Modifier
                .padding(bottom = 12.dp)
                .fillMaxWidth()
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            content()
        }

        Icon(
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = "More content to the right",
            modifier = Modifier.align(Alignment.End),
            tint = MaterialTheme.colorScheme.primary
        )
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
private fun ProfileValue(label: String, value: String) {
    val tileShape = RoundedCornerShape(12.dp)
    Column(
        modifier = Modifier
            .width(120.dp)
            .fillMaxHeight()
            .clip(tileShape)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, tileShape)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            label, style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}
