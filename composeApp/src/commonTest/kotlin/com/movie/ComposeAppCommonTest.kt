package com.movie

import androidx.compose.ui.test.*
import com.movie.core.design.theme.MovieTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class ComposeAppCommonTest {

    @Test
    fun app_startsWithMoviesScreen() = runComposeUiTest {
        // When
        setContent {
            App()
        }
        
        // Then - verify movies screen is displayed
        onNodeWithText("Movies").assertIsDisplayed()
        onNodeWithText("Search movies...").assertIsDisplayed()
    }
    
    @Test
    fun app_displaysThemeElements() = runComposeUiTest {
        // When
        setContent {
            App()
        }
        
        // Then - verify theme is applied (top bar exists)
        onNode(hasText("Movies")).assertIsDisplayed()
    }
}