package com.salman.news.presentation.screen.feedback

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.salman.news.MR
import com.salman.news.domain.exception.InvalidFeedbackMessageException
import com.salman.news.domain.exception.InvalidRatingException
import com.salman.news.presentation.LocalTopNavigator
import com.salman.news.presentation.composables.*
import com.salman.news.presentation.theme.Dimens
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/6/2024.
 */
class FeedbackScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalTopNavigator.current
        val viewModel: FeedbackViewModel = getScreenModel()
        val state by viewModel.state.collectAsState()
        ContainerWithError(
            error = state.error?.let { getErrorMessage(it) },
        ) {
            if (state.isSentSuccessfully) {
                SuccessDialog(
                    text = stringResource(MR.strings.thank_you_for_feedback),
                    onDismiss = { navigator.pop() }
                )
            }

            ScreenWithNavigationButton(
                title = stringResource(MR.strings.feedback),
                onBackPressed = { navigator.pop() },
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(Dimens.ItemsPadding)
                ) {
                    RatingSection(
                        rate = state.rating,
                        onRateChange = viewModel::onRatingChanged
                    )
                    MessageSection(
                        modifier = Modifier.weight(1f),
                        text = state.message,
                        onTextChange = viewModel::onMessageChanged
                    )
                    NPrimaryButton(
                        text = stringResource(MR.strings.submit_feedback),
                        isLoading = state.isSending,
                        enabled = state.isSubmissionEnabled
                    ) {
                        viewModel.sendFeedback()
                    }
                }
            }
        }
    }

    @Composable
    private fun getErrorMessage(throwable: Throwable): String {
        return when (throwable) {
            is InvalidRatingException -> stringResource(MR.strings.please_select_rating)
            is InvalidFeedbackMessageException -> stringResource(MR.strings.invalid_issue_message)
            else -> stringResource(MR.strings.something_went_wrong)
        }
    }

    @Composable
    private fun RatingSection(
        modifier: Modifier = Modifier,
        rate: Int,
        onRateChange: (Int) -> Unit,
    ) {
        Container(modifier = modifier) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Dimens.ItemsPadding)
            ) {
                val spannedString = buildAnnotatedString {
                    withStyle(
                        MaterialTheme.typography.bodyLarge.toSpanStyle()
                            .copy(color = Color.Red, fontWeight = FontWeight.Bold)
                    ) {
                        append("* ")
                    }
                    append(stringResource(MR.strings.rate_your_experience))
                }
                Text(
                    text = spannedString,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    val tint = when (rate) {
                        1 -> colorResource(MR.colors.star_rate_first)
                        2 -> colorResource(MR.colors.star_rate_second)
                        3 -> colorResource(MR.colors.star_rate_third)
                        4 -> colorResource(MR.colors.star_rate_fourth)
                        5 -> colorResource(MR.colors.star_rate_fifth)
                        else -> MaterialTheme.colorScheme.primary
                    }
                    val strokeTint = MaterialTheme.colorScheme.primary
                    repeat(5) {
                        val isSelected = it < rate
                        val actualRate = it + 1
                        val painter = if (isSelected) {
                            painterResource(MR.images.ic_star_filled)
                        } else {
                            painterResource(MR.images.ic_star_outlined)
                        }
                        DefaultIcon(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.medium)
                                .clickable { onRateChange(actualRate) }
                                .padding(4.dp),
                            tint = if (isSelected) tint else strokeTint,
                            painter = painter,
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun MessageSection(
        modifier: Modifier = Modifier,
        text: String,
        onTextChange: (String) -> Unit,
    ) {
        Container(modifier = modifier) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Dimens.ItemsPadding)
            ) {
                Text(
                    stringResource(MR.strings.tell_us_what_can_be_improved),
                    style = MaterialTheme.typography.bodyLarge,
                )
                TextField(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(2.dp, MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium),
                    value = text,
                    onValueChange = onTextChange,
                    shape = MaterialTheme.shapes.medium,
                    label = { Text(stringResource(MR.strings.tell_us_how_can_we_improve)) },
                    colors = NTextFieldDefaults,

                )
            }
        }
    }

    @Composable
    private fun Container(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit,
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
                .padding(Dimens.ScreenPadding),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}