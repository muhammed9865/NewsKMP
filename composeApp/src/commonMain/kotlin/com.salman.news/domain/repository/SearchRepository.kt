package com.salman.news.domain.repository

import com.salman.news.domain.model.Article
import com.salman.news.domain.model.SearchTimeFrame

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/24/2024.
 */
interface SearchRepository {

    suspend fun search(query: String, timeFrame: SearchTimeFrame): List<Article>

}