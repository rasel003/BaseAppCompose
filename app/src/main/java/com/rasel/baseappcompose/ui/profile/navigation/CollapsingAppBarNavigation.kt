package com.rasel.baseappcompose.ui.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.rasel.baseappcompose.ui.profile.CollapsingAppBarDemo
import kotlinx.serialization.Serializable

@Serializable
object  CollapsingAppBarRoute

fun NavController.navigateToCollapsingAppBar(navOptions: NavOptions = NavOptions.Builder().build()) =
    navigate(route = CollapsingAppBarRoute, navOptions)

fun NavGraphBuilder.collapsingAppBarScreen() {
    composable<CollapsingAppBarRoute> {
        CollapsingAppBarDemo()
    }
}