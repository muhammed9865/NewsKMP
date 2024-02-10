package com.salman.news.data.mapper

import com.salman.news.domain.model.ScreenMode

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/9/2024.
 */

fun String.toScreenMode(): ScreenMode = ScreenMode.valueOf(this)