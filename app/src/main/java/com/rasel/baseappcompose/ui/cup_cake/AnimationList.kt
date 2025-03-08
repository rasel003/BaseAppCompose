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
import com.rasel.baseappcompose.ui.navigation.AppRoute
import com.rasel.baseappcompose.ui.shared.EpisodeListItem

@Composable
fun AnimationList(
    navigateTo: (String) -> Unit
) {

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
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navigateTo(AppRoute.SHOW_HIDE_ANIMATION) }
        ) {
            Text("Show Hide Animation")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navigateTo(AppRoute.VALUE_BASED_ANIMATION) }
        ) {
            Text("Value based Animation")
        }

    }

}


@ThemePreviews
@Composable
fun AnimationListPreview() {
    NiaTheme {
        AnimationList(navigateTo = { })
    }
}