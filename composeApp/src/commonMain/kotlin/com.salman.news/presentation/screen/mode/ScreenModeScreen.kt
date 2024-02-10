package com.salman.news.presentation.screen.mode

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.salman.news.MR
import com.salman.news.domain.model.ScreenMode
import com.salman.news.presentation.LocalTopNavigator
import com.salman.news.presentation.composables.ContainerWithError
import com.salman.news.presentation.composables.NPrimaryButton
import com.salman.news.presentation.composables.ScreenWithNavigationButton
import com.salman.news.presentation.composables.SuccessDialog
import com.salman.news.presentation.theme.DarkColorScheme
import com.salman.news.presentation.theme.Dimens
import com.salman.news.presentation.theme.LightColorScheme
import dev.icerock.moko.resources.compose.stringResource

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/6/2024.
 */
class ScreenModeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalTopNavigator.current
        val viewModel: ScreenModeViewModel = getScreenModel()
        val state by viewModel.state.collectAsState()

        ContainerWithError(error = state.error?.message) {
            ScreenWithNavigationButton(
                title = stringResource(MR.strings.screen_mode),
                onBackPressed = { navigator.pop() }
            ) {
                if (state.isSaved) {
                    SuccessDialog(text = stringResource(MR.strings.screen_mode_changed)) {
                        viewModel.hideSuccessMessage()
                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(Dimens.ItemsPadding)
                ) {
                    ModeContainer(
                        modifier = Modifier.weight(1f),
                        isSelected = state.selectedMode == ScreenMode.SYSTEM,
                        screenMode = stringResource(MR.strings.screen_mode_system),
                        onClick = { viewModel.onModeSelected(ScreenMode.SYSTEM) }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            ModeContent(modifier = Modifier.fillMaxHeight().weight(1f), colorScheme = LightColorScheme)
                            ModeContent(modifier = Modifier.fillMaxHeight().weight(1f), colorScheme = DarkColorScheme)
                        }
                    }
                    ModeContainer(
                        modifier = Modifier.weight(1f),
                        isSelected = state.selectedMode == ScreenMode.LIGHT,
                        screenMode = stringResource(MR.strings.screen_mode_light),
                        onClick = { viewModel.onModeSelected(ScreenMode.LIGHT) }
                    ) {
                        ModeContent(colorScheme = LightColorScheme)
                    }
                    ModeContainer(
                        modifier = Modifier.weight(1f),
                        isSelected = state.selectedMode == ScreenMode.DARK,
                        screenMode = stringResource(MR.strings.screen_mode_dark),
                        onClick = { viewModel.onModeSelected(ScreenMode.DARK) }
                    ) {
                        ModeContent(colorScheme = DarkColorScheme)
                    }
                    Spacer(Modifier.weight(1.2f))
                    NPrimaryButton(
                        text = stringResource(MR.strings.save),
                        isLoading = state.isSaving,
                        enabled = state.isSettingModeEnabled
                    ) {
                        viewModel.saveMode()
                    }
                }
            }
        }
    }

    @Composable
    private fun ModeContainer(
        modifier: Modifier = Modifier,
        screenMode: String,
        isSelected: Boolean = false,
        onClick: () -> Unit = {},
        content: @Composable () -> Unit,
    ) {
        val elevation = if (isSelected) 4.dp else 0.dp
        val sizeScale by animateFloatAsState(if (isSelected) 1.05f else 1f)
        val borderColor = if (isSelected) Color.Green else MaterialTheme.colorScheme.surface
        Column(modifier) {
            Text(screenMode, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.height(Dimens.ItemsPadding))

            Surface(
                modifier = Modifier.fillMaxSize()
                    .scale(sizeScale)
                    .shadow(elevation, MaterialTheme.shapes.medium)
                    .clip(MaterialTheme.shapes.medium)
                    .border(2.dp, color = borderColor, shape = MaterialTheme.shapes.medium)
                    .clickable { onClick() },
            ) {
                content()
            }
        }
    }

    @Composable
    private fun ModeContent(
        modifier: Modifier = Modifier,
        colorScheme: ColorScheme,
    ) {
        val shape = MaterialTheme.shapes.medium.copy(
            topEnd = CornerSize(0.dp),
            bottomEnd = CornerSize(0.dp),
            bottomStart = CornerSize(0.dp),
        )
        with(colorScheme) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(background),
                contentAlignment = Alignment.BottomEnd,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .fillMaxHeight(0.5f)
                        .clip(shape)
                        .border(width = 2.dp, color = surface, shape = shape)
                        .background(primaryContainer)
                        .padding(8.dp),
                ) {
                    Text(
                        text = stringResource(MR.strings.screen_mode_Aa),
                        color = onPrimaryContainer,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}