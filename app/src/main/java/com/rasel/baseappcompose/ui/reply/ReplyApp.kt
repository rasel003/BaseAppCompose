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

package com.rasel.baseappcompose.ui.reply

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.rasel.baseappcompose.CupcakeScreen
import com.rasel.baseappcompose.MovieDetailsScreen
import com.rasel.baseappcompose.OrderSummaryScreen
import com.rasel.baseappcompose.OrderViewModel
import com.rasel.baseappcompose.R
import com.rasel.baseappcompose.SelectOptionScreen
import com.rasel.baseappcompose.StartOrderScreen
import com.rasel.baseappcompose.data.DataSource
import com.rasel.baseappcompose.data.Result
import com.rasel.baseappcompose.data.posts.impl.BlockingFakePostsRepository
import com.rasel.baseappcompose.data.posts.impl.post3
import com.rasel.baseappcompose.ui.article.ArticleScreen
import com.rasel.baseappcompose.ui.home.HomeFeedScreen
import com.rasel.baseappcompose.ui.home.HomeUiState
import com.rasel.baseappcompose.ui.navigation.ReplyNavigationActions
import com.rasel.baseappcompose.ui.navigation.ReplyNavigationWrapper
import com.rasel.baseappcompose.ui.navigation.ReplyRoute
import com.rasel.baseappcompose.ui.utils.DevicePosture
import com.rasel.baseappcompose.ui.utils.ReplyContentType
import com.rasel.baseappcompose.ui.utils.ReplyNavigationType
import com.rasel.baseappcompose.ui.utils.isBookPosture
import com.rasel.baseappcompose.ui.utils.isSeparating
import kotlinx.coroutines.runBlocking

private fun NavigationSuiteType.toReplyNavType() = when (this) {
    NavigationSuiteType.NavigationBar -> ReplyNavigationType.BOTTOM_NAVIGATION
    NavigationSuiteType.NavigationRail -> ReplyNavigationType.NAVIGATION_RAIL
    NavigationSuiteType.NavigationDrawer -> ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
    else -> ReplyNavigationType.BOTTOM_NAVIGATION
}

@Composable
fun ReplyApp(
    windowSize: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    replyHomeUIState: ReplyHomeUIState,
    closeDetailScreen: () -> Unit = {},
    navigateToDetail: (Long, ReplyContentType) -> Unit = { _, _ -> },
    toggleSelectedEmail: (Long) -> Unit = { }
) {
    /**
     * We are using display's folding features to map the device postures a fold is in.
     * In the state of folding device If it's half fold in BookPosture we want to avoid content
     * at the crease/hinge
     */
    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()

    val foldingDevicePosture = when {
        isBookPosture(foldingFeature) ->
            DevicePosture.BookPosture(foldingFeature.bounds)

        isSeparating(foldingFeature) ->
            DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

        else -> DevicePosture.NormalPosture
    }

    val contentType = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> ReplyContentType.SINGLE_PANE
        WindowWidthSizeClass.Medium -> if (foldingDevicePosture != DevicePosture.NormalPosture) {
            ReplyContentType.DUAL_PANE
        } else {
            ReplyContentType.SINGLE_PANE
        }
        WindowWidthSizeClass.Expanded -> ReplyContentType.DUAL_PANE
        else -> ReplyContentType.SINGLE_PANE
    }

    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        ReplyNavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination =
        navBackStackEntry?.destination?.route ?: ReplyRoute.INBOX

    Surface {
        ReplyNavigationWrapper(
            selectedDestination = selectedDestination,
            navigateToTopLevelDestination = navigationActions::navigateTo
        ) {
            ReplyNavHost(
                navController = navController,
                contentType = contentType,
                displayFeatures = displayFeatures,
                replyHomeUIState = replyHomeUIState,
                navigationType = navSuiteType.toReplyNavType(),
                closeDetailScreen = closeDetailScreen,
                navigateToDetail = navigateToDetail,
                toggleSelectedEmail = toggleSelectedEmail,
            )
        }
    }
}

@Composable
private fun ReplyNavHost(
    navController: NavHostController,
    contentType: ReplyContentType,
    displayFeatures: List<DisplayFeature>,
    replyHomeUIState: ReplyHomeUIState,
    navigationType: ReplyNavigationType,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long, ReplyContentType) -> Unit,
    toggleSelectedEmail: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = viewModel(),
) {

    val postsFeed = runBlocking {
        (BlockingFakePostsRepository().getPostsFeed() as Result.Success).data
    }
    val post = runBlocking {
        (BlockingFakePostsRepository().getPost(post3.id) as Result.Success).data
    }
    val uiState by viewModel.uiState.collectAsState()


    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ReplyRoute.INBOX,
    ) {
        composable(ReplyRoute.INBOX) {
            ReplyInboxScreen(
                contentType = contentType,
                replyHomeUIState = replyHomeUIState,
                navigationType = navigationType,
                displayFeatures = displayFeatures,
                closeDetailScreen = closeDetailScreen,
                navigateToDetail = navigateToDetail,
                toggleSelectedEmail = toggleSelectedEmail
            )
        }
        composable(ReplyRoute.CUP_CAKE) {
            StartOrderScreen(
                quantityOptions = DataSource.quantityOptions,
                onNextButtonClicked = {
                    viewModel.setQuantity(it)
                    navController.navigate(CupcakeScreen.Flavor.name)
                },
                onNextButtonClicked1 = {
                    navController.navigate(CupcakeScreen.MOVIE_DETAILS.name)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.padding_medium))
            )
        }
        composable(ReplyRoute.ARTICLES) {
            ArticleScreen(post, true, {}, false, {})
        }
        composable(route = ReplyRoute.JET_NEWS) {
            val context = LocalContext.current
            HomeFeedScreen(
                uiState = HomeUiState.HasPosts(
                    postsFeed = postsFeed,
                    selectedPost = postsFeed.highlightedPost,
                    isArticleOpen = false,
                    favorites = emptySet(),
                    isLoading = false,
                    errorMessages = emptyList(),
                    searchInput = ""
                ),
                showTopAppBar = true,
                onToggleFavorite = {},
                onSelectPost = {},
                onRefreshPosts = {},
                onErrorDismiss = {},
                openDrawer = {},
                homeListLazyListState = rememberLazyListState(),
                snackbarHostState = SnackbarHostState(),
                onSearchInputChanged = {}
            )
        }

        composable(route = CupcakeScreen.Flavor.name) {
            val context = LocalContext.current
            SelectOptionScreen(
                subtotal = uiState.price,
                onNextButtonClicked = { navController.navigate(CupcakeScreen.Pickup.name) },
                onCancelButtonClicked = {
                    cancelOrderAndNavigateToStart(viewModel, navController)
                },
                options = DataSource.flavors.map { id -> context.resources.getString(id) },
                onSelectionChanged = { viewModel.setFlavor(it) },
                modifier = Modifier.fillMaxHeight()
            )
        }
        composable(route = CupcakeScreen.Pickup.name) {
            SelectOptionScreen(
                subtotal = uiState.price,
                onNextButtonClicked = { navController.navigate(CupcakeScreen.Summary.name) },
                onCancelButtonClicked = {
                    cancelOrderAndNavigateToStart(viewModel, navController)
                },
                options = uiState.pickupOptions,
                onSelectionChanged = { viewModel.setDate(it) },
                modifier = Modifier.fillMaxHeight()
            )
        }
        composable(route = CupcakeScreen.Summary.name) {
            val context = LocalContext.current
            OrderSummaryScreen(
                orderUiState = uiState,
                onCancelButtonClicked = {
                    cancelOrderAndNavigateToStart(viewModel, navController)
                },
                onSendButtonClicked = { subject: String, summary: String ->
                    shareOrder(context, subject = subject, summary = summary)
                },
                modifier = Modifier.fillMaxHeight()
            )
        }
        composable(route = CupcakeScreen.MOVIE_DETAILS.name) {
            val context = LocalContext.current
            MovieDetailsScreen(
                orderUiState = uiState,
                onCancelButtonClicked = {
                    cancelOrderAndNavigateToStart(viewModel, navController)
                },
                onSendButtonClicked = { subject: String, summary: String ->
                    shareOrder(context, subject = subject, summary = summary)
                },
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}


/**
 * Resets the [OrderUiState] and pops up to [CupcakeScreen.Start]
 */
private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(CupcakeScreen.Start.name, inclusive = false)
}

/**
 * Creates an intent to share order details
 */
private fun shareOrder(context: Context, subject: String, summary: String) {
    // Create an ACTION_SEND implicit intent with order details in the intent extras
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_cupcake_order)
        )
    )
}