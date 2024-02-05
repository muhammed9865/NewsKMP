package com.salman.news.data.mapper

import com.salman.news.core.DateTimeUtil
import com.salman.news.core.ImageUrlGenerator
import com.salman.news.data.source.local.entity.ArticleEntity
import com.salman.news.data.source.local.entity.SourceEntity
import com.salman.news.data.source.remote.model.article.Article
import com.salman.news.domain.model.Article as DomainArticle
import com.salman.news.data.source.remote.model.article.Source
import com.salman.news.domain.model.ArticleSource

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/26/2024.
 */

fun ArticleEntity.toDTO() = kotlin.run {
    Article(
        author,
        content,
        description,
        publishedDate,
        source.toDTO(),
        title,
        articleUrl,
        imageUrl
    )
}

fun ArticleEntity.toDomain(): DomainArticle {
    val author = author ?: source.name
    return DomainArticle(
        id,
        title,
        description,
        content,
        DateTimeUtil.parseLocalDateTime(publishedDate),
        author,
        source.toDomain(),
        imageUrl ?: ImageUrlGenerator.get(),
        articleUrl,
        isSaved
    )
}

fun SourceEntity.toDTO(): Source {
    return Source(
        id, name
    )
}

fun SourceEntity.toDomain(): ArticleSource {
    return ArticleSource(id ?: name, name)
}