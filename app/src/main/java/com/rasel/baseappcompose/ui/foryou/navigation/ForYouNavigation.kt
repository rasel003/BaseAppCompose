/*
 * Copyright 2022 The Android Open Source Project
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

package com.rasel.baseappcompose.ui.foryou.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import com.rasel.baseappcompose.R
import com.rasel.baseappcompose.designsystem.component.NiaTopAppBar
import com.rasel.baseappcompose.designsystem.icon.NiaIcons
import com.rasel.baseappcompose.notifications.DEEP_LINK_URI_PATTERN
import com.rasel.baseappcompose.ui.foryou.ForYouScreen
import kotlinx.serialization.Serializable

@Serializable data object ForYouRoute // route to ForYou screen

@Serializable data object ForYouBaseRoute // route to base navigation graph

fun NavController.navigateToForYou(navOptions: NavOptions) = navigate(route = ForYouRoute, navOptions)

/**
 *  The ForYou section of the app. It can also display information about topics.
 *  This should be supplied from a separate module.
 *
 *  @param onTopicClick - Called when a topic is clicked, contains the ID of the topic
 *  @param topicDestination - Destination for topic content
 */
@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.forYouSection(
    onTopicClick: (String) -> Unit,
    navigateToSearch: () -> Unit,
    onTopAppBarActionClick: () -> Unit,
    topicDestination: NavGraphBuilder.() -> Unit,
) {
    navigation<ForYouBaseRoute>(startDestination = ForYouRoute) {
        composable<ForYouRoute>(
            deepLinks = listOf(
                navDeepLink {
                    /**
                     * This destination has a deep link that enables a specific news resource to be
                     * opened from a notification (@see SystemTrayNotifier for more). The news resource
                     * ID is sent in the URI rather than being modelled in the route type because it's
                     * transient data (stored in SavedStateHandle) that is cleared after the user has
                     * opened the news resource.
                     */
                    uriPattern = DEEP_LINK_URI_PATTERN
                },
            ),
        ) {
            Column {
                NiaTopAppBar(
                    titleRes = R.string.app_name,
                    navigationIcon = NiaIcons.Search,
                    navigationIconContentDescription = stringResource(
                        id = R.string.feature_settings_top_app_bar_navigation_icon_description,
                    ),
                    actionIcon = NiaIcons.Bookmarks,
                    actionIconContentDescription = stringResource(
                        id = R.string.feature_settings_top_app_bar_action_icon_description,
                    ),
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent,
                    ),
                    onActionClick = { onTopAppBarActionClick() },
                    onNavigationClick = { navigateToSearch() },
                )
                ForYouScreen(onTopicClick)
            }
        }
        topicDestination()
    }
}
