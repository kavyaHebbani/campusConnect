package com.kavya.campusconnect.ui.viewmodel

import com.kavya.campusconnect.domain.repository.EventRepository
import com.kavya.campusconnect.model.CampusEvent
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EventsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = mockk<EventRepository>()

    @Test
    fun loadEvents_repositoryReturnsEvents_emitsSuccess() = runTest {
        val events = listOf(sampleEvent)
        coEvery { repository.getEvents() } returns events

        val viewModel = EventsViewModel(repository)

        assertEquals(EventsUiState.Loading, viewModel.uiState.value)
        advanceUntilIdle()

        assertEquals(EventsUiState.Success(events), viewModel.uiState.value)
        coVerify(exactly = 1) { repository.getEvents() }
    }

    @Test
    fun loadEvents_repositoryReturnsNoEvents_emitsEmptySuccess() = runTest {
        coEvery { repository.getEvents() } returns emptyList()

        val viewModel = EventsViewModel(repository)
        advanceUntilIdle()

        assertEquals(EventsUiState.Success(emptyList()), viewModel.uiState.value)
    }

    @Test
    fun loadEvents_repositoryThrows_emitsError() = runTest {
        coEvery { repository.getEvents() } throws IllegalStateException("Events unavailable")

        val viewModel = EventsViewModel(repository)
        advanceUntilIdle()

        assertEquals(EventsUiState.Error("Events unavailable"), viewModel.uiState.value)
    }

    @Test
    fun loadEvents_afterFailure_canRetrySuccessfully() = runTest {
        coEvery { repository.getEvents() } throws IllegalStateException("Temporary failure") andThen listOf(sampleEvent)

        val viewModel = EventsViewModel(repository)
        advanceUntilIdle()
        assertEquals(EventsUiState.Error("Temporary failure"), viewModel.uiState.value)

        viewModel.loadEvents()
        runCurrent()

        assertEquals(EventsUiState.Success(listOf(sampleEvent)), viewModel.uiState.value)
        coVerify(exactly = 2) { repository.getEvents() }
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

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule : org.junit.rules.TestWatcher() {
    private val dispatcher = StandardTestDispatcher()

    override fun starting(description: org.junit.runner.Description) {
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: org.junit.runner.Description) {
        Dispatchers.resetMain()
    }
}
