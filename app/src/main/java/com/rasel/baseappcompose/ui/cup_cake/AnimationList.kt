package com.rasel.baseappcompose.ui.cup_cake

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import com.rasel.baseappcompose.designsystem.component.ThemePreviews
import com.rasel.baseappcompose.designsystem.theme.NiaTheme
import com.rasel.baseappcompose.ui.navigation.AppRoute

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