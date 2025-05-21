package com.rasel.baseappcompose.data.mock_data

import com.rasel.baseappcompose.data.model.DarkThemeConfig
import com.rasel.baseappcompose.data.model.NewsResource
import com.rasel.baseappcompose.data.model.ThemeBrand
import com.rasel.baseappcompose.data.model.Topic
import com.rasel.baseappcompose.data.model.UnsplashPhoto
import com.rasel.baseappcompose.data.model.UnsplashPhotoUrls
import com.rasel.baseappcompose.data.model.UnsplashUser
import com.rasel.baseappcompose.data.model.UserData
import com.rasel.baseappcompose.data.model.UserNewsResource
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant


val topicsTestData: List<Topic> = listOf(
    Topic(
        id = "2",
        name = "Headlines",
        shortDescription = "News we want everyone to see",
        longDescription = "Stay up to date with the latest events and announcements from Android!",
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/now-in-android.appspot.com/o/img%2Fic_topic_Headlines.svg?alt=media&token=506faab0-617a-4668-9e63-4a2fb996603f",
        url = "",
    ),
    Topic(
        id = "3",
        name = "UI",
        shortDescription = "Material Design, Navigation, Text, Paging, Accessibility (a11y), Internationalization (i18n), Localization (l10n), Animations, Large Screens, Widgets",
        longDescription = "Learn how to optimize your app's user interface - everything that users can see and interact with. Stay up to date on topics such as Material Design, Navigation, Text, Paging, Compose, Accessibility (a11y), Internationalization (i18n), Localization (l10n), Animations, Large Screens, Widgets, and many more!",
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/now-in-android.appspot.com/o/img%2Fic_topic_UI.svg?alt=media&token=0ee1842b-12e8-435f-87ba-a5bb02c47594",
        url = "",
    ),
    Topic(
        id = "4",
        name = "Testing",
        shortDescription = "CI, Espresso, TestLab, etc",
        longDescription = "Testing is an integral part of the app development process. By running tests against your app consistently, you can verify your app's correctness, functional behavior, and usability before you release it publicly. Stay up to date on the latest tricks in CI, Espresso, and Firebase TestLab.",
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/now-in-android.appspot.com/o/img%2Fic_topic_Testing.svg?alt=media&token=a11533c4-7cc8-4b11-91a3-806158ebf428",
        url = "",
    ),
)

val userNewsResourcesTestData: List<UserNewsResource> = UserData(
    bookmarkedNewsResources = setOf("1", "4"),
    viewedNewsResources = setOf("1", "2", "4"),
    followedTopics = emptySet(),
    themeBrand = ThemeBrand.ANDROID,
    darkThemeConfig = DarkThemeConfig.DARK,
    shouldHideOnboarding = true,
    useDynamicColor = false,
).let { userData ->
    listOf(
        UserNewsResource(
            newsResource = NewsResource(
                id = "1",
                title = "Android Basics with Compose",
                content = "We released the first two units of Android Basics with Compose, our first free course that teaches Android Development with Jetpack Compose to anyone; you do not need any prior programming experience other than basic computer literacy to get started. You’ll learn the fundamentals of programming in Kotlin while building Android apps using Jetpack Compose, Android’s modern toolkit that simplifies and accelerates native UI development. These two units are just the beginning; more will be coming soon. Check out Android Basics with Compose to get started on your Android development journey",
                url = "https://android-developers.googleblog.com/2022/05/new-android-basics-with-compose-course.html",
                headerImageUrl = "https://developer.android.com/images/hero-assets/android-basics-compose.svg",
                publishDate = LocalDateTime(
                    year = 2022,
                    monthNumber = 5,
                    dayOfMonth = 4,
                    hour = 23,
                    minute = 0,
                    second = 0,
                    nanosecond = 0,
                ).toInstant(TimeZone.UTC),
                type = "Codelab",
                topics = listOf(topicsTestData[2]),
            ),
            userData = userData,
        ),
        UserNewsResource(
            newsResource = NewsResource(
                id = "2",
                title = "Thanks for helping us reach 1M YouTube Subscribers",
                content = "Thank you everyone for following the Now in Android series and everything the " +
                        "Android Developers YouTube channel has to offer. During the Android Developer " +
                        "Summit, our YouTube channel reached 1 million subscribers! Here’s a small video to " +
                        "thank you all.",
                url = "https://youtu.be/-fJ6poHQrjM",
                headerImageUrl = "https://i.ytimg.com/vi/-fJ6poHQrjM/maxresdefault.jpg",
                publishDate = Instant.parse("2021-11-09T00:00:00.000Z"),
                type = "Video 📺",
                topics = topicsTestData.take(2),
            ),
            userData = userData,
        ),
        UserNewsResource(
            newsResource = NewsResource(
                id = "3",
                title = "Transformations and customisations in the Paging Library",
                content = "A demonstration of different operations that can be performed " +
                        "with Paging. Transformations like inserting separators, when to " +
                        "create a new pager, and customisation options for consuming " +
                        "PagingData.",
                url = "https://youtu.be/ZARz0pjm5YM",
                headerImageUrl = "https://i.ytimg.com/vi/ZARz0pjm5YM/maxresdefault.jpg",
                publishDate = Instant.parse("2021-11-01T00:00:00.000Z"),
                type = "Video 📺",
                topics = listOf(topicsTestData[2]),
            ),
            userData = userData,
        ),
        UserNewsResource(
            newsResource = NewsResource(
                id = "4",
                title = "New Jetpack Release",
                content = "New Jetpack release includes updates to libraries such as CameraX, Benchmark, and" +
                        "more!",
                url = "https://developer.android.com/jetpack/androidx/versions/all-channel",
                headerImageUrl = "",
                publishDate = Instant.parse("2022-10-01T00:00:00.000Z"),
                type = "",
                topics = listOf(topicsTestData[2]),
            ),
            userData = userData,
        ),
    )
}

val fakeUnsplashPhoto = List(10) {
    UnsplashPhoto(
        id = FakeValueFactory.randomInt().toString(),
        urls = UnsplashPhotoUrls(
            raw = randomSampleImageUrl(),
            full = randomSampleImageUrl(),
            regular = randomSampleImageUrl(),
            small = randomSampleImageUrl(),
            thumb = randomSampleImageUrl(),
            small_s3 = randomSampleImageUrl()
        ),
        user = UnsplashUser(
            name = FakeValueFactory.randomString(),
            username = FakeValueFactory.randomString()
        )
    )
}

val photoLists = listOf(
    "https://images.unsplash.com/photo-1568565110033-4eab8bcdd72c?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1556695736-d287caebc48e?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1425913397330-cf8af2ff40a1?q=80&w=3174&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1476231682828-37e571bc172f?q=80&w=3174&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1449824913935-59a10b8d2000?q=80&w=1920&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1564501911891-74a27d949ee1?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1510414842594-a61c69b5ae57?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1523590564318-491748f70ea7?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1486870591958-9b9d0d1dda99?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1568605117036-5fe5e7bab0b7?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1526481280693-3bfa7568e0f3?q=80&w=2971&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1533050487297-09b450131914?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1545569341-9eb8b30979d9?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1458869612855-bb6009d50368?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1509099395498-a26c959ba0b7?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1455577380025-4321f1e1dca7?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1593132808462-578ca7a387d9?q=80&w=3174&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1570129477492-45c003edd2be?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1482049016688-2d3e1b311543?q=80&w=3110&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?q=80&w=3087&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?q=80&w=2881&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://plus.unsplash.com/premium_photo-1676968002767-1f6a09891350?q=80&w=3087&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://plus.unsplash.com/premium_photo-1661595245288-65d1430d0d13?q=80&w=3000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://plus.unsplash.com/premium_photo-1682116752956-c880046f5361?q=80&w=3099&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://plus.unsplash.com/premium_photo-1686782502531-feb0e6a56eb5?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://plus.unsplash.com/premium_photo-1664358190450-2d84d93b9546?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
)