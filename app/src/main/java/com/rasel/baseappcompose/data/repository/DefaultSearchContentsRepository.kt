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

package com.rasel.baseappcompose.data.repository

import com.rasel.baseappcompose.data.database.dao.NewsResourceFtsDao
import com.rasel.baseappcompose.data.database.dao.TopicDao
import com.rasel.baseappcompose.data.database.dao.TopicFtsDao
import com.rasel.baseappcompose.data.Dispatcher
import com.rasel.baseappcompose.data.NiaDispatchers
import com.rasel.baseappcompose.data.database.dao.NewsResourceDao
import com.rasel.baseappcompose.data.database.model.PopulatedNewsResource
import com.rasel.baseappcompose.data.database.model.asExternalModel
import com.rasel.baseappcompose.data.database.model.asFtsEntity
import com.rasel.baseappcompose.data.model.SearchResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultSearchContentsRepository @Inject constructor(
    private val newsResourceDao: NewsResourceDao,
    private val newsResourceFtsDao: NewsResourceFtsDao,
    private val topicDao: TopicDao,
    private val topicFtsDao: TopicFtsDao,
    @Dispatcher(NiaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : SearchContentsRepository {

    override suspend fun populateFtsData() {
        withContext(ioDispatcher) {
            newsResourceFtsDao.insertAll(
                newsResourceDao.getNewsResources(
                    useFilterTopicIds = false,
                    useFilterNewsIds = false,
                )
                    .first()
                    .map(PopulatedNewsResource::asFtsEntity),
            )
            topicFtsDao.insertAll(topicDao.getOneOffTopicEntities().map { it.asFtsEntity() })
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun searchContents(searchQuery: String): Flow<SearchResult> {
        // Surround the query by asterisks to match the query when it's in the middle of
        // a word
        val newsResourceIds = newsResourceFtsDao.searchAllNewsResources("*$searchQuery*")
        val topicIds = topicFtsDao.searchAllTopics("*$searchQuery*")

        val newsResourcesFlow = newsResourceIds
            .mapLatest { it.toSet() }
            .distinctUntilChanged()
            .flatMapLatest {
                newsResourceDao.getNewsResources(useFilterNewsIds = true, filterNewsIds = it)
            }
        val topicsFlow = topicIds
            .mapLatest { it.toSet() }
            .distinctUntilChanged()
            .flatMapLatest(topicDao::getTopicEntities)
        return combine(newsResourcesFlow, topicsFlow) { newsResources, topics ->
            SearchResult(
                topics = topics.map { it.asExternalModel() },
                newsResources = newsResources.map { it.asExternalModel() },
            )
        }
    }

    override fun getSearchContentsCount(): Flow<Int> =
        combine(
            newsResourceFtsDao.getCount(),
            topicFtsDao.getCount(),
        ) { newsResourceCount, topicsCount ->
            newsResourceCount + topicsCount
        }
}
