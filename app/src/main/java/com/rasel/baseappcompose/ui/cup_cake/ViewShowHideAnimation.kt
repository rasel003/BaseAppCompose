package com.rasel.baseappcompose.ui.cup_cake

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayCircleFilled
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.compose.snippets.animations.colorBlue
import com.google.samples.apps.nowinandroid.core.designsystem.component.ThemePreviews
import com.rasel.baseappcompose.R
import com.rasel.baseappcompose.designsystem.theme.NiaTheme
import com.rasel.baseappcompose.domain.PreviewEpisodes
import com.rasel.baseappcompose.domain.PreviewPodcasts
import com.rasel.baseappcompose.ui.shared.EpisodeListItem

@Composable
fun ViewShowHideAnimation() {

    val density = LocalDensity.current

    // Create a MutableTransitionState<Boolean> for the AnimatedVisibility.
    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            // Apply safe area padding to the whole Column
            .padding(WindowInsets.safeDrawing.asPaddingValues())
    ) {
        // animation 1
        Row {
            var visible by remember { mutableStateOf(true) }

            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically {
                    // Slide in from 40 dp from the top.
                    with(density) { -40.dp.roundToPx() }
                } + expandVertically(
                    // Expand from the top.
                    expandFrom = Alignment.Top
                ) + fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {

                Text(
                    "Hello",
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.secondary)
                        .clickable {
                            visible = visible.not()
                        }
                )
            }
        }


        // animation 2
        AnimatedVisibility(visibleState = state) {
            Text(text = "Hello, world!",
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        state.targetState.not()
                    })
        }

        // Use the MutableTransitionState to know the current animation state
        // of the AnimatedVisibility.
        Text(
            text = when {
                state.isIdle && state.currentState -> "Visible"
                !state.isIdle && state.currentState -> "Disappearing"
                state.isIdle && !state.currentState -> "Invisible"
                else -> "Appearing"
            }
        )


        // animation 3
        Row {
            var visible by remember { mutableStateOf(true) }

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                // Fade in/out the background and the foreground.
                Box(
                    Modifier
                        .width(100.dp)
                        .background(Color.DarkGray)
                ) {
                    Box(
                        Modifier
                            .align(Alignment.Center)
                            .animateEnterExit(
                                // Slide in/out the inner box.
                                enter = slideInVertically(),
                                exit = slideOutVertically()
                            )
                            .sizeIn(minWidth = 256.dp, minHeight = 64.dp)
                            .background(Color.Red)
                            .clickable {
                                visible = visible.not()
                            }
                    ) {
                        // Content of the notification…
                    }
                }
            }
        }

        // animation 4
        Row {
            var visible by remember { mutableStateOf(true) }

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(),
                exit = fadeOut()
            ) { // this: AnimatedVisibilityScope
                // Use AnimatedVisibilityScope#transition to add a custom animation
                // to the AnimatedVisibility.
                val background by transition.animateColor(label = "color") { state ->
                    if (state == EnterExitState.Visible) Color.Blue else Color.Gray
                }
                Box(
                    modifier = Modifier
                        .size(128.dp)
                        .background(background)
                )
            }
        }

        // animation 5
        Row {
            var count by remember { mutableIntStateOf(0) }
            Button(onClick = { count++ }) {
                Text("Add")
            }
            AnimatedContent(
                targetState = count,
                label = "animated content"
            ) { targetCount ->
                // Make sure to use `targetCount`, not `count`.
                Text(text = "Count: $targetCount")
            }
        }

        // animation 6
        Row {
            var count by remember { mutableIntStateOf(0) }
            Button(onClick = { count++ }) {
                Text("Add")
            }
            AnimatedContent(
                targetState = count,
                transitionSpec = {
                    // Compare the incoming number with the previous number.
                    if (targetState > initialState) {
                        // If the target number is larger, it slides up and fades in
                        // while the initial (smaller) number slides up and fades out.
                        slideInVertically { height -> height } + fadeIn() togetherWith
                                slideOutVertically { height -> -height } + fadeOut()
                    } else {
                        // If the target number is smaller, it slides down and fades in
                        // while the initial number slides down and fades out.
                        slideInVertically { height -> -height } + fadeIn() togetherWith
                                slideOutVertically { height -> height } + fadeOut()
                    }.using(
                        // Disable clipping since the faded slide-in/out should
                        // be displayed out of bounds.
                        SizeTransform(clip = false)
                    )
                }, label = "animated content"
            ) { targetCount ->
                Text(text = "$targetCount")
            }
        }

        // animation 7
        Row {
            var expanded by remember { mutableStateOf(false) }
            Surface(
                color = MaterialTheme.colorScheme.primary,
                onClick = { expanded = !expanded }
            ) {
                AnimatedContent(
                    targetState = expanded,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(150, 150)) togetherWith
                                fadeOut(animationSpec = tween(150)) using
                                SizeTransform { initialSize, targetSize ->
                                    if (targetState) {
                                        keyframes {
                                            // Expand horizontally first.
                                            IntSize(targetSize.width, initialSize.height) at 150
                                            durationMillis = 300
                                        }
                                    } else {
                                        keyframes {
                                            // Shrink vertically first.
                                            IntSize(initialSize.width, targetSize.height) at 150
                                            durationMillis = 300
                                        }
                                    }
                                }
                    }, label = "size transform"
                ) { targetExpanded ->
                    if (targetExpanded) {
                        Image(
                            imageVector = Icons.Rounded.PlayCircleFilled,
                            contentDescription = stringResource(R.string.cd_play),
                            contentScale = ContentScale.Fit,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                            modifier = Modifier
                                .clickable { /* TODO */ }
                                .size(48.dp)
                                .padding(6.dp)
                                .semantics { role = Role.Button }
                        )
                    } else {
                        NiaTheme {
                            EpisodeListItem(
                                episode = PreviewEpisodes[0],
                                podcast = PreviewPodcasts[0],
                                onClick = {},
                                onQueueEpisode = {},
                                showSummary = true
                            )
                        }
                    }
                }
            }
        }

        // animation 8
        var currentPage by remember { mutableStateOf("A") }
        Crossfade(targetState = currentPage, label = "cross fade") { screen ->
            when (screen) {
                "A" -> Text("Page A", modifier = Modifier.clickable { currentPage = "B" })
                "B" -> Text("Page B", modifier = Modifier.clickable { currentPage = "A" })
            }
        }


        // animation 8
        var expanded by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .background(colorBlue)
                .animateContentSize()
                .height(if (expanded) 400.dp else 200.dp)
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    expanded = !expanded
                }

        ) {
        }

    }

}


@ThemePreviews
@Composable
fun ViewShowHideAnimationPreview() {
    NiaTheme {
        ViewShowHideAnimation()
    }
}