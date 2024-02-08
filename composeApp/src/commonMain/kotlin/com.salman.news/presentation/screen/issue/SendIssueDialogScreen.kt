package com.salman.news.presentation.screen.issue

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.salman.news.MR
import com.salman.news.domain.exception.InvalidEmailException
import com.salman.news.domain.exception.InvalidIssueMessageException
import com.salman.news.presentation.composables.ContainerWithError
import com.salman.news.presentation.composables.Header
import com.salman.news.presentation.composables.NPrimaryButton
import com.salman.news.presentation.composables.SuccessDialog
import com.salman.news.presentation.screen.dialogScreenModifier
import com.salman.news.presentation.theme.Dimens
import dev.icerock.moko.resources.compose.stringResource

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/7/2024.
 */
class SendIssueDialogScreen(
    private val onDismiss: () -> Unit = {}
) : Screen {

    @Composable
    override fun Content() {
        val viewModel: SendIssueViewModel = getScreenModel()
        val state by viewModel.state.collectAsState()
        if (state.isSent) {
            SuccessDialog(
                text = stringResource(MR.strings.issue_sent_successfully),
                onDismiss = { onDismiss(); viewModel.onDispose() }
            )
        } else {
            Dialog(
                onDismissRequest = { onDismiss(); viewModel.onDispose() }
            ) {
                val error = when (state.error) {
                    null -> null
                    is InvalidEmailException -> stringResource(MR.strings.invalid_email)
                    is InvalidIssueMessageException -> stringResource(MR.strings.invalid_issue_message)
                    else -> stringResource(MR.strings.something_went_wrong)
                }

                ContainerWithError(
                    error = error,
                    modifier = Modifier.clip(MaterialTheme.shapes.medium)
                ) {
                    FormSection(
                        state = state,
                        onEmailChange = { viewModel.onEmailChange(it) },
                        onMessageChange = { viewModel.onMessageChange(it) },
                        onSend = { viewModel.onSendIssue() }
                    )
                }
            }
        }
    }

    @Composable
    private fun FormSection(
        modifier: Modifier = Modifier,
        state: SendIssueState,
        onEmailChange: (String) -> Unit,
        onMessageChange: (String) -> Unit,
        onSend: () -> Unit
    ) {
        Column(
            modifier = modifier.dialogScreenModifier(),
            verticalArrangement = Arrangement.spacedBy(Dimens.ItemsPadding),
        ) {
            HeaderSection()
            OutlinedTextField(
                value = state.email,
                onValueChange = { onEmailChange(it) },
                label = { Text(stringResource(MR.strings.email)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )
            OutlinedTextField(
                value = state.message,
                onValueChange = { onMessageChange(it) },
                label = { Text(stringResource(MR.strings.what_is_the_issue)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Default,
                    keyboardType = KeyboardType.Text
                ),
                maxLines = 5,
                minLines = 5,
            )
            NPrimaryButton(
                text = stringResource(MR.strings.send_issue),
                onClick = { onSend() },
                isLoading = state.isSending
            )
        }
    }

    @Composable
    private fun HeaderSection(modifier: Modifier = Modifier) {
        Column(modifier = modifier) {
            Header(header = stringResource(MR.strings.having_an_issue))
            Text(stringResource(MR.strings.we_will_get_back_to_you))
        }
    }
}