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

data class AppTopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
)

object AppRoute {
    const val START = "BaseAppCompose"
    const val Flavor = "Choose_Flavor"
    const val Pickup = "Choose_Pickup_Date"
    const val MOVIE_DETAILS = "Movie Details"
    const val Summary = "Order Summary"
    const val INBOX = "Inbox"
    const val ARTICLES = "Articles"
    const val DM = "DirectMessages"
    const val GROUPS = "Groups"
    const val JET_NEWS = "jet_news"
    const val CUP_CAKE = "cup_cake"
    const val Feed = "feed"
    const val ArticleDetails = "ArticleDetails"
    const val HOME_ROUTE = "home"
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
