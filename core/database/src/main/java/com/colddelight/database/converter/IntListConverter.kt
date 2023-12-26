package com.colddelight.database.converter

import androidx.room.TypeConverter

class IntListConverter {

    @TypeConverter
    fun fromString(value: String): List<Int> {
        return if(value.isBlank()) emptyList()
        else value.split(",").map { it.toInt() }
    }

    @TypeConverter
    fun toString(value: List<Int>): String {
        return value.joinToString(",")
    }
}