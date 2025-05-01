package com.rasel.baseappcompose.ui.reply

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import androidx.window.layout.DisplayFeature
import com.rasel.baseappcompose.DEEPLINK_DOMAIN
import com.rasel.baseappcompose.NiaApplication.Companion.JETNEWS_APP_URI
import com.rasel.baseappcompose.R
import com.rasel.baseappcompose.animations.AnimationExamplesScreen
import com.rasel.baseappcompose.animations.sharedelement.LocalSharedTransitionScope
import com.rasel.baseappcompose.animations.sharedelement.PlaceholderSizeAnimated_Demo
import com.rasel.baseappcompose.data.AppContainer
import com.rasel.baseappcompose.data.DataSource
import com.rasel.baseappcompose.data.Result
import com.rasel.baseappcompose.data.mock_data.colleagueProfile
import com.rasel.baseappcompose.data.mock_data.exampleUiState
import com.rasel.baseappcompose.data.mock_data.randomSampleImageUrl
import com.rasel.baseappcompose.data.posts.impl.BlockingFakePostsRepository
import com.rasel.baseappcompose.data.posts.impl.post3
import com.rasel.baseappcompose.designsystem.component.AppBarExamples
import com.rasel.baseappcompose.designsystem.component.BadgeExamples
import com.rasel.baseappcompose.designsystem.component.ButtonExamples
import com.rasel.baseappcompose.designsystem.component.CardExamples
import com.rasel.baseappcompose.designsystem.component.CarouselExamples
import com.rasel.baseappcompose.designsystem.component.CheckboxExamples
import com.rasel.baseappcompose.designsystem.component.ChipExamples
import com.rasel.baseappcompose.designsystem.component.ComponentsScreen
import com.rasel.baseappcompose.designsystem.component.DatePickerExamples
import com.rasel.baseappcompose.designsystem.component.DialogExamples
import com.rasel.baseappcompose.designsystem.component.DividerExamples
import com.rasel.baseappcompose.designsystem.component.FloatingActionButtonExamples
import com.rasel.baseappcompose.designsystem.component.PartialBottomSheet
import com.rasel.baseappcompose.designsystem.component.ProgressIndicatorExamples
import com.rasel.baseappcompose.designsystem.component.ScaffoldExample
import com.rasel.baseappcompose.designsystem.component.SliderExamples
import com.rasel.baseappcompose.designsystem.component.SwitchExamples
import com.rasel.baseappcompose.designsystem.component.TimePickerExamples
import com.rasel.baseappcompose.ui.bookmarks.navigation.bookmarksScreen
import com.rasel.baseappcompose.ui.bookmarks.navigation.navigateToBookmarks
import com.rasel.baseappcompose.ui.box_with_constraints.navigation.boxWithConstraintScreen
import com.rasel.baseappcompose.ui.camera_preview.CameraPreviewScreen
import com.rasel.baseappcompose.ui.conversation.ConversationContent
import com.rasel.baseappcompose.ui.cup_cake.AnimationList
import com.rasel.baseappcompose.ui.cup_cake.MovieDetailsScreen
import com.rasel.baseappcompose.ui.cup_cake.ValueBasedAnimation
import com.rasel.baseappcompose.ui.cup_cake.ViewShowHideAnimation
import com.rasel.baseappcompose.ui.foryou.navigation.forYouSection
import com.rasel.baseappcompose.ui.foryou.navigation.navigateToForYou
import com.rasel.baseappcompose.ui.graphics.ApplyPolygonAsClipImage
import com.rasel.baseappcompose.ui.graphics.BitmapFromComposableFullSnippet
import com.rasel.baseappcompose.ui.graphics.BrushExamplesScreen
import com.rasel.baseappcompose.ui.home.HomeRoute
import com.rasel.baseappcompose.ui.home.HomeViewModel
import com.rasel.baseappcompose.ui.images.ImageExamplesScreen
import com.rasel.baseappcompose.ui.images.ImageView
import com.rasel.baseappcompose.ui.images.navigation.ImageviewRoute
import com.rasel.baseappcompose.ui.interests.InterestsViewModel
import com.rasel.baseappcompose.ui.interests.navigation.navigateToInterests
import com.rasel.baseappcompose.ui.interests2pane.interestsListDetailScreen
import com.rasel.baseappcompose.ui.jet_caster.home.MainScreen
import com.rasel.baseappcompose.ui.jet_caster.player.PlayerScreen
import com.rasel.baseappcompose.ui.jet_news.interests.InterestsRoute
import com.rasel.baseappcompose.ui.landing.LandingScreen
import com.rasel.baseappcompose.ui.navigation.AppRoute
import com.rasel.baseappcompose.ui.navigation.AppRoute.CONVERSATION_DETAILS_SCREEN
import com.rasel.baseappcompose.ui.navigation.AppRoute.CONVERSATION_SCREEN
import com.rasel.baseappcompose.ui.navigation.AppRoute.LANDING_SCREEN
import com.rasel.baseappcompose.ui.navigation.AppRoute.PAGING_3
import com.rasel.baseappcompose.ui.navigation.Destination
import com.rasel.baseappcompose.ui.navigation.POST_ID
import com.rasel.baseappcompose.ui.navigation.Screen
import com.rasel.baseappcompose.ui.navigation.TopComponentsDestination
import com.rasel.baseappcompose.ui.order.OrderSummaryScreen
import com.rasel.baseappcompose.ui.order.OrderViewModel
import com.rasel.baseappcompose.ui.order.SelectOptionScreen
import com.rasel.baseappcompose.ui.order.StartOrderScreen
import com.rasel.baseappcompose.ui.profile.ProfileScreen
import com.rasel.baseappcompose.ui.search.navigation.navigateToSearch
import com.rasel.baseappcompose.ui.search.navigation.searchScreen
import com.rasel.baseappcompose.ui.setting.SettingsDialog
import com.rasel.baseappcompose.ui.snack_home.composableWithCompositionLocal
import com.rasel.baseappcompose.ui.snackdetail.SnackDetail
import com.rasel.baseappcompose.ui.topic.navigation.navigateToTopic
import com.rasel.baseappcompose.ui.topic.navigation.topicScreen
import com.rasel.baseappcompose.ui.utils.NiaAppState
import com.rasel.baseappcompose.ui.utils.ReplyContentType
import com.rasel.baseappcompose.ui.utils.ReplyNavigationType
import com.rasel.baseappcompose.ui.utils.TopLevelDestination
import kotlinx.coroutines.runBlocking

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ReplyNavHost(
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
    interestsViewModel: InterestsViewModel = hiltViewModel(),
    showSettingsDialog: Boolean,
    openSettingDialog: () -> Unit,
    onSettingsDismissed: () -> Unit,
    cancelOrderAndNavigateToStart: () -> Unit,
    navigationActions: NiaAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    openDrawer: () -> Unit = {},
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


    val context = LocalContext.current
    var hasPermission by remember {
        mutableStateOf(if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else true)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
    }


    if(!hasPermission && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Button(onClick = {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }) {
            Text(text = "Request permission")
        }
    }
   else if (navigationActions.isOnline) {
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
                            toggleSelectedEmail = toggleSelectedEmail,
                            navigateToPaging3 = { navigationActions.navigateTo(PAGING_3) },
                            navigateToChat = {
                                navigationActions.navigateTo(CONVERSATION_SCREEN)
                            }
                        )
                    }
                    composable(AppRoute.CUP_CAKE) {
                        StartOrderScreen(
                            quantityOptions = DataSource.quantityOptions,
                            onNextButtonClicked = {
                                viewModel.setQuantity(it)
                                navigationActions.navigateTo(AppRoute.Flavor)
                            },
                            navigateTo = navigationActions::navigateTo,
                            navigateToForYou = {
                                navigationActions.navController.navigateToForYou(
                                    NavOptions.Builder().build()
                                )
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(dimensionResource(R.dimen.padding_medium)),
                            navController
                        )
                    }
                    composable(AppRoute.ARTICLES) { backStackEntry ->
                        MainScreen(
                            windowSizeClass = adaptiveInfo.windowSizeClass,
                            navigateToPlayer = { episode ->
                                navigationActions.navigateToPlayer(episode.uri, backStackEntry)
                            }
                        )
                    }
                    composable(Screen.Player.route) {
                        PlayerScreen(
                            windowSizeClass = adaptiveInfo.windowSizeClass,
                            displayFeatures = displayFeatures,
                            onBackPress = navigationActions::navigateBack
                        )
                    }

                    forYouSection(
                        onTopicClick = navController::navigateToTopic,
                        navigateToSearch = { navController.navigateToSearch() },
                        onTopAppBarActionClick = {navController.navigateToBookmarks(NavOptions.Builder().build())},
                    ) {
                        topicScreen(
                            showBackButton = true,
                            onBackClick = navController::popBackStack,
                            onTopicClick = navController::navigateToTopic,
                        )
                    }
                    bookmarksScreen(
                        onTopicClick = navController::navigateToInterests,
                        onShowSnackbar = onShowSnackbar,
                    )
                    boxWithConstraintScreen()
                    searchScreen(
                        onBackClick = navController::popBackStack,
                        onInterestsClick = {
                            navigationActions.navigateToTopLevelDestination(
                                TopLevelDestination.INTERESTS
                            )
                        },
                        onTopicClick = navController::navigateToInterests,
                    )
                    interestsListDetailScreen()

                    composable(route = AppRoute.Flavor) {
                        SelectOptionScreen(
                            subtotal = uiState.price,
                            onNextButtonClicked = { navigationActions.navigateTo(AppRoute.Pickup) },
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
                            onNextButtonClicked = { navigationActions.navigateTo(AppRoute.Summary) },
                            onCancelButtonClicked = {
                                cancelOrderAndNavigateToStart()
                            },
                            options = uiState.pickupOptions,
                            onSelectionChanged = { viewModel.setDate(it) },
                            modifier = Modifier.fillMaxHeight()
                        )
                    }
                    composable(route = AppRoute.Summary) {
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
                    composable(route = AppRoute.MOVIE_DETAILS) {
                        MovieDetailsScreen(
                            orderUiState = uiState,
                            navigateTo = navigationActions::navigateTo,
                            modifier = Modifier.fillMaxHeight()
                        )
                    }
                    /*composable(route = PAGING_3) {
                        GalleryScreen(navigateToImageview = navController::navigateToImageview)
                    } */
                    composable(route = PAGING_3) {
                        CameraPreviewScreen()
                    }
                    composable<ImageviewRoute>(
                        deepLinks = listOf(
                            navDeepLink<ImageviewRoute>(
                                basePath = "https://$DEEPLINK_DOMAIN"
                            )
                        )
                    ) { entry ->
                        val imageviewRoute = entry.toRoute<ImageviewRoute>()
                        ImageView(
                            randomSampleImageUrl(),
                            upPress = { navController.navigateUp() })
                    }
                    composable(route = AppRoute.ANIMATION_LIST) {
                        AnimationList(navigateTo = navigationActions::navigateTo)
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
                                    "$JETNEWS_APP_URI/${AppRoute.HOME_ROUTE}?$POST_ID={$POST_ID}"
                            }
                        )
                    ) { navBackStackEntry ->
                        val homeViewModel: HomeViewModel = viewModel(
                            factory = HomeViewModel.provideFactory(
                                postsRepository = appContainer.postsRepository,
                                preSelectedPostId = navBackStackEntry.arguments?.getString(POST_ID)
                            )
                        )
                        HomeRoute(
                            homeViewModel = homeViewModel,
                            interestsViewModel = interestsViewModel,
                            isExpandedScreen = isExpandedScreen,
                            openDrawer = openDrawer,
                            openSettingDialog = { openSettingDialog() }
                        )
                    }
                    composable(
                        route = AppRoute.HOME_ROUTE,
                        deepLinks = listOf(
                            navDeepLink {
                                uriPattern =
                                    "$JETNEWS_APP_URI/${AppRoute.HOME_ROUTE}?$POST_ID={$POST_ID}"
                            }
                        )
                    ) { navBackStackEntry ->
                        val homeViewModel: HomeViewModel = viewModel(
                            factory = HomeViewModel.provideFactory(
                                postsRepository = appContainer.postsRepository,
                                preSelectedPostId = navBackStackEntry.arguments?.getString(POST_ID)
                            )
                        )
                        HomeRoute(
                            homeViewModel = homeViewModel,
                            interestsViewModel = interestsViewModel,
                            isExpandedScreen = isExpandedScreen,
                            openDrawer = openDrawer,
                            openSettingDialog = openSettingDialog,
                        )
                    }
                    composable(AppRoute.INTERESTS_ROUTE) {
                        InterestsRoute(
                            interestsViewModel = interestsViewModel,
                            isExpandedScreen = isExpandedScreen,
                            openDrawer = openDrawer
                        )
                    }

                    composable(LANDING_SCREEN) {
                        LandingScreen { navigationActions.navigateTo(it.route) }
                    }
                    Destination.entries.forEach { destination ->
                        composable(destination.route) {
                            when (destination) {
                                Destination.BrushExamples -> BrushExamplesScreen()
                                Destination.ImageExamples -> ImageExamplesScreen()
                                Destination.AnimationQuickGuideExamples -> AnimationExamplesScreen()
                                Destination.ScreenshotExample -> BitmapFromComposableFullSnippet()
                                Destination.ComponentsExamples -> ComponentsScreen {
                                    navigationActions.navigateTo(
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
                        route = AppRoute.HOME_ROUTE,
                    ) { backStackEntry ->
                        MainContainer(
                            onSnackSelected = { snackId: Long, origin: String, from: NavBackStackEntry ->
                                // In order to discard duplicated navigation events, we check the Lifecycle
                                if (from.lifecycleIsResumed()) {
                                    navigationActions.navigateTo("${AppRoute.SNACK_DETAIL_ROUTE}/$snackId?origin=$origin")
                                }
                            }
                        )
                    }
                    composableWithCompositionLocal(
                        "${AppRoute.SNACK_DETAIL_ROUTE}/" +
                                "{${AppRoute.SNACK_ID_KEY}}" +
                                "?origin={${AppRoute.ORIGIN}}",
                        arguments = listOf(
                            navArgument(AppRoute.SNACK_ID_KEY) {
                                type = NavType.LongType
                            }
                        ),

                        ) { backStackEntry ->
                        val arguments = requireNotNull(backStackEntry.arguments)
                        val snackId = arguments.getLong(AppRoute.SNACK_ID_KEY)
                        val origin = arguments.getString(AppRoute.ORIGIN)
                        SnackDetail(
                            snackId,
                            origin = origin ?: "",
                            upPress = { navController.navigateUp() }
                        )
                    }
                    composable(CONVERSATION_SCREEN) {
                        ConversationContent(
                            uiState = exampleUiState,
                            navigateToProfile = { navigationActions.navigateTo(CONVERSATION_DETAILS_SCREEN) },
                            onNavIconPressed = { navigationActions.openDrawer() }
                        )
                    }
                    composable(CONVERSATION_DETAILS_SCREEN) {
                        ProfileScreen(userData = colleagueProfile)
                    }
                }
            }
        }

    } else {
        OfflineDialog { navigationActions.refreshOnline() }
    }
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
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED