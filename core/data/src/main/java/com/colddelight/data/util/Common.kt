package com.colddelight.data.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

fun getTodayDate(): String{
    val currentDate = Date()
    val dateFormat = SimpleDateFormat("yy.MM.dd")
    return dateFormat.format(currentDate)
}
