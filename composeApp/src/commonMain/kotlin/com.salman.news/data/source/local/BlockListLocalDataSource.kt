package com.salman.news.data.source.local

import com.salman.news.data.source.local.entity.BlockListedEntity
import com.salman.news.data.source.remote.model.article.Source
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/14/2024.
 */
interface BlockListLocalDataSource {

    suspend fun getBlockListFlow(): Flow<List<BlockListedEntity>>
    suspend fun blockSource(source: Source)
    suspend fun blockAuthor(author: String)
    suspend fun unblock(id: Long)
    suspend fun clearBlackList()
}