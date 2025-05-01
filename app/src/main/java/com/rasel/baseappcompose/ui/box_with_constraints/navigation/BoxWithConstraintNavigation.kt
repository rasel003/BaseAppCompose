package com.rasel.baseappcompose.ui.box_with_constraints.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.rasel.baseappcompose.ui.box_with_constraints.BoxWithConstraintScreen
import kotlinx.serialization.Serializable

@Serializable
data object BoxWithConstraintRoute // route to ForYou screen

fun NavController.navigateToBoxWithConstraint(navOptions: NavOptions = NavOptions.Builder().build()) = navigate(route = BoxWithConstraintRoute, navOptions)


fun NavGraphBuilder.boxWithConstraintScreen() {
    composable<BoxWithConstraintRoute> {
        BoxWithConstraintScreen()
    }
}