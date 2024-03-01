package com.salman.news.presentation.common.extensions

import com.salman.news.MR
import com.salman.news.domain.exception.EmptySearchResultException
import dev.icerock.moko.resources.StringResource

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/25/2024.
 */

val Throwable.stringResource: StringResource
    get() {
        return when (this) {
            is EmptySearchResultException -> {
                MR.strings.empty_search
            }

            else -> {
                MR.strings.something_went_wrong
            }
        }
    }