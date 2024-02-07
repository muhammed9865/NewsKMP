package com.salman.news.presentation.screen.more

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import cafe.adriel.voyager.core.screen.Screen
import com.salman.news.MR
import com.salman.news.presentation.screen.blocklist.BlockListScreen
import com.salman.news.presentation.screen.feedback.FeedbackScreen
import com.salman.news.presentation.screen.language.LanguageSelectionScreen
import com.salman.news.presentation.screen.mode.ScreenModeScreen
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/6/2024.
 */
interface SettingScreen {
    @Composable
    fun title(): String
    @Composable
    fun icon(): Painter
    fun destination(): Screen

    companion object Screens {
        fun settings(): List<SettingScreen> {
            return listOf(
                BlockListSettingScreen,
                LanguageSettingScreen,
                ScreenModeSettingScreen,
                FeedbackSettingScreen
            )
        }
    }
}

data object BlockListSettingScreen: SettingScreen {
    @Composable
    override fun title(): String {
        return stringResource(MR.strings.block_list)
    }

    @Composable
    override fun icon(): Painter {
        return painterResource(MR.images.ic_block)
    }

    override fun destination(): Screen {
        return BlockListScreen()
    }
}

data object LanguageSettingScreen: SettingScreen {
    @Composable
    override fun title(): String {
        return stringResource(MR.strings.language)
    }

    @Composable
    override fun icon(): Painter {
        return painterResource(MR.images.ic_globe)
    }

    override fun destination(): Screen {
        return LanguageSelectionScreen()
    }
}

data object ScreenModeSettingScreen: SettingScreen {
    @Composable
    override fun title(): String {
        return stringResource(MR.strings.screen_mode)
    }

    @Composable
    override fun icon(): Painter {
        return painterResource(MR.images.ic_screen_mode)
    }

    override fun destination(): Screen {
        return ScreenModeScreen()
    }
}

data object FeedbackSettingScreen: SettingScreen {
    @Composable
    override fun title(): String {
        return stringResource(MR.strings.give_us_feedback)
    }

    @Composable
    override fun icon(): Painter {
        return painterResource(MR.images.ic_star_half_filled)
    }

    override fun destination(): Screen {
        return FeedbackScreen()
    }
}

