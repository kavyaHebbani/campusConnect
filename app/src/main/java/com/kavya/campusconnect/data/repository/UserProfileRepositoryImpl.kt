package com.kavya.campusconnect.data.repository

import com.kavya.campusconnect.data.sampleUserProfile
import com.kavya.campusconnect.domain.repository.UserProfileRepository
import com.kavya.campusconnect.model.UserProfile
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor() : UserProfileRepository {

    override suspend fun getUserProfile(): UserProfile = sampleUserProfile
}