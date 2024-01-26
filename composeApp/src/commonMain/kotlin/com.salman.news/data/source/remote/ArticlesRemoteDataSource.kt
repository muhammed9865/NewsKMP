package com.salman.news.data.source.remote

import com.salman.news.data.model.article.ArticlesDTO

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */
interface ArticlesRemoteDataSource {

    /**
     * Fetches articles based on the [countryCode]
     *
     * @param page the page index to fetch, used for pagination
     * @param countryCode used to fetch news for a specific country.
     *
     * @return [ArticlesDTO]
     */
    suspend fun getTopHeadlines(page: Int, countryCode: String): ArticlesDTO
}