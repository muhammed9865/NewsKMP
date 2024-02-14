package com.salman.news.data.mapper

import com.salman.news.data.source.local.entity.BlockListedEntity
import com.salman.news.domain.model.BlockListedItem
import comsalmannewsdata.table.BlockList

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/10/2024.
 */

fun BlockListedEntity.toDomain() = BlockListedItem(
    id,
    source,
    author
)

fun BlockList.toEntity() = BlockListedEntity(
    id,
    source_name,
    author
)