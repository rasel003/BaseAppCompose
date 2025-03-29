package com.rasel.baseappcompose.ui.images.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.serialization.Serializable

@Serializable
data class ImageviewRoute(
    // The ID of the topic which will be initially selected at this destination
    val url: String,
)
fun NavController.navigateToImageview(
    url: String,
    navOptions: NavOptions? = null,
) {
    navigate(route = ImageviewRoute(url = url), navOptions)
}