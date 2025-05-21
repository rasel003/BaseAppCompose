package com.rasel.baseappcompose.ui.profile

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.rasel.baseappcompose.R
import com.rasel.baseappcompose.data.mock_data.photoLists
import com.rasel.baseappcompose.designsystem.theme.NiaTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingAppBarDemo(modifier: Modifier = Modifier) {


    val context = LocalContext.current
    val isDarkTheme = isSystemInDarkTheme()

    LaunchedEffect(Unit) {
        // set the status bar icon color
        (context as ComponentActivity).enableEdgeToEdge(
            statusBarStyle = if (isDarkTheme) {
                SystemBarStyle.light(
                    scrim = android.graphics.Color.TRANSPARENT,
                    darkScrim = android.graphics.Color.TRANSPARENT
                )
            } else {
                SystemBarStyle.dark(
                    scrim = android.graphics.Color.TRANSPARENT
                )
            }
        )
    }

    val expandedAppBarHeight = 180.dp
    // header translation should be half of expandedAppBarHeight
    val headerTranslation = (expandedAppBarHeight / 2)

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val scrollState = scrollBehavior.state
    val appBarExpanded by remember {
        // app bar expanded state
        // collapsedFraction 1f = collapsed
        // collapsedFraction 0f = expanded
        derivedStateOf { scrollState.collapsedFraction < 0.9f }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Column(
                modifier = Modifier
                    .zIndex(0f)
                    .fillMaxWidth()
                    // the app bar background color
                    .background(MaterialTheme.colorScheme.tertiary)
            ) {
                CollapsedAppBar(visible = !appBarExpanded)
                TopAppBar(
                    title = {
                        AppBarHeader(
                            modifier = Modifier.graphicsLayer {
                                // set the header translationY based on the
                                // scroll behavior state collapsedFraction
                                translationY =
                                    scrollState.collapsedFraction * headerTranslation.toPx()
                            },
                            visible = appBarExpanded
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent
                    ),
                    expandedHeight = expandedAppBarHeight,
                    // remove extra padding on top (from status bar)
                    windowInsets = WindowInsets(0),
                    scrollBehavior = scrollBehavior
                )
            }
        },
    ) { innerPadding ->
        val cornerRadius = 16.dp
        Box(
            modifier = Modifier
                .zIndex(1f)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .background(MaterialTheme.colorScheme.tertiary)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    // manually draw the elevation
                    .drawBehind {
                        val shadowColor = Color.Black.copy(alpha = 0.25f)
                        val paint = Paint().asFrameworkPaint().apply {
                            color = android.graphics.Color.TRANSPARENT
                            setShadowLayer(
                                4.dp.toPx(), // shadow blur
                                0f, // shadow offset x
                                (-1).dp.toPx(), // shadow offset y
                                shadowColor.toArgb() // shadow color
                            )
                        }

                        drawIntoCanvas {
                            it.nativeCanvas.drawRoundRect(
                                0f,
                                0f,
                                size.width,
                                size.height / 2,
                                cornerRadius.toPx(),
                                cornerRadius.toPx(),
                                paint
                            )
                        }
                    }
                    // background with rounded corner on top side
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(
                            topStart = cornerRadius,
                            topEnd = cornerRadius
                        )
                    )
            ) {
                PhotoGridHeader(scrollBehavior = scrollBehavior)
                PhotoGrid(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    items = photoLists,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsedAppBar(
    modifier: Modifier = Modifier,
    visible: Boolean
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            // back icon on the collapsed app bar
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        title = {
            // animate fadeIn fadeOut the avatar + name on the collapsed app bar
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween()),
                exit = fadeOut(animationSpec = tween())
            ) {
                // avatar + name on the collapsed app bar
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.surfaceDim,
                                shape = CircleShape
                            ),
                        painter = painterResource(R.drawable.avatar_1),
                        contentDescription = null
                    )
                    Text(
                        text = "John Doe",
                        color = MaterialTheme.colorScheme.onTertiary,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            // set background color for expanded state to transparent
            containerColor = Color.Transparent,
            // set background color for collapsed state to transparent
            scrolledContainerColor = Color.Transparent,
            // set navigation icon color
            navigationIconContentColor = MaterialTheme.colorScheme.onTertiary
        )
    )
}

@Composable
fun AppBarHeader(
    modifier: Modifier = Modifier,
    visible: Boolean
) {
    // animate fadeIn fadeOut the expanded app bar header
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween()),
        exit = fadeOut(animationSpec = tween())
    ) {
        Column(
            // only add end padding
            // because the TopAppBar already has start padding
            modifier = modifier.padding(end = 16.dp, top = 4.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.surfaceDim,
                            shape = CircleShape
                        ),
                    painter = painterResource(R.drawable.avatar_1),
                    contentDescription = null
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "John Doe",
                        color = MaterialTheme.colorScheme.onTertiary,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "@johndoe",
                        color = MaterialTheme.colorScheme.onTertiary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Text(
                text = stringResource(R.string.empty_screen_subtitle),
                color = MaterialTheme.colorScheme.onTertiary,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoGridHeader(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val coroutineScope = rememberCoroutineScope()
    val dragState = remember { mutableFloatStateOf(0f) }

    Column(
        modifier = modifier
            // detect user drag gesture so user able to scroll the content
            // by dragging the header on top of the content
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        coroutineScope.launch {
                            // dragState.floatValue * 2 = scroll snap speed
                            val velocity = Velocity(0f, dragState.floatValue * 2)
                            scrollBehavior.nestedScrollConnection.onPostFling(
                                Velocity.Zero,
                                velocity
                            )
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        dragState.floatValue = dragAmount.y

                        // dragAmount.y * 0.6f = adjust the scroll speed
                        // to match with the LazyVerticalGrid scroll speed
                        // change the 0.6f multiplier if needed
                        val scrollDelta = Offset(0f, dragAmount.y * 0.6f)

                        val preConsumed = scrollBehavior.nestedScrollConnection.onPreScroll(
                            scrollDelta,
                            NestedScrollSource.UserInput
                        )
                        val remaining = scrollDelta - preConsumed
                        scrollBehavior.nestedScrollConnection.onPostScroll(
                            consumed = preConsumed,
                            available = remaining,
                            NestedScrollSource.UserInput
                        )
                    }
                )
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .padding(top = 12.dp)
        ) {
            // drag handle indicator
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(width = 36.dp, height = 5.dp)
                    .background(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = CircleShape
                    )
            )
        }
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                text = "Photos",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.outlineVariant)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                text = "Likes",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun PhotoGrid(
    modifier: Modifier = Modifier,
    items: List<String>
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(items = items, key = { it }) { imageUrl ->
            // requires coil dependency
            // https://github.com/coil-kt/coil
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .aspectRatio(1f),
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}


@Preview
@Composable
fun CollapsingAppBarDemoFabPreview() {
    NiaTheme {
        CollapsingAppBarDemo()
    }
}