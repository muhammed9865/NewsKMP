package com.salman.news.presentation.screen.home

import cafe.adriel.voyager.core.model.ScreenModel
import com.salman.news.domain.repository.ArticleRepository

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */
class HomeViewModel(
    private val repository: ArticleRepository
): ScreenModel {

}