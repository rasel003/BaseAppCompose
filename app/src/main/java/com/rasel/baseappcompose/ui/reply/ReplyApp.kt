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
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.example.compose.snippets.animations.AnimationExamplesScreen
import com.example.jetnews.ui.interests.InterestsViewModel
import com.rasel.baseappcompose.AppDrawer
import com.rasel.baseappcompose.JetnewsDestinations
import com.rasel.baseappcompose.POST_ID
import com.rasel.baseappcompose.R
import com.rasel.baseappcompose.animations.sharedelement.*
import com.rasel.baseappcompose.data.AppContainer
import com.rasel.baseappcompose.data.DataSource
import com.rasel.baseappcompose.data.Result
import com.rasel.baseappcompose.data.posts.impl.BlockingFakePostsRepository
import com.rasel.baseappcompose.data.posts.impl.post3
import com.rasel.baseappcompose.designsystem.component.*
import com.rasel.baseappcompose.ui.JetnewsApplication.Companion.JETNEWS_APP_URI
import com.rasel.baseappcompose.ui.cup_cake.AnimationList
import com.rasel.baseappcompose.ui.cup_cake.MovieDetailsScreen
import com.rasel.baseappcompose.ui.cup_cake.ValueBasedAnimation
import com.rasel.baseappcompose.ui.cup_cake.ViewShowHideAnimation
import com.rasel.baseappcompose.ui.graphics.*
import com.rasel.baseappcompose.ui.home.HomeRoute
import com.rasel.baseappcompose.ui.home.HomeViewModel
import com.rasel.baseappcompose.ui.images.*
import com.rasel.baseappcompose.ui.jet_caster.JetcasterAppState
import com.rasel.baseappcompose.ui.jet_caster.Screen
import com.rasel.baseappcompose.ui.jet_caster.home.MainScreen
import com.rasel.baseappcompose.ui.jet_caster.player.PlayerScreen
import com.rasel.baseappcompose.ui.jet_caster.rememberJetcasterAppState
import com.rasel.baseappcompose.ui.landing.LandingScreen
import com.rasel.baseappcompose.ui.navigation.Destination
import com.rasel.baseappcompose.ui.navigation.MainDestinations
import com.rasel.baseappcompose.ui.navigation.AppNavigationActions
import com.rasel.baseappcompose.ui.navigation.ReplyNavigationWrapper
import com.rasel.baseappcompose.ui.navigation.AppRoute
import com.rasel.baseappcompose.ui.navigation.AppRoute.LANDING_SCREEN
import com.rasel.baseappcompose.ui.navigation.CupCake
import com.rasel.baseappcompose.ui.navigation.Flavor
import com.rasel.baseappcompose.ui.navigation.Inbox
import com.rasel.baseappcompose.ui.navigation.TopComponentsDestination
import com.rasel.baseappcompose.ui.navigation.rememberJetsnackNavController
import com.rasel.baseappcompose.ui.news.PagingListScreen
import com.rasel.baseappcompose.ui.order.OrderSummaryScreen
import com.rasel.baseappcompose.ui.order.OrderViewModel
import com.rasel.baseappcompose.ui.order.SelectOptionScreen
import com.rasel.baseappcompose.ui.order.StartOrderScreen
import com.rasel.baseappcompose.ui.setting.SettingsDialog
import com.rasel.baseappcompose.ui.snack_home.HomeSections
import com.rasel.baseappcompose.ui.snack_home.addHomeGraph
import com.rasel.baseappcompose.ui.snack_home.composableWithCompositionLocal
import com.rasel.baseappcompose.ui.snackdetail.SnackDetail
import com.rasel.baseappcompose.ui.snackdetail.nonSpatialExpressiveSpring
import com.rasel.baseappcompose.ui.snackdetail.spatialExpressiveSpring
import com.rasel.baseappcompose.ui.utils.DevicePosture
import com.rasel.baseappcompose.ui.utils.ReplyContentType
import com.rasel.baseappcompose.ui.utils.ReplyNavigationType
import com.rasel.baseappcompose.ui.utils.isBookPosture
import com.rasel.baseappcompose.ui.utils.isSeparating
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private fun NavigationSuiteType.toReplyNavType() = when (this) {
    NavigationSuiteType.NavigationBar -> ReplyNavigationType.BOTTOM_NAVIGATION
    NavigationSuiteType.NavigationRail -> ReplyNavigationType.NAVIGATION_RAIL
    NavigationSuiteType.NavigationDrawer -> ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
    else -> ReplyNavigationType.BOTTOM_NAVIGATION
}

@Composable
fun ReplyApp(
    appContainer: AppContainer,
    windowSize: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    replyHomeUIState: ReplyHomeUIState,
    closeDetailScreen: () -> Unit = {},
    navigateToDetail: (Long, ReplyContentType) -> Unit = { _, _ -> },
    toggleSelectedEmail: (Long) -> Unit = { },
    context: Context = LocalContext.current
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
        AppNavigationActions(navController, context)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination =
        navBackStackEntry?.destination?.route ?: AppRoute.INBOX

    var showSettingsDialog by rememberSaveable { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val currentRoute = navBackStackEntry?.destination?.route ?: JetnewsDestinations.HOME_ROUTE

    val isExpandedScreen = windowSize.widthSizeClass == WindowWidthSizeClass.Expanded
    val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)

    BackHandler(sizeAwareDrawerState.isOpen) {
        coroutineScope.launch {
            sizeAwareDrawerState.close()
        }
    }


    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer(
                currentRoute = currentRoute,
                navigateToHome = { /*navigationActions.navigateToHome*/ },
                navigateToInterests = { /*navigationActions.navigateToInterests*/ },
                closeDrawer = { coroutineScope.launch { sizeAwareDrawerState.close() } }
            )
        },
        drawerState = sizeAwareDrawerState,
        // Only enable opening the drawer via gestures if the screen is not expanded
        gesturesEnabled = !isExpandedScreen
    ) {
        Surface {
            ReplyNavigationWrapper(
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navigationActions::navigateTo
            ) {
                ReplyNavHost(
                    appContainer = appContainer,
                    navController = navController,
                    contentType = contentType,
                    displayFeatures = displayFeatures,
                    replyHomeUIState = replyHomeUIState,
                    navigationType = navSuiteType.toReplyNavType(),
                    closeDetailScreen = closeDetailScreen,
                    navigateToDetail = navigateToDetail,
                    toggleSelectedEmail = toggleSelectedEmail,
                    showSettingsDialog = showSettingsDialog,
                    openSettingDialog = { showSettingsDialog = true },
                    onSettingsDismissed = { showSettingsDialog = false },
                    navigateTo = { navigationActions.navigateTo(it) },
                    cancelOrderAndNavigateToStart = { navigationActions.cancelOrderAndNavigateToStart() }
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ReplyNavHost(
    appContainer: AppContainer,
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
    appState: JetcasterAppState = rememberJetcasterAppState(),
    showSettingsDialog: Boolean,
    openSettingDialog: () -> Unit,
    onSettingsDismissed: () -> Unit,
    navigateTo: (String) -> Unit,
    cancelOrderAndNavigateToStart: () -> Unit
) {

    val postsFeed = runBlocking {
        (BlockingFakePostsRepository().getPostsFeed() as Result.Success).data
    }
    val post = runBlocking {
        (BlockingFakePostsRepository().getPost(post3.id) as Result.Success).data
    }
    val uiState by viewModel.uiState.collectAsState()


    val isExpandedScreen = false

    val adaptiveInfo = currentWindowAdaptiveInfo()


    if (showSettingsDialog) {
        SettingsDialog(
            onDismiss = { onSettingsDismissed() },
        )
    }

    if (appState.isOnline) {
        SharedTransitionLayout {
            CompositionLocalProvider(
                LocalSharedTransitionScope provides this
            ) {
                NavHost(
                    modifier = modifier,
                    navController = navController,
                    startDestination = AppRoute.INBOX,
                ) {
                    composable(AppRoute.INBOX) {
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
                    composable(AppRoute.CUP_CAKE) {
                        StartOrderScreen(
                            quantityOptions = DataSource.quantityOptions,
                            onNextButtonClicked = {
                                viewModel.setQuantity(it)
                                navigateTo(AppRoute.Flavor)
                            },
                            navigateTo = navigateTo,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(dimensionResource(R.dimen.padding_medium))
                        )
                    }
                    composable(AppRoute.ARTICLES) { backStackEntry ->
                        MainScreen(
                            windowSizeClass = adaptiveInfo.windowSizeClass,
                            navigateToPlayer = { episode ->
                                appState.navigateToPlayer(episode.uri, backStackEntry)
                            }
                        )
                    }
                    composable(Screen.Player.route) {
                        PlayerScreen(
                            windowSizeClass = adaptiveInfo.windowSizeClass,
                            displayFeatures = displayFeatures,
                            onBackPress = appState::navigateBack
                        )
                    }

                    composable(route = AppRoute.Flavor) {
                        val context = LocalContext.current
                        SelectOptionScreen(
                            subtotal = uiState.price,
                            onNextButtonClicked = { navigateTo(AppRoute.Pickup) },
                            onCancelButtonClicked = {
                                viewModel.resetOrder()
                                cancelOrderAndNavigateToStart()
                            },
                            options = DataSource.flavors.map { id -> context.resources.getString(id) },
                            onSelectionChanged = { viewModel.setFlavor(it) },
                            modifier = Modifier.fillMaxHeight()
                        )
                    }
                    composable(route = AppRoute.Pickup) {
                        SelectOptionScreen(
                            subtotal = uiState.price,
                            onNextButtonClicked = { navigateTo(AppRoute.Summary) },
                            onCancelButtonClicked = {
                                cancelOrderAndNavigateToStart()
                            },
                            options = uiState.pickupOptions,
                            onSelectionChanged = { viewModel.setDate(it) },
                            modifier = Modifier.fillMaxHeight()
                        )
                    }
                    composable(route = AppRoute.Summary) {
                        val context = LocalContext.current
                        OrderSummaryScreen(
                            orderUiState = uiState,
                            onCancelButtonClicked = {
                                cancelOrderAndNavigateToStart()
                            },
                            onSendButtonClicked = { subject: String, summary: String ->
                                shareOrder(context, subject = subject, summary = summary)
                            },
                            modifier = Modifier.fillMaxHeight()
                        )
                    }
                    /*composable(route = AppRoute.MOVIE_DETAILS) {
                        MovieDetailsScreen(
                            orderUiState = uiState,
                            navigateTo = navigateTo,
                            modifier = Modifier.fillMaxHeight()
                        )
                    }*/
                     composable(route = AppRoute.MOVIE_DETAILS) {
                         PagingListScreen(
                        )
                    }
                    composable(route = AppRoute.ANIMATION_LIST) {
                        AnimationList(navigateTo = navigateTo)
                    }
                    composable(route = AppRoute.SHOW_HIDE_ANIMATION) {
                        ViewShowHideAnimation()
                    }
                    composable(route = AppRoute.VALUE_BASED_ANIMATION) {
                        ValueBasedAnimation()
                    }
                    composable(
                        route = AppRoute.JET_NEWS,
                        deepLinks = listOf(
                            navDeepLink {
                                uriPattern =
                                    "$JETNEWS_APP_URI/${JetnewsDestinations.HOME_ROUTE}?$POST_ID={$POST_ID}"
                            }
                        )
                    ) { navBackStackEntry ->
                        val homeViewModel: HomeViewModel = viewModel(
                            factory = HomeViewModel.provideFactory(
                                postsRepository = appContainer.postsRepository,
                                preSelectedPostId = navBackStackEntry.arguments?.getString(POST_ID)
                            )
                        )
                        val interestsViewModel: InterestsViewModel = viewModel(
                            factory = InterestsViewModel.provideFactory(appContainer.interestsRepository)
                        )
                        HomeRoute(
                            homeViewModel = homeViewModel,
                            interestsViewModel = interestsViewModel,
                            isExpandedScreen = isExpandedScreen,
                            openSettingDialog = { openSettingDialog() }
                        )
                    }

                    composable(LANDING_SCREEN) {
                        LandingScreen { navigateTo(it.route) }
                    }
                    Destination.entries.forEach { destination ->
                        composable(destination.route) {
                            when (destination) {
                                Destination.BrushExamples -> BrushExamplesScreen()
                                Destination.ImageExamples -> ImageExamplesScreen()
                                Destination.AnimationQuickGuideExamples -> AnimationExamplesScreen()
                                Destination.ScreenshotExample -> BitmapFromComposableFullSnippet()
                                Destination.ComponentsExamples -> ComponentsScreen {
                                    navigateTo(
                                        it.route
                                    )
                                }

                                Destination.ShapesExamples -> ApplyPolygonAsClipImage()
                                Destination.SharedElementExamples -> PlaceholderSizeAnimated_Demo()
                            }
                        }
                    }
                    TopComponentsDestination.entries.forEach { destination ->
                        composable(destination.route) {
                            when (destination) {
                                TopComponentsDestination.CardExamples -> CardExamples()
                                TopComponentsDestination.SwitchExamples -> SwitchExamples()
                                TopComponentsDestination.SliderExamples -> SliderExamples()
                                TopComponentsDestination.DialogExamples -> DialogExamples()
                                TopComponentsDestination.ChipExamples -> ChipExamples()
                                TopComponentsDestination.FloatingActionButtonExamples -> FloatingActionButtonExamples()
                                TopComponentsDestination.ButtonExamples -> ButtonExamples()
                                TopComponentsDestination.ProgressIndicatorExamples -> ProgressIndicatorExamples()
                                TopComponentsDestination.ScaffoldExample -> ScaffoldExample()
                                TopComponentsDestination.AppBarExamples -> AppBarExamples {
                                    navController.popBackStack()
                                }

                                TopComponentsDestination.CheckboxExamples -> CheckboxExamples()
                                TopComponentsDestination.DividerExamples -> DividerExamples()
                                TopComponentsDestination.BadgeExamples -> BadgeExamples()
                                TopComponentsDestination.PartialBottomSheet -> PartialBottomSheet()
                                TopComponentsDestination.TimePickerExamples -> TimePickerExamples()
                                TopComponentsDestination.DatePickerExamples -> DatePickerExamples()
                                TopComponentsDestination.CarouselExamples -> CarouselExamples()
                            }
                        }
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.HOME_ROUTE
                    ) { backStackEntry ->
                        MainContainer(
                            onSnackSelected = { snackId: Long, origin: String, from: NavBackStackEntry ->
                                // In order to discard duplicated navigation events, we check the Lifecycle
                                if (from.lifecycleIsResumed()) {
                                    navigateTo("${MainDestinations.SNACK_DETAIL_ROUTE}/$snackId?origin=$origin")
                                }
                            }
                        )
                    }

                    composableWithCompositionLocal(
                        "${MainDestinations.SNACK_DETAIL_ROUTE}/" +
                                "{${MainDestinations.SNACK_ID_KEY}}" +
                                "?origin={${MainDestinations.ORIGIN}}",
                        arguments = listOf(
                            navArgument(MainDestinations.SNACK_ID_KEY) {
                                type = NavType.LongType
                            }
                        ),

                        ) { backStackEntry ->
                        val arguments = requireNotNull(backStackEntry.arguments)
                        val snackId = arguments.getLong(MainDestinations.SNACK_ID_KEY)
                        val origin = arguments.getString(MainDestinations.ORIGIN)
                        SnackDetail(
                            snackId,
                            origin = origin ?: "",
                            upPress = { navController.navigateUp() }
                        )
                    }
                }
            }
        }

    } else {
        OfflineDialog { appState.refreshOnline() }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainContainer(
    modifier: Modifier = Modifier,
    onSnackSelected: (Long, String, NavBackStackEntry) -> Unit
) {
    val jetsnackScaffoldState = rememberJetsnackScaffoldState()
    val nestedNavController = rememberJetsnackNavController()
    val navBackStackEntry by nestedNavController.navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No SharedElementScope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No SharedElementScope found")
    JetsnackScaffold(
        bottomBar = {
            with(animatedVisibilityScope) {
                with(sharedTransitionScope) {
                    JetsnackBottomBar(
                        tabs = HomeSections.entries.toTypedArray(),
                        currentRoute = currentRoute ?: HomeSections.FEED.route,
                        navigateToRoute = nestedNavController::navigateToBottomBarRoute,
                        modifier = Modifier
                            .renderInSharedTransitionScopeOverlay(
                                zIndexInOverlay = 1f,
                            )
                            .animateEnterExit(
                                enter = fadeIn(nonSpatialExpressiveSpring()) + slideInVertically(
                                    spatialExpressiveSpring()
                                ) {
                                    it
                                },
                                exit = fadeOut(nonSpatialExpressiveSpring()) + slideOutVertically(
                                    spatialExpressiveSpring()
                                ) {
                                    it
                                }
                            )
                    )
                }
            }
        },
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(
                hostState = it,
                modifier = Modifier.systemBarsPadding(),
                snackbar = { snackbarData -> JetsnackSnackbar(snackbarData) }
            )
        },
        snackBarHostState = jetsnackScaffoldState.snackBarHostState,
    ) { padding ->
        NavHost(
            navController = nestedNavController.navController,
            startDestination = HomeSections.FEED.route
        ) {
            addHomeGraph(
                onSnackSelected = onSnackSelected,
                modifier = Modifier
                    .padding(padding)
                    .consumeWindowInsets(padding)
            )
        }
    }
}

@Composable
fun OfflineDialog(onRetry: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = stringResource(R.string.connection_error_title)) },
        text = { Text(text = stringResource(R.string.connection_error_message)) },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text(stringResource(R.string.retry_label))
            }
        }
    )
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

/**
 * Determine the drawer state to pass to the modal drawer.
 */
@Composable
private fun rememberSizeAwareDrawerState(isExpandedScreen: Boolean): DrawerState {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    return if (!isExpandedScreen) {
        // If we want to allow showing the drawer, we use a real, remembered drawer
        // state defined above
        drawerState
    } else {
        // If we don't want to allow the drawer to be shown, we provide a drawer state
        // that is locked closed. This is intentionally not remembered, because we
        // don't want to keep track of any changes and always keep it closed
        DrawerState(DrawerValue.Closed)
    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED