/*
 * Copyright 2020 The Android Open Source Project
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

package com.rasel.baseappcompose.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rasel.baseappcompose.data.database.dao.CategoriesDao
import com.rasel.baseappcompose.data.database.dao.EpisodesDao
import com.rasel.baseappcompose.data.database.dao.PodcastCategoryEntryDao
import com.rasel.baseappcompose.data.database.dao.PodcastFollowedEntryDao
import com.rasel.baseappcompose.data.database.dao.PodcastsDao
import com.rasel.baseappcompose.data.database.dao.TransactionRunnerDao
import com.example.jetcaster.core.data.database.model.Category
import com.example.jetcaster.core.data.database.model.Episode
import com.example.jetcaster.core.data.database.model.Podcast
import com.example.jetcaster.core.data.database.model.PodcastCategoryEntry
import com.example.jetcaster.core.data.database.model.PodcastFollowedEntry
import com.google.samples.apps.nowinandroid.core.database.dao.NewsResourceFtsDao
import com.google.samples.apps.nowinandroid.core.database.dao.RecentSearchQueryDao
import com.google.samples.apps.nowinandroid.core.database.dao.TopicDao
import com.google.samples.apps.nowinandroid.core.database.dao.TopicFtsDao
import com.rasel.baseappcompose.data.database.util.AppTypeConverters
import com.rasel.baseappcompose.data.database.dao.NewsResourceDao
import com.rasel.baseappcompose.data.database.model.NewsResourceEntity
import com.rasel.baseappcompose.data.database.model.NewsResourceFtsEntity
import com.rasel.baseappcompose.data.database.model.NewsResourceTopicCrossRef
import com.rasel.baseappcompose.data.database.model.RecentSearchQueryEntity
import com.rasel.baseappcompose.data.database.model.TopicEntity
import com.rasel.baseappcompose.data.database.model.TopicFtsEntity

/**
 * The [RoomDatabase] we use in this app.
 */
@Database(
    entities = [
        Podcast::class,
        Episode::class,
        PodcastCategoryEntry::class,
        Category::class,
        PodcastFollowedEntry::class,
        NewsResourceEntity::class,
        NewsResourceTopicCrossRef::class,
        NewsResourceFtsEntity::class,
        TopicEntity::class,
        TopicFtsEntity::class,
        RecentSearchQueryEntity::class,
    ],
   /* autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3, spec = DatabaseMigrations.Schema2to3::class),
        AutoMigration(from = 3, to = 4),
        AutoMigration(from = 4, to = 5),
        AutoMigration(from = 5, to = 6),
        AutoMigration(from = 6, to = 7),
        AutoMigration(from = 7, to = 8),
        AutoMigration(from = 8, to = 9),
        AutoMigration(from = 9, to = 10),
        AutoMigration(from = 10, to = 11, spec = DatabaseMigrations.Schema10to11::class),
        AutoMigration(from = 11, to = 12, spec = DatabaseMigrations.Schema11to12::class),
        AutoMigration(from = 12, to = 13),
        AutoMigration(from = 13, to = 14),
    ],*/
    version = 1,
    exportSchema = false
)
@TypeConverters(AppTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun podcastsDao(): PodcastsDao
    abstract fun episodesDao(): EpisodesDao
    abstract fun categoriesDao(): CategoriesDao
    abstract fun podcastCategoryEntryDao(): PodcastCategoryEntryDao
    abstract fun transactionRunnerDao(): TransactionRunnerDao
    abstract fun podcastFollowedEntryDao(): PodcastFollowedEntryDao
    abstract fun topicDao(): TopicDao
    abstract fun newsResourceDao(): NewsResourceDao
    abstract fun topicFtsDao(): TopicFtsDao
    abstract fun newsResourceFtsDao(): NewsResourceFtsDao
    abstract fun recentSearchQueryDao(): RecentSearchQueryDao
}
