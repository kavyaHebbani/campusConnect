package com.kavya.campusconnect.domain.repository

import com.kavya.campusconnect.model.CampusEvent

interface EventRepository {
    suspend fun getEvents(): List<CampusEvent>
}
