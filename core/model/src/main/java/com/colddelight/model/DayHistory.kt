package com.colddelight.model

import java.time.LocalDate

data class DayHistory(
    val createdTime: LocalDate,
    val categoryList: List<Int>,
    val id: Int = 0,
)
