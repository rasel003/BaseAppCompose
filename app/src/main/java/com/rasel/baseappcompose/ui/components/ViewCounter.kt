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

package com.rasel.baseappcompose.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rasel.baseappcompose.JetnewsDestinations
import com.rasel.baseappcompose.R
import com.rasel.baseappcompose.ui.theme.JetnewsTheme

@Composable
fun ViewCounter(
    drawableResource: Int,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Row{
        Image(
            modifier = modifier
                .size(14.dp)
                .clip(CircleShape),
            painter = painterResource(id = drawableResource),
            contentDescription = description,
        )
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 8.dp),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = description,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewViewCounter() {
    JetnewsTheme {
        ViewCounter(
            drawableResource = R.drawable.avatar_7,
            title = "12M",
            description = "Followers"
        )
    }
}