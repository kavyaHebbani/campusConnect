package com.kavya.campusconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint

import com.kavya.campusconnect.ui.screens.EventsScreen
import com.kavya.campusconnect.ui.screens.HomeScreen
import com.kavya.campusconnect.ui.screens.MessagesScreen
import com.kavya.campusconnect.ui.theme.CampusConnectTheme
import com.kavya.campusconnect.ui.viewmodel.EventsViewModel
import com.kavya.campusconnect.ui.viewmodel.HomeViewModel

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
fun CampusConnectApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
    val eventsViewModel: EventsViewModel = viewModel()
    val eventsUiState by eventsViewModel.uiState.collectAsStateWithLifecycle()
    val homeViewModel: HomeViewModel = viewModel()
    val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            painterResource(it.icon),
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            when (currentDestination) {
                AppDestinations.HOME -> HomeScreen(
                    onEventsClick = { currentDestination = AppDestinations.EVENTS },
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
}
