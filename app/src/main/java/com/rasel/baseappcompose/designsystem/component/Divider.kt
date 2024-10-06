/*
 * Copyright 2023 The Android Open Source Project
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

package com.rasel.baseappcompose.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.background
import com.rasel.baseappcompose.designsystem.theme.JetsnackTheme
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.unit.ColorProvider
import com.rasel.baseappcompose.designsystem.theme.JetnewsGlanceColorScheme
import com.rasel.baseappcompose.designsystem.theme.NiaTheme

/**
 * A thin line that groups content in lists and layouts.
 *
 * @param thickness thickness in dp of this divider line.
 * @param color color of this divider line.
 */
@Composable
fun Divider(
    thickness: Dp = DividerDefaults.Thickness,
    color: ColorProvider = DividerDefaults.color
) {
    Spacer(
        modifier = GlanceModifier
            .fillMaxWidth()
            .height(thickness)
            .background(color)
    )
}

/** Default values for [Divider] */
object DividerDefaults {
    /** Default thickness of a divider. */
    val Thickness: Dp = 1.dp

    /** Default color of a divider. */
    val color: ColorProvider @Composable get() = JetnewsGlanceColorScheme.outlineVariant
}


@Preview
@Composable
fun DividerExamples() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Column with divider", fontWeight = FontWeight.Bold)
        HorizontalDividerExample()
        Text("Row with divider", fontWeight = FontWeight.Bold)
        VerticalDividerExample()
    }
}

@Preview
// [START android_compose_components_horizontaldivider]
@Composable
fun HorizontalDividerExample() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text("First item in list")
        HorizontalDivider(thickness = 2.dp)
        Text("Second item in list")
    }
}
// [END android_compose_components_horizontaldivider]

@Preview
// [START android_compose_components_verticaldivider]
@Composable
fun VerticalDividerExample() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("First item in row")
        VerticalDivider(color = MaterialTheme.colorScheme.secondary)
        Text("Second item in row")
    }
}
// [END android_compose_components_verticaldivider]



@Composable
fun JetsnackDivider(
    modifier: Modifier = Modifier,
    color: Color = JetsnackTheme.colors.uiBorder.copy(alpha = DividerAlpha),
    thickness: Dp = 1.dp
) {
    HorizontalDivider(
        modifier = modifier,
        color = color,
        thickness = thickness
    )
}

private const val DividerAlpha = 0.12f

@Preview("default", showBackground = true)
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun DividerPreview() {
    NiaTheme {
        Box(Modifier.size(height = 10.dp, width = 100.dp)) {
            JetsnackDivider(Modifier.align(Alignment.Center))
        }
    }
}
