/*
 * Copyright 2021 The Android Open Source Project
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

import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.samples.apps.nowinandroid.core.designsystem.component.ThemePreviews

import com.rasel.baseappcompose.designsystem.theme.LocalBackgroundTheme
import com.rasel.baseappcompose.designsystem.theme.NiaTheme

/**
 * An alternative to [androidx.compose.material3.Snackbar] utilizing
 * [com.example.jetsnack.ui.theme.JetsnackColors]
 */
@Composable
fun JetsnackSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    actionOnNewLine: Boolean = false,
    shape: Shape = MaterialTheme.shapes.small,
    backgroundColor: Color = LocalBackgroundTheme.current.color,
    contentColor: Color =  Color.Magenta,
    actionColor: Color = Color.Green
) {
    Snackbar(
        snackbarData = snackbarData,
        modifier = modifier,
        actionOnNewLine = actionOnNewLine,
        shape = shape,
        containerColor = backgroundColor,
        contentColor = contentColor,
        actionColor = actionColor
    )
}

/*
@ThemePreviews
@Composable
fun JetsnackSnackbarPreview() {
    NiaTheme {
        JetsnackSnackbar(
            snackbarData = SnackbarData()
        )
    }
}
*/
