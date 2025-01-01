package com.rasel.baseappcompose.ui.cup_cake

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateRect
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.rounded.PlayCircleFilled
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
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
fun ValueBasedAnimation() {

    Column {

        Row {
            var enabled by remember { mutableStateOf(true) }

            val alpha: Float by animateFloatAsState(if (enabled) 1f else 0.5f, label = "alpha")
            Box(
                Modifier
                    .fillMaxSize()
                    .graphicsLayer(alpha = alpha)
                    .background(Color.Red)
            )
        }
        Row {
            var currentState by remember { mutableStateOf(BoxState.Collapsed) }
            val transition = updateTransition(currentState, label = "box state")

            val rect: Rect by transition.animateRect(label = "rectangle") { state ->
                when (state) {
                    BoxState.Collapsed -> Rect(0f, 0f, 100f, 100f)
                    BoxState.Expanded -> Rect(100f, 100f, 300f, 300f)
                }
            }
            val borderWidth by transition.animateDp(label = "border width") { state ->
                when (state) {
                    BoxState.Collapsed -> 1.dp
                    BoxState.Expanded -> 0.dp
                }
            }

            val color by transition.animateColor(
                transitionSpec = {
                    when {
                        BoxState.Expanded isTransitioningTo BoxState.Collapsed -> spring(stiffness = 50f)
                        else -> tween(durationMillis = 500)
                    }
                }, label = "color"
            ) { state ->
                when (state) {
                    BoxState.Collapsed -> MaterialTheme.colorScheme.primary
                    BoxState.Expanded -> MaterialTheme.colorScheme.background
                }
            }

            // Start in collapsed state and immediately animate to expanded
//            var currentState = remember { MutableTransitionState(BoxState.Collapsed) }
//            currentState.targetState = BoxState.Expanded
//            val transition = rememberTransition(currentState, label = "box state")


        }
        Row {
            var selected by remember { mutableStateOf(false) }
// Animates changes when `selected` is changed.
            val transition = updateTransition(selected, label = "selected state")
            val borderColor by transition.animateColor(label = "border color") { isSelected ->
                if (isSelected) Color.Magenta else Color.White
            }
            val elevation by transition.animateDp(label = "elevation") { isSelected ->
                if (isSelected) 10.dp else 2.dp
            }
            Surface(
                onClick = { selected = !selected },
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, borderColor),
                shadowElevation = elevation
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Hello, world!")
                    // AnimatedVisibility as a part of the transition.
                    transition.AnimatedVisibility(
                        visible = { targetSelected -> targetSelected },
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
                        Text(text = "It is fine today.")
                    }
                    // AnimatedContent as a part of the transition.
                    transition.AnimatedContent { targetState ->
                        if (targetState) {
                            Text(text = "Selected")
                        } else {
                            Icon(imageVector = Icons.Default.Phone, contentDescription = "Phone")
                        }
                    }
                }
            }
        }

    }

}

enum class BoxState {
    Collapsed,
    Expanded
}

@Composable
fun AnimatingBox(boxState: BoxState) {
    val transitionData = updateTransitionData(boxState)
    // UI tree
    Box(
        modifier = Modifier
            .background(transitionData.color)
            .size(transitionData.size)
    )
}

// Holds the animation values.
private class TransitionData(
    color: State<Color>,
    size: State<Dp>
) {
    val color by color
    val size by size
}

// Create a Transition and return its animation values.
@Composable
private fun updateTransitionData(boxState: BoxState): TransitionData {
    val transition = updateTransition(boxState, label = "box state")
    val color = transition.animateColor(label = "color") { state ->
        when (state) {
            BoxState.Collapsed -> Color.Gray
            BoxState.Expanded -> Color.Red
        }
    }
    val size = transition.animateDp(label = "size") { state ->
        when (state) {
            BoxState.Collapsed -> 64.dp
            BoxState.Expanded -> 128.dp
        }
    }
    return remember(transition) { TransitionData(color, size) }
}


@ThemePreviews
@Composable
fun ValueBasedAnimationPreview() {
    NiaTheme {
        ValueBasedAnimation()
    }
}