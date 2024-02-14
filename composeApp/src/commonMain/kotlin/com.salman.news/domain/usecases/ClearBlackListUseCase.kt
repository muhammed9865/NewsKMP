package com.salman.news.domain.usecases

import com.salman.news.domain.repository.BlockListRepository

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/14/2024.
 */
class ClearBlackListUseCase(
    private val blackListRepository: BlockListRepository,
) {

    suspend operator fun invoke() {
        blackListRepository.clearBlackList()
    }
}