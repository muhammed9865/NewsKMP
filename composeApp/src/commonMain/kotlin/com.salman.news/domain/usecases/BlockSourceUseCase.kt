package com.salman.news.domain.usecases

import com.salman.news.domain.model.ArticleSource
import com.salman.news.domain.repository.BlockListRepository

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/14/2024.
 */
class BlockSourceUseCase(
    private val blockListRepository: BlockListRepository
) {

    suspend operator fun invoke(source: ArticleSource) {
        blockListRepository.blockSource(source)
    }
}