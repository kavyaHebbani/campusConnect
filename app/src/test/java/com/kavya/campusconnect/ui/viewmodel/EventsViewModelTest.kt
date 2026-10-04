package com.kavya.campusconnect.ui.viewmodel

import com.kavya.campusconnect.domain.repository.EventRepository
import com.kavya.campusconnect.model.CampusEvent
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test

class EventsViewModelTest {
    private val repository = mockk<EventRepository>()

    @Test
    fun loadEvents_repositoryReturnsEvents_emitsSuccess() {
        val events = listOf(sampleEvent)
        every { repository.getEvents() } returns events

        val viewModel = EventsViewModel(repository)

        assertEquals(EventsUiState.Success(events), viewModel.uiState.value)
        verify(exactly = 1) { repository.getEvents() }
    }

    @Test
    fun loadEvents_repositoryReturnsNoEvents_emitsEmptySuccess() {
        every { repository.getEvents() } returns emptyList()

        val viewModel = EventsViewModel(repository)

        assertEquals(EventsUiState.Success(emptyList()), viewModel.uiState.value)
    }

    @Test
    fun loadEvents_repositoryThrows_emitsError() {
        every { repository.getEvents() } throws IllegalStateException("Events unavailable")

        val viewModel = EventsViewModel(repository)

        assertEquals(EventsUiState.Error("Events unavailable"), viewModel.uiState.value)
    }

    @Test
    fun loadEvents_afterFailure_canRetrySuccessfully() {
        every { repository.getEvents() } throws IllegalStateException("Temporary failure") andThen listOf(sampleEvent)

        val viewModel = EventsViewModel(repository)
        assertEquals(EventsUiState.Error("Temporary failure"), viewModel.uiState.value)

        viewModel.loadEvents()

        assertEquals(EventsUiState.Success(listOf(sampleEvent)), viewModel.uiState.value)
        verify(exactly = 2) { repository.getEvents() }
    }

    private companion object {
        val sampleEvent = CampusEvent(
            id = "campus-tour",
            title = "Campus Tour",
            date = "Monday, October 12",
            time = "10:00 AM",
            location = "Main Gate",
            description = "Explore the campus with a student guide."
        )
    }
}
