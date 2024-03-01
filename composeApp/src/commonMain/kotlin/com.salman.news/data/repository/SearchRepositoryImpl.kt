package com.salman.news.data.repository

import com.salman.news.data.mapper.toDomain
import com.salman.news.data.source.remote.ArticlesRemoteDataSource
import com.salman.news.domain.exception.EmptySearchResultException
import com.salman.news.domain.model.Article
import com.salman.news.domain.model.SearchTimeFrame
import com.salman.news.domain.repository.SearchRepository

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/24/2024.
 */
class SearchRepositoryImpl(
    private val remoteDataSource: ArticlesRemoteDataSource,
) : SearchRepository {
    private var lastSearchResult: List<Article> = emptyList()
    override suspend fun search(query: String, timeFrame: SearchTimeFrame): List<Article> {
        val result = remoteDataSource.search(query, timeFrame.date)
        if (result.isSuccess) {
            lastSearchResult = result
                .getOrNull()?.articles?.map {
                    it.toDomain()
                } ?: emptyList()

            if (lastSearchResult.isEmpty()) {
                throw EmptySearchResultException()
            }
            return lastSearchResult
        } else {
            throw result.exceptionOrNull()!!
        }
    }

    override suspend fun getLastSearchResult(): List<Article> {
        return lastSearchResult
    }
}