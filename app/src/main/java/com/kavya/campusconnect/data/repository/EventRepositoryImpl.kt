package com.kavya.campusconnect.data.repository

import com.kavya.campusconnect.data.sampleEvents
import com.kavya.campusconnect.domain.repository.EventRepository
import com.kavya.campusconnect.model.CampusEvent

class EventRepositoryImpl : EventRepository {
    override fun getEvents(): List<CampusEvent> = sampleEvents
}
