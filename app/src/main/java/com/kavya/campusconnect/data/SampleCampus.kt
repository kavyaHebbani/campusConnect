package com.kavya.campusconnect.data

import com.kavya.campusconnect.model.CafeteriaMeal

val sampleCafeteriaMeals = listOf(
    CafeteriaMeal(
        name = "Breakfast",
        servingHours = "7:00–10:30 AM",
        items = listOf("Oatmeal and fresh fruit", "Egg and cheese sandwich", "Coffee and tea")
    ),
    CafeteriaMeal(
        name = "Lunch",
        servingHours = "11:00 AM–2:00 PM",
        items = listOf("Grilled chicken bowl", "Vegetable pasta", "Soup of the day")
    ),
    CafeteriaMeal(
        name = "Dinner",
        servingHours = "5:00–8:00 PM",
        items = listOf("Roasted vegetables", "Rice and tofu bowl", "Daily chef special")
    )
)