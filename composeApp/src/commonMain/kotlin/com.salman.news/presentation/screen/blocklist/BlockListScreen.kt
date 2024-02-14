package com.salman.news.presentation.screen.blocklist

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.salman.news.MR
import com.salman.news.core.Resource
import com.salman.news.domain.model.BlockListedItem
import com.salman.news.presentation.LocalTopNavigator
import com.salman.news.presentation.composables.ContainerWithError
import com.salman.news.presentation.composables.NPrimaryButton
import com.salman.news.presentation.composables.ScreenWithTopBar
import com.salman.news.presentation.composables.ShimmerContainer
import com.salman.news.presentation.composables.TonalIconButton
import com.salman.news.presentation.theme.Dimens
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/6/2024.
 */
class BlockListScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalTopNavigator.current
        val viewModel: BlockListViewModel = getScreenModel()
        val state by viewModel.state.collectAsState()

        ContainerWithError {
            ScreenWithTopBar(
                title = stringResource(MR.strings.block_list),
                onBackPressed = { navigator.pop() },
                actions = {
                    TonalIconButton(
                        painter = painterResource(MR.images.ic_add),
                        onClick = viewModel::toggleBlockNewItems
                    )
                }
            ) {
                if (state.showBlockNewItems) {
                    BlockNewItemsBottomSheet().Content(
                        onDismiss = viewModel::toggleBlockNewItems,
                        onAuthorItemClicked = viewModel::blockAuthor,
                        onSourceItemClicked = viewModel::blockSource,
                        sources = state.allSources,
                        authors = state.allAuthors,
                    )
                }
                Column(
                    modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                ) {
                    BlockedSourcesSection(
                        modifier = Modifier.fillMaxWidth()
                            .weight(1.5f),
                        sources = state.blockedSources,
                        onRemoveClicked = viewModel::removeItem
                    )
                    Spacer(modifier = Modifier.weight(0.2f))
                    BlockedAuthorsSection(
                        modifier = Modifier.fillMaxWidth()
                            .weight(1.5f),
                        authors = state.blockedAuthors,
                        onRemoveClicked = viewModel::removeItem
                    )
                    Spacer(modifier = Modifier.weight(0.2f))
                    NPrimaryButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(MR.strings.clear_block_list),
                        onClick = viewModel::clearBlackList
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun BlockedSourcesSection(
        modifier: Modifier = Modifier,
        sources: Resource<List<BlockListedItem>>,
        onRemoveClicked: (BlockListedItem) -> Unit = {},
    ) {
        Container(
            modifier = modifier,
            title = stringResource(MR.strings.blocked_sources),
        ) {
            when (sources) {
                is Resource.Success -> {
                    val sourcesList = sources.data
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(Dimens.ItemsPadding),
                        contentPadding = PaddingValues(Dimens.ItemsPadding)
                    ) {
                        items(sourcesList, key = { it.id }) { source ->
                            BlockedItemContainer(
                                modifier = Modifier.animateItemPlacement(),
                                text = source.source!!,
                                onRemoveClicked = { onRemoveClicked(source) }
                            )
                        }
                    }
                }

                is Resource.Loading -> {
                    LoadingItemsContainer()
                }

                is Resource.Error -> {
                    EmptyListContainer(stringResource(MR.strings.you_dont_have_blocked_sources))
                }

                else -> {}
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun BlockedAuthorsSection(
        modifier: Modifier = Modifier,
        authors: Resource<List<BlockListedItem>>,
        onRemoveClicked: (BlockListedItem) -> Unit = {},
    ) {
        Container(
            modifier = modifier,
            title = stringResource(MR.strings.blocked_authors),
        ) {
            when (authors) {
                is Resource.Success -> {
                    val authorsList = authors.data
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(Dimens.ItemsPadding),
                        contentPadding = PaddingValues(Dimens.ItemsPadding)
                    ) {
                        items(authorsList, key = { it.id }) { author ->
                            BlockedItemContainer(
                                modifier = Modifier.animateItemPlacement(),
                                text = author.author!!,
                                onRemoveClicked = { onRemoveClicked(author) }
                            )
                        }
                    }
                }

                is Resource.Loading -> {
                    LoadingItemsContainer()
                }

                is Resource.Error -> {
                    EmptyListContainer(stringResource(MR.strings.you_dont_have_blocked_authors))
                }

                else -> {}
            }

        }
    }



    @Composable
    private fun EmptyListContainer(message: String, modifier: Modifier = Modifier) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(message, color = MaterialTheme.colorScheme.onBackground)
        }
    }

    @Composable
    private fun LoadingItemsContainer(modifier: Modifier = Modifier) {
        val items = 5
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(Dimens.ItemsPadding)
        ) {
            repeat(items) {
                ShimmerContainer(
                    modifier = Modifier.fillMaxWidth()
                        .padding(Dimens.ItemsPadding)
                        .clip(MaterialTheme.shapes.medium)
                ) {
                    Surface(
                        content = {},
                        modifier = Modifier.fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                            .border(
                                2.dp,
                                color = MaterialTheme.colorScheme.surface,
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(24.dp),
                    )
                }
            }
        }
    }

    @Composable
    private fun BlockedItemContainer(
        modifier: Modifier = Modifier,
        text: String,
        onRemoveClicked: () -> Unit = {},
    ) {
        val elevation = 4.dp
        Surface(
            modifier = modifier.fillMaxWidth()
                .shadow(elevation, MaterialTheme.shapes.medium)
                .clip(MaterialTheme.shapes.medium)
                .border(
                    2.dp,
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.medium
                )
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
                    text = stringResource(MR.strings.remove),
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
            }
            Spacer(modifier = Modifier.height(Dimens.ItemsPadding))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
                    .animateContentSize(),
            ) {
                content()
            }
        }
    }
}