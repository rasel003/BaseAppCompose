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

package com.rasel.baseappcompose.data.model

import com.rasel.baseappcompose.data.database.model.NewsResourceEntity
import com.rasel.baseappcompose.data.database.model.NewsResourceTopicCrossRef
import com.rasel.baseappcompose.data.database.model.TopicEntity
import com.rasel.baseappcompose.data.model.Topic
import kotlinx.datetime.Instant

/**
 * External data layer representation of a fully populated NiA news resource
 */
data class NewsResource(
    val id: String,
    val title: String,
    val content: String,
    val url: String,
    val headerImageUrl: String?,
    val publishDate: Instant,
    val type: String,
    val topics: List<Topic>,
)



fun NetworkNewsResource.asEntity() = NewsResourceEntity(
    id = id,
    title = title,
    content = content,
    url = url,
    headerImageUrl = headerImageUrl,
    publishDate = publishDate,
    type = type,
)

fun NetworkNewsResourceExpanded.asEntity() = NewsResourceEntity(
    id = id,
    title = title,
    content = content,
    url = url,
    headerImageUrl = headerImageUrl,
    publishDate = publishDate,
    type = type,
)

/**
 * A shell [TopicEntity] to fulfill the foreign key constraint when inserting
 * a [NewsResourceEntity] into the DB
 */
fun NetworkNewsResource.topicEntityShells() =
    topics.map { topicId ->
        TopicEntity(
            id = topicId,
            name = "",
            url = "",
            imageUrl = "",
            shortDescription = "",
            longDescription = "",
        )
    }

fun NetworkNewsResource.topicCrossReferences(): List<NewsResourceTopicCrossRef> =
    topics.map { topicId ->
        NewsResourceTopicCrossRef(
            newsResourceId = id,
            topicId = topicId,
        )
    }