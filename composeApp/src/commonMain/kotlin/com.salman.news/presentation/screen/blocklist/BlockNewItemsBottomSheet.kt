package com.salman.news.presentation.screen.blocklist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.salman.news.MR
import com.salman.news.core.Resource
import com.salman.news.domain.model.ArticleAuthor
import com.salman.news.domain.model.ArticleSource
import com.salman.news.presentation.composables.IndexedColorfulContainer
import com.salman.news.presentation.composables.ShimmerContainer
import com.salman.news.presentation.theme.Dimens
import dev.icerock.moko.resources.compose.stringResource

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/14/2024.
 */
class BlockNewItemsBottomSheet {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    fun Content(
        modifier: Modifier = Modifier,
        sources: Resource<List<ArticleSource>>,
        authors: Resource<List<ArticleAuthor>>,
        onDismiss: () -> Unit = {},
        onAuthorItemClicked: (ArticleAuthor) -> Unit,
        onSourceItemClicked: (ArticleSource) -> Unit,
    ) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = onDismiss,
            containerColor = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                SectionContainer(
                    modifier = Modifier.fillMaxHeight(0.4f),
                    title = stringResource(MR.strings.sources),
                    items = sources,
                ) {
                    itemsIndexed(it, key = { _, item -> item.name }) { index, item ->
                        ItemContainer(
                            modifier = Modifier.animateItemPlacement(),
                            index = index,
                            item = item.name,
                            onAddItemClicked = { onSourceItemClicked(item) }
                        )
                    }
                }
                Divider(
                    modifier = Modifier.fillMaxWidth()
                )
                SectionContainer(
                    modifier = Modifier.fillMaxHeight(0.6f),
                    title = stringResource(MR.strings.authors),
                    items = authors,
                ) {
                    itemsIndexed(it, key = { _, item -> item.name }) { index, item ->
                        ItemContainer(
                            modifier = Modifier.animateItemPlacement(),
                            index = index,
                            item = item.name,
                            addPaddingToLastItem = index == it.size - 1,
                            onAddItemClicked = { onAuthorItemClicked(item) }
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun <T> SectionContainer(
        modifier: Modifier = Modifier,
        title: String,
        items: Resource<List<T>>,
        onSuccess: LazyListScope.(List<T>) -> Unit
    ) {
        Column(modifier = modifier) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = Dimens.ScreenPadding)
            )
            when (items) {
                is Resource.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = Dimens.ItemsPadding),
                    ) {
                        onSuccess(items.data)
                    }
                }

                is Resource.Error -> {
                    Text(
                        text = stringResource(MR.strings.something_went_wrong),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {
                    repeat(3) {
                        LoadingItemContainer(index = it)
                    }
                }
            }
        }
    }

    @Composable
    private fun LoadingItemContainer(modifier: Modifier = Modifier, index: Int) {
        val containerColor = if (index.isOdd()) {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
        } else {
            MaterialTheme.colorScheme.background
        }
        ShimmerContainer {
            Box(
                modifier
                    .fillMaxWidth()
                    .background(containerColor)
                    .padding(32.dp),
            )
        }
    }

    @Composable
    private fun ItemContainer(
        modifier: Modifier = Modifier,
        index: Int,
        item: String,
        addPaddingToLastItem: Boolean = false,
        onAddItemClicked: () -> Unit
    ) {
        IndexedColorfulContainer(
            modifier = modifier.fillMaxWidth(),
            index = index
        ) {
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable { onAddItemClicked() }
                    .padding(Dimens.ItemsPadding),
                text = item,
            )
            if (addPaddingToLastItem) {
                Spacer(modifier = Modifier.height(Dimens.ItemsPadding))
            }
        }
    }


    private fun Int.isOdd() = this % 2 != 0
}