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

import androidx.annotation.VisibleForTesting
import com.rasel.baseappcompose.core.analytics.AnalyticsHelper
import com.rasel.baseappcompose.data.database.NiaPreferencesDataSource
import com.rasel.baseappcompose.data.model.DarkThemeConfig
import com.rasel.baseappcompose.data.model.ThemeBrand
import com.rasel.baseappcompose.data.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class OfflineFirstUserDataRepository @Inject constructor(
    private val niaPreferencesDataSource: NiaPreferencesDataSource,
//    private val analyticsHelper: AnalyticsHelper,
) : UserDataRepository {

    override val userData: Flow<UserData> = flow {

        val userData = UserData(
            bookmarkedNewsResources = setOf("1", "4"),
            viewedNewsResources = setOf("1", "2", "4"),
            followedTopics = emptySet(),
            themeBrand = ThemeBrand.ANDROID,
            darkThemeConfig = DarkThemeConfig.DARK,
            shouldHideOnboarding = true,
            useDynamicColor = false,
        )
        emit(userData)
    }
//        niaPreferencesDataSource.userData

    @VisibleForTesting
    override suspend fun setFollowedTopicIds(followedTopicIds: Set<String>) {
    }
//        niaPreferencesDataSource.setFollowedTopicIds(followedTopicIds)

    override suspend fun setTopicIdFollowed(followedTopicId: String, followed: Boolean) {
//        niaPreferencesDataSource.setTopicIdFollowed(followedTopicId, followed)
//        analyticsHelper.logTopicFollowToggled(followedTopicId, followed)
    }

    override suspend fun setNewsResourceBookmarked(newsResourceId: String, bookmarked: Boolean) {
//        niaPreferencesDataSource.setNewsResourceBookmarked(newsResourceId, bookmarked)
        /*analyticsHelper.logNewsResourceBookmarkToggled(
            newsResourceId = newsResourceId,
            isBookmarked = bookmarked,
        )*/
    }

    override suspend fun setNewsResourceViewed(newsResourceId: String, viewed: Boolean) {}
//    = niaPreferencesDataSource.setNewsResourceViewed(newsResourceId, viewed)

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
//        niaPreferencesDataSource.setThemeBrand(themeBrand)
//        analyticsHelper.logThemeChanged(themeBrand.name)
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
//        niaPreferencesDataSource.setDarkThemeConfig(darkThemeConfig)
//        analyticsHelper.logDarkThemeConfigChanged(darkThemeConfig.name)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
//        niaPreferencesDataSource.setDynamicColorPreference(useDynamicColor)
//        analyticsHelper.logDynamicColorPreferenceChanged(useDynamicColor)
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
//        niaPreferencesDataSource.setShouldHideOnboarding(shouldHideOnboarding)
//        analyticsHelper.logOnboardingStateChanged(shouldHideOnboarding)
    }
}
