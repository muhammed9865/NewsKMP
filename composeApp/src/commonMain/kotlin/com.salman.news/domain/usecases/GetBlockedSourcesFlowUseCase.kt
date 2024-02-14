package com.salman.news.domain.usecases

import com.salman.news.core.Resource
import com.salman.news.domain.exception.EmptyBlockListException
import com.salman.news.domain.model.BlockListedItem
import com.salman.news.domain.repository.ArticleRepository
import com.salman.news.domain.repository.BlockListRepository
import com.salman.news.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/10/2024.
 */
class GetBlockedSourcesFlowUseCase(
    private val blockListRepository: BlockListRepository
) {

    suspend operator fun invoke(): Flow<Resource<List<BlockListedItem>>> {
        return blockListRepository.getBlackListedItems()
            .map { blockList ->
                Logger.debug(this@GetBlockedSourcesFlowUseCase, "BlockList: $blockList")
                val blockedSources =
                    blockList.filter { it.source?.isNotBlank() == true && it.author.isNullOrBlank() }
                if (blockedSources.isEmpty()) {
                    Resource.Error(EmptyBlockListException())
                } else {
                    Resource.Success(blockedSources)
                }
            }.flowOn(Dispatchers.Default)
    }
}