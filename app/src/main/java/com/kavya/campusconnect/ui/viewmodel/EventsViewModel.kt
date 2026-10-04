package com.kavya.campusconnect.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.kavya.campusconnect.domain.repository.EventRepository
import com.kavya.campusconnect.model.CampusEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

sealed interface EventsUiState {
    data object Loading : EventsUiState
    data class Success(val events: List<CampusEvent>) : EventsUiState
    data class Error(val message: String) : EventsUiState
}

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<EventsUiState>(EventsUiState.Loading)
    val uiState: StateFlow<EventsUiState> = _uiState.asStateFlow()

    init {
        loadEvents()
    }

    fun loadEvents() {
        _uiState.value = EventsUiState.Loading
        _uiState.value = try {
            EventsUiState.Success(eventRepository.getEvents())
        } catch (exception: Exception) {
            EventsUiState.Error(exception.message ?: "Couldn't load events.")
        }
    }
}
