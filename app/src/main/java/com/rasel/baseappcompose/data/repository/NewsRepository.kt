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

package com.rasel.baseappcompose.data.repository

import androidx.paging.PagingData
import com.rasel.baseappcompose.data.model.NewsResource
import com.rasel.baseappcompose.data.model.UnsplashPhoto
import com.rasel.baseappcompose.data.util.Syncable
import kotlinx.coroutines.flow.Flow

/**
 * Encapsulation class for query parameters for [NewsResource]
 */
data class NewsResourceQuery(
    /**
     * Topic ids to filter for. Null means any topic id will match.
     */
    val filterTopicIds: Set<String>? = null,
    /**
     * News ids to filter for. Null means any news id will match.
     */
    val filterNewsIds: Set<String>? = null,
)

/**
 * Data layer implementation for [NewsResource]
 */
interface NewsRepository : Syncable {
    /**
     * Returns available news resources that match the specified [query].
     */
    fun getNewsResources(
        query: NewsResourceQuery = NewsResourceQuery(
            filterTopicIds = null,
            filterNewsIds = null,
        ),
    ): Flow<List<NewsResource>>


    fun getNews(): Flow<PagingData<UnsplashPhoto>>
}
