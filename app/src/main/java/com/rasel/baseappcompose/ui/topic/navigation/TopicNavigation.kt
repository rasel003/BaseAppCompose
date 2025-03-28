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

package com.rasel.baseappcompose.ui.topic.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.rasel.baseappcompose.ui.topic.TopicScreen
import kotlinx.serialization.Serializable
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8

private val URL_CHARACTER_ENCODING = UTF_8.name()

const val TOPIC_ROUTE = "topic_route"

@Serializable data class TopicRoute(val id: String)


fun NavController.navigateToTopic(topicId: String, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(createTopicRoute(topicId)) {
        navOptions()
    }
}

fun createTopicRoute(topicId: String): String {
    val encodedId = URLEncoder.encode(topicId, URL_CHARACTER_ENCODING)
    return "$TOPIC_ROUTE/$encodedId"
}

fun NavGraphBuilder.topicScreen(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onTopicClick: (String) -> Unit,
) {
    composable<TopicRoute> {
        TopicScreen(
            showBackButton = showBackButton,
            onBackClick = onBackClick,
            onTopicClick = onTopicClick,
        )
    }
}
