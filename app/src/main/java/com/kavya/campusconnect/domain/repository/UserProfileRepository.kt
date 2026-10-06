package com.kavya.campusconnect.domain.repository

import com.kavya.campusconnect.model.UserProfile

interface UserProfileRepository {

    suspend fun getUserProfile(): UserProfile
}