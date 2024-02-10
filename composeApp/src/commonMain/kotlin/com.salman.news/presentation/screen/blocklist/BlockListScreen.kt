package com.salman.news.presentation.screen.blocklist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.salman.news.MR
import com.salman.news.domain.model.ArticleSource
import com.salman.news.presentation.LocalTopNavigator
import com.salman.news.presentation.composables.ContainerWithError
import com.salman.news.presentation.composables.NPrimaryButton
import com.salman.news.presentation.composables.NBasicTextField
import com.salman.news.presentation.composables.ScreenWithNavigationButton
import com.salman.news.presentation.model.ModelUtil
import com.salman.news.presentation.theme.Dimens
import dev.icerock.moko.resources.compose.stringResource

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/6/2024.
 */
class BlockListScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalTopNavigator.current
        val articles = ModelUtil.fakeArticles()
        val sources = articles.map { it.source }
        val authors = articles.map { it.author }

        ContainerWithError {
            ScreenWithNavigationButton(
                title = stringResource(MR.strings.block_list),
                onBackPressed = { navigator.pop() }
            ) {
                Column {
                    BlockedSourcesSection(
                        modifier = Modifier.fillMaxWidth()
                            .weight(1.5f),
                        sources = sources
                    )
                    Spacer(modifier = Modifier.weight(0.2f))
                    BlockedAuthorsSection(
                        modifier = Modifier.fillMaxWidth()
                            .weight(1.5f),
                        authors = authors
                    )
                    NPrimaryButton(
                        modifier = Modifier.fillMaxWidth()
                            .padding(Dimens.ScreenPadding),
                        text = stringResource(MR.strings.clear_block_list),
                        onClick = {}
                    )
                }
            }
        }
    }

    @Composable
    private fun BlockedSourcesSection(
        modifier: Modifier = Modifier,
        sources: List<ArticleSource>,
        onRemoveClicked: (ArticleSource) -> Unit = {},
    ) {
        var sourceQuery by remember { mutableStateOf("") }
        Container(
            modifier = modifier,
            title = stringResource(MR.strings.blocked_sources),
            searchQuery = sourceQuery,
            onSearchQueryChanged = { sourceQuery = it }
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(Dimens.ItemsPadding),
                contentPadding = PaddingValues(Dimens.ItemsPadding)
            ) {
                items(sources) { source ->
                    ItemContainer(
                        text = source.name,
                        onRemoveClicked = { onRemoveClicked(source) }
                    )
                }
            }
        }
    }

    @Composable
    private fun BlockedAuthorsSection(
        modifier: Modifier = Modifier,
        authors: List<String>,
        onRemoveClicked: (String) -> Unit = {},
    ) {
        var authorQuery by remember { mutableStateOf("") }
        Container(
            modifier = modifier,
            title = stringResource(MR.strings.blocked_authors),
            searchQuery = authorQuery,
            onSearchQueryChanged = { authorQuery = it }
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(Dimens.ItemsPadding),
                contentPadding = PaddingValues(Dimens.ItemsPadding)
            ) {
                items(authors) { author ->
                    ItemContainer(
                        text = author,
                        onRemoveClicked = { onRemoveClicked(author) }
                    )
                }
            }
        }
    }

    @Composable
    private fun ItemContainer(
        modifier: Modifier = Modifier,
        text: String,
        onRemoveClicked: () -> Unit = {},
    ) {
        val elevation = 4.dp
        Surface(
            modifier = modifier.fillMaxWidth()
                .shadow(elevation, MaterialTheme.shapes.medium)
                .clip(MaterialTheme.shapes.medium)
                .border(2.dp, color = MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.background)
                .padding(Dimens.ItemsPadding),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Text(
                    text = text,
                    modifier = Modifier.weight(1f)
                        .align(Alignment.CenterVertically),
                )
                Text(
                    text = "Remove",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .clickable { onRemoveClicked() }
                        .padding(4.dp)
                )
            }
        }
    }

    @Composable
    private fun Container(
        modifier: Modifier = Modifier,
        title: String = "",
        searchQuery: String = "",
        onSearchQueryChanged: (String) -> Unit = {},
        content: @Composable () -> Unit,
    ) {
        Column(modifier) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                // Implement search bar
                NBasicTextField(
                    modifier = Modifier.weight(1f),
                    text = searchQuery,
                    onTextChanged = onSearchQueryChanged
                )
            }
            Spacer(modifier = Modifier.height(Dimens.ItemsPadding))
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
                    .padding(horizontal = Dimens.ScreenPadding),
                contentAlignment = Alignment.Center,
            ) {
                content()
            }
        }
    }
}