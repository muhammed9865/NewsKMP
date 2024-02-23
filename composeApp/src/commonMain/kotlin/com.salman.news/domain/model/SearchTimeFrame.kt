package com.salman.news.domain.model

import com.salman.news.core.DateTimeUtil.getCurrentDateMinusDaysFormatted

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/17/2024.
 */
sealed class SearchTimeFrame(val date: String) {
    data object Last24Hours : SearchTimeFrame(getCurrentDateMinusDaysFormatted(1))
    data object Last7Days : SearchTimeFrame(getCurrentDateMinusDaysFormatted(7))
    data object Last14Days : SearchTimeFrame(getCurrentDateMinusDaysFormatted(14))

    companion object {
        fun values() = setOf(Last24Hours, Last7Days, Last14Days)
        val Default = Last24Hours
    }

}