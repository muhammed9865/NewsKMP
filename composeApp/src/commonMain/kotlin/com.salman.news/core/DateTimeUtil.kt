package com.salman.news.core

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.DurationUnit
import kotlin.time.toDuration

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/26/2024.
 */
object DateTimeUtil {

    fun parseLocalDateTime(date: String): LocalDateTime {
        val instant = Instant.parse(date)
        return instant.toLocalDateTime(TimeZone.UTC)
    }

    fun format(date: LocalDateTime): String {
        val amOrPm = if (date.hour > 12) "PM" else "AM"
        val hour = if (date.hour > 12) // 24h-system to 12h-system
            24 - date.hour
        else
            date.hour
        return with(date) {
            "$hour:$minute $amOrPm $dayOfMonth/$monthNumber/$year"
        }
    }

    fun getCurrentDateMinusDaysFormatted(days: Int): String {
        val date = Clock.System
            .now()
            .minus(DateTimePeriod(days = days).days.toDuration(DurationUnit.DAYS))
            .toLocalDateTime(TimeZone.currentSystemDefault())

        // format: 2024-01-25
        return "${date.year}-${date.monthNumber}-${date.dayOfMonth}"
    }
}