package com.colddelight.data.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

fun getTodayDate(): String {
    val currentDate = Date()
    val dateFormat = SimpleDateFormat("yy.MM.dd")
    return dateFormat.format(currentDate)
}


fun getTodayDateWithDayOfWeek(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd. EEE")
    return currentDate.format(formatter)
}

fun getDayOfWeek(
    dayOfWeek: Int
): String {
    return when (dayOfWeek) {
        1 -> "월요일"
        2 -> "화요일"
        3 -> "수요일"
        4 -> "목요일"
        5 -> "금요일"
        6 -> "토요일"
        else -> "일요일"
    }
}








