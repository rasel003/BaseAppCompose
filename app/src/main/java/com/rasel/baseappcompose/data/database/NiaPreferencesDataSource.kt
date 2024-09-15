package com.rasel.baseappcompose.data.database

import com.rasel.baseappcompose.data.model.DarkThemeConfig
import com.rasel.baseappcompose.data.model.ThemeBrand
import com.rasel.baseappcompose.data.model.UserData
import javax.inject.Inject

class NiaPreferencesDataSource @Inject constructor(
) {

    val userData = listOf(
        UserData(
            bookmarkedNewsResources = setOf("1", "4"),
            viewedNewsResources = setOf("1", "2", "4"),
            followedTopics = emptySet(),
            themeBrand = ThemeBrand.ANDROID,
            darkThemeConfig = DarkThemeConfig.DARK,
            shouldHideOnboarding = true,
            useDynamicColor = false,
        )
    )

}