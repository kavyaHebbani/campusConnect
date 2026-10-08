package com.kavya.campusconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint

import com.kavya.campusconnect.ui.screens.EventsScreen
import com.kavya.campusconnect.ui.screens.CampusServicesScreen
import com.kavya.campusconnect.ui.screens.HomeScreen
import com.kavya.campusconnect.ui.screens.MessagesScreen
import com.kavya.campusconnect.ui.screens.ProfileScreen
import com.kavya.campusconnect.ui.theme.CampusConnectTheme
import com.kavya.campusconnect.ui.viewmodel.EventsViewModel
import com.kavya.campusconnect.ui.viewmodel.HomeViewModel
import com.kavya.campusconnect.ui.viewmodel.ProfileViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampusConnectTheme {
                CampusConnectApp()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CampusConnectApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
    val eventsViewModel: EventsViewModel = viewModel()
    val eventsUiState by eventsViewModel.uiState.collectAsStateWithLifecycle()
    val homeViewModel: HomeViewModel = viewModel()
    val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val profileViewModel: ProfileViewModel = viewModel()
    val profileUiState by profileViewModel.uiState.collectAsStateWithLifecycle()

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            painterResource(it.icon),
                            contentDescription = it.label,
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        if (currentDestination == AppDestinations.HOME) {
                            Column {
                                Text(
                                    "Welcome to Your Campus",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    LocalDate.now().format(
                                        DateTimeFormatter.ofPattern(
                                            "EEEE, MMMM d",
                                            Locale.getDefault()
                                        )
                                    ),
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        } else {
                            Text(
                                currentDestination.label,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { innerPadding ->
            when (currentDestination) {
                AppDestinations.HOME -> HomeScreen(
                    onEventsClick = { currentDestination = AppDestinations.EVENTS },
                    onCampusServicesClick = {
                        currentDestination = AppDestinations.CAMPUS_SERVICES
                    },
                    onPaymentClick = { currentDestination = AppDestinations.PROFILE },
                    onRetry = homeViewModel::loadUserProfile,
                    modifier = Modifier.padding(innerPadding),
                    uiState = homeUiState
                )

                AppDestinations.EVENTS -> EventsScreen(
                    uiState = eventsUiState,
                    onRetry = eventsViewModel::loadEvents,
                    modifier = Modifier.padding(innerPadding)
                )

                AppDestinations.MESSAGES -> MessagesScreen(Modifier.padding(innerPadding))
                AppDestinations.PROFILE -> ProfileScreen(
                    uiState = profileUiState,
                    onRetry = profileViewModel::loadUserProfile,
                    modifier = Modifier.padding(innerPadding)
                )

                AppDestinations.CAMPUS_SERVICES -> CampusServicesScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: Int,
) {
    HOME("Home", R.drawable.ic_home),
    EVENTS("Events", R.drawable.ic_favorite),
    MESSAGES("Messages", R.drawable.ic_account_box),
    PROFILE("Profile", R.drawable.ic_account_box),
    CAMPUS_SERVICES("Campus", R.drawable.ic_map),
}
