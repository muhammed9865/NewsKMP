package com.salman.news.domain.repository

import com.salman.news.domain.model.ArticleSource
import com.salman.news.domain.model.BlockListedItem
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/14/2024.
 */
interface BlockListRepository {

    suspend fun getBlackListedItems(): Flow<List<BlockListedItem>>
    suspend fun blockSource(source: ArticleSource)
    suspend fun blockAuthor(author: String)
    suspend fun unblockItem(id: Long)

    suspend fun clearBlackList()
}