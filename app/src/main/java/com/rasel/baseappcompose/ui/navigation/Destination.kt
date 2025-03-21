/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rasel.baseappcompose.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.ui.graphics.vector.ImageVector
import com.rasel.baseappcompose.R
import kotlinx.serialization.Serializable

data class AppTopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
)

@Serializable
object Start

@Serializable
object Flavor

@Serializable
object Pickup

@Serializable
object MovieDetails

@Serializable
object AnimationList

@Serializable
object ShowHideAnimation

@Serializable
object ValueBasedAnimation

@Serializable
object LandingScreen

@Serializable
object Summary

@Serializable
object Inbox

@Serializable
object Articles

@Serializable
object Dm

@Serializable
object Groups

@Serializable
object JetNews

@Serializable
object CupCake

@Serializable
object Feed

@Serializable
object ArticleDetails

@Serializable
object HomeRoute


object AppRoute {
    const val START = "BaseAppCompose"
    const val Flavor = "Choose_Flavor"
    const val Pickup = "Choose_Pickup_Date"
    const val MOVIE_DETAILS = "Movie Details"
    const val PAGING_3 = "PAGING_3"
    const val ANIMATION_LIST = "Animation List"
    const val SHOW_HIDE_ANIMATION = "Show Hide Animation"
    const val VALUE_BASED_ANIMATION = "Value Based Animation"
    const val LANDING_SCREEN = "Landing Screen"
    const val Summary = "Order Summary"
    const val INBOX = "Inbox"
    const val ARTICLES = "Articles"
    const val JET_NEWS = "jet_news"
    const val CUP_CAKE = "cup_cake"
    const val HOME_ROUTE = "home"
    const val INTERESTS_ROUTE = "interests"
}
val TOP_LEVEL_DESTINATIONS = listOf(
    AppTopLevelDestination(
        route = AppRoute.INBOX,
        selectedIcon = Icons.Default.Inbox,
        unselectedIcon = Icons.Default.Inbox,
        iconTextId = R.string.tab_inbox
    ),
    AppTopLevelDestination(
        route = AppRoute.ARTICLES,
        selectedIcon = Icons.AutoMirrored.Filled.Article,
        unselectedIcon = Icons.AutoMirrored.Filled.Article,
        iconTextId = R.string.tab_article
    ),
    AppTopLevelDestination(
        route = AppRoute.CUP_CAKE,
        selectedIcon = Icons.Outlined.ChatBubbleOutline,
        unselectedIcon = Icons.Outlined.ChatBubbleOutline,
        iconTextId = R.string.tab_inbox
    ),
    AppTopLevelDestination(
        route = AppRoute.JET_NEWS,
        selectedIcon = Icons.Default.People,
        unselectedIcon = Icons.Default.People,
        iconTextId = R.string.tab_article
    )

)

enum class Destination(val route: String, val title: String) {
    BrushExamples("brushExamples", "Brush Examples"),
    ImageExamples("imageExamples", "Image Examples"),
    AnimationQuickGuideExamples("animationExamples", "Animation Examples"),
    ComponentsExamples("topComponents", "Top Compose Components"),
    ScreenshotExample("screenshotExample", "Screenshot Examples"),
    ShapesExamples("shapesExamples", "Shapes Examples"),
    SharedElementExamples("sharedElement", "Shared elements")
}

// Enum class for compose components navigation screen.
enum class TopComponentsDestination(val route: String, val title: String) {
    CardExamples("cardExamples", "Card"),
    SwitchExamples("switchExamples", "Switch"),
    SliderExamples("sliderExamples", "Slider"),
    DialogExamples("dialogExamples", "Dialog"),
    ChipExamples("chipExamples", "Chip"),
    FloatingActionButtonExamples("floatingActionButtonExamples", "Floating Action Button"),
    ButtonExamples("buttonExamples", "Button"),
    ProgressIndicatorExamples("progressIndicatorExamples", "Progress Indicators"),
    ScaffoldExample("scaffoldExample", "Scaffold"),
    AppBarExamples("appBarExamples", "App bars"),
    CheckboxExamples("checkboxExamples", "Checkbox"),
    DividerExamples("dividerExamples", "Dividers"),
    BadgeExamples("badgeExamples", "Badges"),
    PartialBottomSheet("partialBottomSheets", "Partial Bottom Sheet"),
    TimePickerExamples("timePickerExamples", "Time Pickers"),
    DatePickerExamples("datePickerExamples", "Date Pickers"),
    CarouselExamples("carouselExamples", "Carousel")
}

/**
 * List of screens for [JetcasterApp]
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Player : Screen("player/{$ARG_EPISODE_URI}") {
        fun createRoute(episodeUri: String) = "player/$episodeUri"
    }

    object PodcastDetails : Screen("podcast/{$ARG_PODCAST_URI}") {

        val PODCAST_URI = "podcastUri"
        fun createRoute(podcastUri: String) = "podcast/$podcastUri"
    }

    companion object {
        val ARG_PODCAST_URI = "podcastUri"
        val ARG_EPISODE_URI = "episodeUri"
    }
}

