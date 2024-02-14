package com.salman.news.domain.usecases

import com.salman.news.domain.model.BlockListedItem
import com.salman.news.domain.repository.ArticleRepository
import com.salman.news.domain.repository.BlockListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/10/2024.
 */
class RemoveItemFromBlockListUseCase(
    private val blockListRepository: BlockListRepository
) {

    suspend operator fun invoke(item: BlockListedItem): Result<Unit> {
        return withContext(Dispatchers.IO) {
            Result.success(blockListRepository.unblockItem(item.id))
        }
    }
}