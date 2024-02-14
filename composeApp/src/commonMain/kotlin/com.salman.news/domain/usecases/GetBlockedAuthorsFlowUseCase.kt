package com.salman.news.domain.usecases

import com.salman.news.core.Resource
import com.salman.news.domain.exception.EmptyBlockListException
import com.salman.news.domain.model.BlockListedItem
import com.salman.news.domain.repository.ArticleRepository
import com.salman.news.domain.repository.BlockListRepository
import com.salman.news.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/10/2024.
 */
class GetBlockedAuthorsFlowUseCase(
    private val blockListRepository: BlockListRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<BlockListedItem>>> {
        Logger.debug(this@GetBlockedAuthorsFlowUseCase, "Started")
        return blockListRepository.getBlackListedItems()
            .map { blockList ->
                val blockedAuthors =
                    blockList.filter { it.author?.isNotBlank() == true && it.source.isNullOrBlank() }
                if (blockedAuthors.isEmpty()) {
                    Resource.Error(EmptyBlockListException())
                } else {
                    Resource.Success(blockedAuthors)
                }
            }
    }
}