package com.kavya.campusconnect.data.repository

import com.kavya.campusconnect.data.sampleEvents
import com.kavya.campusconnect.domain.repository.EventRepository
import com.kavya.campusconnect.model.CampusEvent
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor() : EventRepository {
    override suspend fun getEvents(): List<CampusEvent> = sampleEvents
}
