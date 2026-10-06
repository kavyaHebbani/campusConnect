package com.kavya.campusconnect.data

import com.kavya.campusconnect.model.UserSchedule
import com.kavya.campusconnect.model.UserProfile

val userScheduleOfTheDays = listOf(
    UserSchedule(
        startTime = "9:00 AM",
        endTime = "10:00 AM",
        title = "Biology Lecture",
        location = "Science Hall 101"
    ),
    UserSchedule(
        startTime = "1:00 PM",
        endTime = "2:00 PM",
        title = "Design Project Review",
        location = "Innovation Hub 204"
    ),
    UserSchedule(
        startTime = "5:00 PM",
        endTime = "6:00 PM",
        title = "Study Room Reservation",
        location = "HCC 232"
    )
)

val sampleUserProfile = UserProfile(
    id = 12345,
    name = "Jake",
    age = 20,
    userSchedule = userScheduleOfTheDays,
    gpa = "8.5"
)