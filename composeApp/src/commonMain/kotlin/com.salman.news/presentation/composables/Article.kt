package com.salman.news.presentation.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.salman.news.MR
import com.salman.news.core.DateTimeUtil
import com.salman.news.presentation.model.ArticleUI
import com.salman.news.presentation.theme.Dimens
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.datetime.LocalDateTime

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/28/2024.
 */
@Composable
fun ArticleItem(
    modifier: Modifier = Modifier,
    articleUi: ArticleUI,
    onMuteAuthor: () -> Unit = {},
    onMuteSource: () -> Unit = {},
    onOptionsMenuClicked: () -> Unit = {},
    onBookmarkClicked: () -> Unit = {},
    onArticleClicked: (ArticleUI) -> Unit,
) {
    val article = articleUi.article
    val isMenuOpen = articleUi.isOptionsMenuOpen

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(2.dp)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clickable {
                    if (isMenuOpen) {
                        onOptionsMenuClicked()
                    } else onArticleClicked(articleUi)
                }
        ) {
            AsyncShimmerImage(
                model = article.imageUrl,
                size = DpSize(Dp.Infinity, 250.dp),
                contentScale = ContentScale.FillBounds,
            )
            Column(
                modifier = Modifier.padding(horizontal = 25.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ArticleTitleWithMenuButton(
                    title = article.title,
                )
                ArticleDescription(articleDescription = article.description) {
                    onArticleClicked(articleUi)
                }
                ArticleFooterSection(
                    modifier = Modifier.padding(top = 8.dp),
                    author = article.author,
                    isBookmarked = article.isSaved,
                    publishDate = article.publishDate,
                    onBookmarkClicked = onBookmarkClicked,
                    onMuteAuthor = onMuteAuthor,
                )
            }
        }
    }
}

@Composable
private fun ArticleFooterSection(
    modifier: Modifier = Modifier,
    author: String,
    publishDate: LocalDateTime,
    isBookmarked: Boolean = false,
    onBookmarkClicked: () -> Unit = {},
    onMuteAuthor: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            painterResource(MR.images.ic_avatar),
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.width(8.dp))
        Column {
            Text(
                text = author,
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF1F1F1F)
            )
            Text(
                text = DateTimeUtil.format(publishDate),
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF1F1F1F)
            )
        }
        Spacer(Modifier.weight(1f))
        val (bookmarkIcon, alpha) = if (isBookmarked) {
            MR.images.ic_bookmark_checked to 1f
        } else {
            MR.images.ic_bookmark_outlined to 0.5f
        }
        val iconSize = 22.dp
        DefaultIcon(
            modifier = Modifier.size(iconSize),
            painter = painterResource(MR.images.ic_block),
            onClick = onMuteAuthor
        )

        DefaultIcon(
            modifier = Modifier.size(22.dp)
                .alpha(alpha),
            painter = painterResource(bookmarkIcon),
            onClick = onBookmarkClicked
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: List<ArticleUI>,
    isLoadingMoreArticles: Boolean = false,
    onMuteAuthor: (ArticleUI, Int) -> Unit = { _, _ -> },
    onMuteSource: (ArticleUI, Int) -> Unit = { _, _ -> },
    onOptionsMenuClicked: (ArticleUI, Int) -> Unit = { _, _ -> },
    onBookmarkClicked: (ArticleUI, Int) -> Unit = { _, _ -> },
    onArticleClicked: (ArticleUI, Int) -> Unit
) {
    val state = rememberLazyListState()
    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = PaddingValues(vertical = Dimens.ItemsPadding),
        verticalArrangement = Arrangement.spacedBy(Dimens.ArticleItemPadding)
    ) {
        itemsIndexed(articles, key = { _, it -> it.article.id }) { index, article ->
            ArticleItem(
                modifier = Modifier.animateItemPlacement(),
                articleUi = article,
                onMuteAuthor = { onMuteAuthor(article, index) },
                onMuteSource = { onMuteSource(article, index) },
                onOptionsMenuClicked = { onOptionsMenuClicked(article, index) },
                onBookmarkClicked = { onBookmarkClicked(article, index) },
                onArticleClicked = { onArticleClicked(article, index) },
            )
        }

        if (isLoadingMoreArticles) {
            item {
                LoadingArticlesList(itemsCount = 2)
            }
        }
    }
}

@Composable
private fun ArticleTitleWithMenuButton(
    modifier: Modifier = Modifier,
    title: String,
) {
    val maxLines = 2
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.weight(4f),
            text = title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ArticleDescription(
    modifier: Modifier = Modifier,
    articleDescription: String?,
    onContinueReadingClicked: () -> Unit
) {
    val maxCharacters = 110
    val description = (articleDescription ?: stringResource(MR.strings.description_not_available))
        .take(maxCharacters)
        .plus("... ")

    val annotatedDescription = buildAnnotatedString {
        withStyle(
            MaterialTheme.typography.bodyMedium.toSpanStyle().copy(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            )
        ) {
            append(description)
        }
    }

    ClickableText(
        modifier = modifier.fillMaxWidth(),
        text = annotatedDescription,
        maxLines = 4,
        onClick = { onContinueReadingClicked() }
    )
}