package com.kavya.campusconnect.data

import com.kavya.campusconnect.model.CampusEvent

val sampleEvents = listOf(
    CampusEvent(
        id = "welcome-week",
        title = "Welcome Week Meetup",
        date = "Monday, October 12",
        time = "10:00 AM",
        location = "Student Union, Main Hall",
        description = "Meet other students and explore campus clubs."
    ),
    CampusEvent(
        id = "career-workshop",
        title = "Career Planning Workshop",
        date = "Tuesday, October 13",
        time = "2:30 PM",
        location = "Library, Room 204",
        description = "Get practical tips for resumes and interviews."
    ),
    CampusEvent(
        id = "outdoor-movie",
        title = "Outdoor Movie Night",
        date = "Friday, October 16",
        time = "7:00 PM",
        location = "North Quad",
        description = "Bring a blanket and enjoy a movie under the stars."
    )
)
