package com.kavya.campusconnect.ui.viewmodel

import com.kavya.campusconnect.domain.repository.UserProfileRepository
import com.kavya.campusconnect.model.UserProfile
import com.kavya.campusconnect.model.UserSchedule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: UserProfileRepository

    @Before
    fun setUp() {
        repository = mockk()
    }

    @Test
    fun loadUserProfile_repositoryReturnsProfile_emitsSuccess() = runTest {
        coEvery { repository.getUserProfile() } returns sampleProfile
        val viewModel = HomeViewModel(repository)

        assertEquals(HomeUiState.Loading, viewModel.uiState.value)
        advanceUntilIdle()

        assertEquals(HomeUiState.Success(sampleProfile), viewModel.uiState.value)
        coVerify(exactly = 1) { repository.getUserProfile() }
    }

    @Test
    fun loadUserProfile_repositoryReturnsProfileWithoutSchedule_emitsSuccess() = runTest {
        val profileWithoutSchedule = sampleProfile.copy(userSchedule = emptyList())
        coEvery { repository.getUserProfile() } returns profileWithoutSchedule
        val viewModel = HomeViewModel(repository)

        advanceUntilIdle()

        assertEquals(HomeUiState.Success(profileWithoutSchedule), viewModel.uiState.value)
    }

    @Test
    fun loadUserProfile_repositoryThrows_emitsError() = runTest {
        coEvery { repository.getUserProfile() } throws IllegalStateException("Profile unavailable")
        val viewModel = HomeViewModel(repository)

        advanceUntilIdle()

        assertEquals(HomeUiState.Error("Profile unavailable"), viewModel.uiState.value)
    }

    @Test
    fun loadUserProfile_afterFailure_canRetrySuccessfully() = runTest {
        coEvery { repository.getUserProfile() } throws IllegalStateException("Temporary failure") andThen sampleProfile
        val viewModel = HomeViewModel(repository)
        advanceUntilIdle()
        assertEquals(HomeUiState.Error("Temporary failure"), viewModel.uiState.value)

        viewModel.loadUserProfile()
        advanceUntilIdle()

        assertEquals(HomeUiState.Success(sampleProfile), viewModel.uiState.value)
        coVerify(exactly = 2) { repository.getUserProfile() }
    }

    private companion object {
        val sampleProfile = UserProfile(
            id = 1L,
            name = "Alex Morgan",
            age = 20,
            userSchedule = listOf(
                UserSchedule(
                    startTime = "5:00 PM",
                    endTime = "6:00 PM",
                    title = "Study Room Reservation",
                    location = "HCC 232"
                )
            ),
            gpa = "3.8"
        )
    }
}
