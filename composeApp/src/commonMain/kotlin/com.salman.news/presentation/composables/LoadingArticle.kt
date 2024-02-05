package com.salman.news.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.salman.news.presentation.theme.Dimens

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/5/2024.
 */
@Composable
fun LoadingArticlesList(modifier: Modifier = Modifier, itemsCount: Int = 5) {
    Column(modifier.fillMaxSize()) {
        Spacer(Modifier.height(Dimens.ItemsPadding))
        repeat(itemsCount) {
            LoadingArticleItem()
            Spacer(Modifier.height(Dimens.ArticleItemPadding))
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