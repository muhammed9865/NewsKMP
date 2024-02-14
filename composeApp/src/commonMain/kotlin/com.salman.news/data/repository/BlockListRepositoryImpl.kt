package com.salman.news.data.repository

import com.salman.news.data.mapper.toDomain
import com.salman.news.data.source.local.ArticlesLocalDataSource
import com.salman.news.data.source.local.BlockListLocalDataSource
import com.salman.news.data.source.remote.model.article.Source
import com.salman.news.domain.model.ArticleSource
import com.salman.news.domain.model.BlockListedItem
import com.salman.news.domain.repository.BlockListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/14/2024.
 */
class BlockListRepositoryImpl(
    private val localDataSource: BlockListLocalDataSource
) : BlockListRepository {

    override suspend fun getBlackListedItems(): Flow<List<BlockListedItem>> {
        return localDataSource.getBlockListFlow().map { entities ->
            entities.map {
                it.toDomain()
            }
        }
    }

    override suspend fun blockSource(source: ArticleSource) {
        localDataSource.blockSource(Source(source.id, source.name))
    }

    override suspend fun blockAuthor(author: String) {
        localDataSource.blockAuthor(author)
    }

    override suspend fun unblockItem(id: Long) {
        localDataSource.unblock(id)
    }

    override suspend fun clearBlackList() {
        localDataSource.clearBlackList()
    }
}