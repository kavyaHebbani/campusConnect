package com.kavya.campusconnect.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavya.campusconnect.domain.repository.UserProfileRepository
import com.kavya.campusconnect.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface HomeUiState {
    data class Success(val userProfile: UserProfile) : HomeUiState
    data class Error(val message: String) : HomeUiState
    data object Loading : HomeUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            _uiState.value = try {
                HomeUiState.Success(userProfileRepository.getUserProfile())
            } catch (ex: Exception) {
                HomeUiState.Error(ex.message ?: "Couldn't load user profile.")
            }
        }
    }
}