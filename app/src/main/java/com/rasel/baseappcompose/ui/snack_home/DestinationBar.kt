/*
 * Copyright 2020 The Android Open Source Project
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

@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.rasel.baseappcompose.ui.snack_home

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.rasel.baseappcompose.R
import com.rasel.baseappcompose.animations.sharedelement.LocalNavAnimatedVisibilityScope
import com.rasel.baseappcompose.animations.sharedelement.LocalSharedTransitionScope
import com.rasel.baseappcompose.designsystem.component.JetsnackDivider
import com.rasel.baseappcompose.designsystem.component.JetsnackPreviewWrapper
import com.rasel.baseappcompose.designsystem.theme.AlphaNearOpaque
import com.rasel.baseappcompose.designsystem.theme.LocalBackgroundTheme
import com.rasel.baseappcompose.ui.snackdetail.spatialExpressiveSpring

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun DestinationBar(modifier: Modifier = Modifier) {
    val sharedElementScope =
        LocalSharedTransitionScope.current ?: throw IllegalStateException("No shared element scope")
    val navAnimatedScope =
        LocalNavAnimatedVisibilityScope.current ?: throw IllegalStateException("No nav scope")
    with(sharedElementScope) {
        with(navAnimatedScope) {
            Column(
                modifier = modifier
                    .renderInSharedTransitionScopeOverlay()
                    .animateEnterExit(
                        enter = slideInVertically(spatialExpressiveSpring()) { -it * 2 },
                        exit = slideOutVertically(spatialExpressiveSpring()) { -it * 2 }
                    )
            ) {
                TopAppBar(
                    windowInsets = WindowInsets(0, 0, 0, 0),
                    title = {
                        Row {
                            Text(
                                text = "Delivery to 1600 Amphitheater Way",
                                style = MaterialTheme.typography.titleMedium,
                                color =  MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .weight(1f)
                                    .align(Alignment.CenterVertically)
                            )
                            IconButton(
                                onClick = { /* todo */ },
                                modifier = Modifier.align(Alignment.CenterVertically)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.ExpandMore,
                                    tint =  MaterialTheme.colorScheme.onBackground,
                                    contentDescription =
                                    stringResource(R.string.label_select_delivery)
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors().copy(
                        containerColor = LocalBackgroundTheme.current.color
                            .copy(alpha = AlphaNearOpaque),
                        titleContentColor =   MaterialTheme.colorScheme.onBackground
                    ),
                )
                JetsnackDivider()
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewDestinationBar() {
    JetsnackPreviewWrapper {
        DestinationBar()
    }
}
