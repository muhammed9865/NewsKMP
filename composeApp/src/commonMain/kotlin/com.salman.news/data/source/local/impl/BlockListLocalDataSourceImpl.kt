package com.salman.news.data.source.local.impl

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.salman.news.data.mapper.toEntity
import com.salman.news.data.source.local.BlockListLocalDataSource
import com.salman.news.data.source.local.entity.BlockListedEntity
import com.salman.news.data.source.remote.model.article.Source
import com.salman.news.database.NewsDatabase
import com.salman.news.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.coroutineContext

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/14/2024.
 */
class BlockListLocalDataSourceImpl(
    private val database: NewsDatabase
) : BlockListLocalDataSource {

    private val blockListQueries
        get() = database.blockListQueries

    override suspend fun getBlockListFlow(): Flow<List<BlockListedEntity>> {
        Logger.debug(this, "BlockListFlow started")
        return blockListQueries.getBlockList()
            .asFlow()
            .mapToList(coroutineContext)
            .map { blockList ->
                Logger.debug(this, blockList.toString())
                blockList.map { it.toEntity() }
            }
    }

    override suspend fun blockSource(source: Source) {
        blockListQueries.blockItem(source_name = source.name, author = null)
    }

    override suspend fun blockAuthor(author: String) {
        blockListQueries.blockItem(source_name = null, author = author)
    }

    override suspend fun unblock(id: Long) {
        blockListQueries.unblockItem(id)
    }

    override suspend fun clearBlackList() {
        blockListQueries.clearBlockList()
    }
}