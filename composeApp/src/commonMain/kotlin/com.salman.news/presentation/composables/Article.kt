package com.salman.news.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
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
    val blurRadiusState by animateIntAsState(if (isMenuOpen) 4 else 0)
    val itemSize = DpSize(width = Dp.Infinity, height = 200.dp)

    Box(modifier.size(itemSize)) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clickable {
                    if (isMenuOpen) {
                        onOptionsMenuClicked()
                    } else onArticleClicked(articleUi)
                }
                .blur(blurRadiusState.dp)
                .padding(end = Dimens.ItemsPadding)
        ) {
            AsyncShimmerImage(model = article.imageUrl, size = DpSize(170.dp, 200.dp))
            Spacer(Modifier.width(Dimens.ItemsPadding))
            Column(verticalArrangement = Arrangement.SpaceAround) {
                ArticleTitleWithMenuButton(
                    title = article.title,
                    isMenuOpen = isMenuOpen,
                    onMenuClick = onOptionsMenuClicked
                )
                ArticleDescription(articleDescription = article.description) {
                    onArticleClicked(articleUi)
                }
                ArticlePublishDate(date = article.publishDate)
                Spacer(Modifier.weight(1f))
                ArticleLabelsWithBookmark(
                    isBookmarked = article.isSaved,
                    labels = listOf(article.author, article.source.name),
                    onBookmarkClicked = onBookmarkClicked
                )
            }
        }

        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(0.5f).align(Alignment.CenterEnd),
            visible = isMenuOpen,
            enter = slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween()
            ),
            exit = slideOutHorizontally(
                animationSpec = tween(),
                targetOffsetX = { it }
            )
        ) {
            OptionsMenu(
                onMuteAuthor = onMuteAuthor,
                onMuteSource = onMuteSource,
                onDismiss = { onOptionsMenuClicked() }
            )
        }
    }
}

@Composable
fun LoadingArticleItem(modifier: Modifier = Modifier) {
    val itemSize = DpSize(width = Dp.Infinity, height = 200.dp)
    val containerModifier = Modifier.fillMaxWidth()
        .clip(MaterialTheme.shapes.large)
    Row(
        modifier = modifier
            .size(itemSize)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(end = Dimens.ItemsPadding)
    ) {
        ShimmerContainer {
            AsyncShimmerImage(model = "null", size = DpSize(170.dp, 200.dp))
        }
        Spacer(Modifier.width(Dimens.ItemsPadding))
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            // Title
            ShimmerContainer(
                modifier = containerModifier.padding(30.dp),
                content = {}
            )
            // Description
            ShimmerContainer(
                modifier = containerModifier.padding(20.dp),
                content = {}
            )
            // Date
            ShimmerContainer(
                modifier = containerModifier.padding(10.dp),
                content = {}
            )

            // Labels
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ShimmerContainer(
                    modifier = containerModifier.padding(vertical = 10.dp, horizontal = 60.dp),
                    content = {}
                )
                ShimmerContainer(
                    modifier = containerModifier.padding(vertical = 10.dp, horizontal = 60.dp),
                    content = {}
                )
            }
        }
    }
}

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: List<ArticleUI>,
    onMuteAuthor: (ArticleUI) -> Unit = {},
    onMuteSource: (ArticleUI) -> Unit = {},
    onOptionsMenuClicked: (ArticleUI) -> Unit = {},
    onBookmarkClicked: (ArticleUI) -> Unit = {},
    onArticleClicked: (ArticleUI) -> Unit,
) {
    val state = rememberLazyListState()
    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = PaddingValues(vertical = Dimens.ItemsPadding),
        verticalArrangement = Arrangement.spacedBy(Dimens.ArticleItemPadding)
    ) {
        items(articles, key = { it.article.id }) { article ->
            ArticleItem(
                articleUi = article,
                onMuteAuthor = { onMuteAuthor(article) },
                onMuteSource = { onMuteSource(article) },
                onOptionsMenuClicked = { onOptionsMenuClicked(article) },
                onBookmarkClicked = { onBookmarkClicked(article) },
                onArticleClicked = onArticleClicked,
            )
        }
    }
}

@Composable
private fun ArticleTitleWithMenuButton(
    modifier: Modifier = Modifier,
    title: String,
    isMenuOpen: Boolean = false,
    onMenuClick: () -> Unit
) {
    val maxLines = 2
    val menuIcon = painterResource(MR.images.ic_menu_open)
    val rotationDegrees = if (isMenuOpen) 180F else 0F
    val animatedRotation by animateFloatAsState(rotationDegrees)
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
        Spacer(Modifier.width(3.dp))
        TonalIconButton(
            painter = menuIcon,
            modifier = Modifier
                .weight(1f)
                .padding(top = Dimens.ItemsPadding)
                .rotate(animatedRotation),
            onClick = onMenuClick
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

    val continueReading = stringResource(MR.strings.continue_reading)
    val continueReadingTag = "continue_reading"

    val annotatedDescription = buildAnnotatedString {
        withStyle(
            MaterialTheme.typography.bodyMedium.toSpanStyle()
        ) {
            append(description)
        }

        withStyle(
            MaterialTheme.typography.labelLarge
                .toSpanStyle()
                .copy(color = MaterialTheme.colorScheme.primary)
        ) {
            addStringAnnotation(
                continueReadingTag, continueReading,
                start = description.length + 1,
                end = description.length + continueReading.length
            )
            append(continueReading)
        }
    }

    ClickableText(
        modifier = modifier.fillMaxWidth(),
        text = annotatedDescription,
        maxLines = 4,
        onClick = {
            annotatedDescription
                .getStringAnnotations(continueReadingTag, it, it)
                .firstOrNull()
                ?.let { onContinueReadingClicked() }
        }
    )
}

@Composable
private fun ArticlePublishDate(
    modifier: Modifier = Modifier,
    date: LocalDateTime
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = DateTimeUtil.format(date),
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
private fun ArticleLabelsWithBookmark(
    modifier: Modifier = Modifier,
    isBookmarked: Boolean = false,
    labels: List<String>,
    onBookmarkClicked: () -> Unit = {},
) {
    val bookmarkIcon = if (isBookmarked)
        painterResource(MR.images.ic_bookmark_checked)
    else
        painterResource(MR.images.ic_bookmark_outlined)
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        labels.forEach {
            Label(
                modifier = Modifier.widthIn(min = 60.dp),
                label = it
            )
            Spacer(Modifier.width(8.dp))
        }
        Spacer(Modifier.weight(1f))
        DefaultIcon(
            modifier = Modifier
                .clickable { onBookmarkClicked() },
            painter = bookmarkIcon,
        )
    }
}

@Composable
private fun OptionsMenu(
    modifier: Modifier = Modifier,
    onMuteAuthor: () -> Unit = {},
    onMuteSource: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth(0.5f)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surface),
    ) {
        TonalIconButton(
            painter = painterResource(MR.images.ic_menu_open),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = Dimens.ItemsPadding, start = Dimens.ItemsPadding)
                .rotate(180f),
            onClick = onDismiss
        )
        Spacer(Modifier.weight(1f))
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .clickable { onMuteAuthor() }
                .padding(PaddingValues(start = Dimens.ItemsPadding, top = 5.dp))
        ) {
            Text(
                text = stringResource(MR.strings.mute_author),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(Modifier.height(2.dp))
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .clickable { onMuteSource() }
                .padding(PaddingValues(start = Dimens.ItemsPadding, top = 5.dp))
        ) {
            Text(
                text = stringResource(MR.strings.mute_source),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}