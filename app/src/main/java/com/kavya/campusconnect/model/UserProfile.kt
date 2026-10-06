package com.kavya.campusconnect.model

data class UserProfile(
    val id: Long,
    val name: String,
    val age: Int,
    val userSchedule: List<UserSchedule>,
    val gpa: String
)
