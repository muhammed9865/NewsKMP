package com.salman.news.presentation.common.extensions

import com.salman.news.MR
import com.salman.news.domain.model.SearchTimeFrame
import dev.icerock.moko.resources.StringResource

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/23/2024.
 */
val SearchTimeFrame.dateStringResource: StringResource
    get() = when (this) {
        SearchTimeFrame.Last24Hours -> MR.strings.last_24_hours
        SearchTimeFrame.Last7Days -> MR.strings.last_7_days
        SearchTimeFrame.Last14Days -> MR.strings.last_14_days
    }