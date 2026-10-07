package com.kavya.campusconnect.data

import com.kavya.campusconnect.model.UserAcademicProfile
import com.kavya.campusconnect.model.UserProfile
import com.kavya.campusconnect.model.UserSchedule

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

val sampleUserAcademicProfile = UserAcademicProfile(
    gpa = 8.5,
    hours = 200,
    attendance = "80/100",
    advisor = "Emma"
)

val sampleUserProfile = UserProfile(
    id = 12345,
    name = "Jake",
    age = 20,
    userSchedule = userScheduleOfTheDays,
    academicProfile = sampleUserAcademicProfile,
    financials = mapOf("parking" to 0, "tuition" to 500, "dining" to 20)
)