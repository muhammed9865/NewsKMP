package com.salman.news.presentation.screen.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.salman.news.MR
import com.salman.news.domain.model.SearchTimeFrame
import com.salman.news.domain.model.Suggestion
import com.salman.news.domain.model.SuggestionsGroup
import com.salman.news.presentation.LocalTopNavigator
import com.salman.news.presentation.common.extensions.dateStringResource
import com.salman.news.presentation.common.extensions.stringResource
import com.salman.news.presentation.composables.ContainerWithError
import com.salman.news.presentation.composables.IndexedColorfulContainer
import com.salman.news.presentation.composables.NTextFieldDefaults
import com.salman.news.presentation.composables.ScreenWithTopBar
import com.salman.news.presentation.screen.search_result.SearchResultScreen
import com.salman.news.presentation.theme.Dimens
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */
class SearchScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalTopNavigator.current
        val viewModel: SearchViewModel = getScreenModel()
        val state by viewModel.state.collectAsState()
        ContainerWithError(
            error = state.error?.stringResource?.let { stringResource(it) },
        ) {
            ScreenWithTopBar(
                title = stringResource(MR.strings.search),
                onBackPressed = { navigator.pop() }
            ) {
                Column {
                    SearchContent(
                        query = state.query,
                        selectedTimeFrame = state.selectedTimeFrame,
                        suggestions = state.suggestions,
                        isSearching = state.isLoading,
                        onQueryChanged = viewModel::onQueryChanged,
                        onSearchClicked = { navigator.push(SearchResultScreen(state.query)) },
                        onSelectedTimeFrameChanged = viewModel::onTimeFrameSelected,
                    )
                }
            }
        }
    }

    @Composable
    private fun SearchContent(
        modifier: Modifier = Modifier,
        query: String = "",
        isSearching: Boolean = false,
        selectedTimeFrame: SearchTimeFrame = SearchTimeFrame.Default,
        suggestions: List<SuggestionsGroup> = emptyList(),
        onSuggestionClicked: (Suggestion) -> Unit = {},
        onQueryChanged: (String) -> Unit = {},
        onSearchClicked: () -> Unit = {},
        onSelectedTimeFrameChanged: (SearchTimeFrame) -> Unit = {},
    ) {
        var searchBarHeight by remember { mutableStateOf(0) }
        Box(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Column {
                Column {
                    SearchBar(
                        query = query,
                        onQueryChanged = onQueryChanged,
                        onSearchClicked = onSearchClicked,
                        isSearching = isSearching,
                        modifier = Modifier.onPlaced {
                            searchBarHeight = it.size.height
                        }
                    )
                    AnimatedVisibility(
                        visible = suggestions.isEmpty(),
                        enter = expandVertically(),
                        exit = shrinkVertically(),
                    ) {
                        TimeFrameSelection(
                            modifier = Modifier.padding(horizontal = Dimens.ItemsPadding),
                            selectedTimeFrame = selectedTimeFrame,
                            onSelectedTimeFrameChanged = onSelectedTimeFrameChanged
                        )
                    }
                }

                AnimatedVisibility(
                    visible = suggestions.isNotEmpty(),
                    enter = expandVertically(),
                    exit = shrinkVertically(),
                ) {
                    SuggestionsList(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.7f)
                            .padding(horizontal = Dimens.ItemsPadding),
                        suggestions = suggestions,
                        onSuggestionClicked = onSuggestionClicked,
                        headerPadding = PaddingValues(
                            start = Dimens.ItemsPadding,
                            end = Dimens.ItemsPadding
                        )
                    )
                }
            }
        }
    }

    @Composable
    private fun SearchBar(
        modifier: Modifier = Modifier,
        query: String = "",
        isSearching: Boolean = false,
        onQueryChanged: (String) -> Unit = {},
        onSearchClicked: () -> Unit = {},
    ) {
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.medium
                ).shadow(4.dp, clip = true, shape = MaterialTheme.shapes.medium),
            value = query,
            onValueChange = onQueryChanged,
            shape = MaterialTheme.shapes.medium,
            label = if (query.isBlank()) {
                {
                    Text(stringResource(MR.strings.what_are_you_looking_for))
                }
            } else null,
            colors = NTextFieldDefaults,
            trailingIcon = {
                if (isSearching) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else
                    IconButton(onSearchClicked) {
                        Icon(
                            painter = painterResource(MR.images.ic_search),
                            contentDescription = null
                        )
                    }
            }
        )
    }

    @Composable
    private fun TimeFrameSelection(
        modifier: Modifier = Modifier,
        selectedTimeFrame: SearchTimeFrame = SearchTimeFrame.Default,
        onSelectedTimeFrameChanged: (SearchTimeFrame) -> Unit,
    ) {
        val shape = MaterialTheme.shapes.medium.copy(
            topStart = CornerSize(0.dp),
            topEnd = CornerSize(0.dp)
        )
        Row(
            modifier = modifier.fillMaxWidth()
                .clip(shape)
                .border(2.dp, MaterialTheme.colorScheme.surface, shape)
        ) {
            SearchTimeFrame.values().forEach {
                val isSelected = it == selectedTimeFrame
                val backgroundColor = if (isSelected) {
                    MaterialTheme.colorScheme.surface
                } else {
                    MaterialTheme.colorScheme.background
                }
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(backgroundColor)
                        .clickable { onSelectedTimeFrameChanged(it) }
                        .padding(4.dp),
                    text = stringResource(it.dateStringResource),
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    },
                    textAlign = TextAlign.Center,
                )

            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun SuggestionsList(
        modifier: Modifier = Modifier,
        headerPadding: PaddingValues = PaddingValues(0.dp),
        suggestions: List<SuggestionsGroup>,
        onSuggestionClicked: (Suggestion) -> Unit,
    ) {
        val bottomRoundedShape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
        LazyColumn(
            modifier = modifier.fillMaxWidth()
                .clip(bottomRoundedShape)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.surface,
                    shape = bottomRoundedShape,
                ),
        ) {
            suggestions.forEach {
                val header = it.date
                stickyHeader(
                    contentType = "header"
                ) {
                    Text(
                        text = header,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(headerPadding)
                    )
                }
                items(it.suggestions.size) { index ->
                    val suggestion = it.suggestions[index]
                    IndexedColorfulContainer(
                        modifier = Modifier.fillMaxWidth()
                            .clickable { onSuggestionClicked(suggestion) },
                        index = index
                    ) {
                        Text(
                            text = suggestion.title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Dimens.ItemsPadding)
                        )
                    }
                }
            }
        }
    }
}