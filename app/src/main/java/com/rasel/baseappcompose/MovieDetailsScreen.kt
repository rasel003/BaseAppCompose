package com.rasel.baseappcompose

/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rasel.baseappcompose.data.model.OrderUiState
import com.rasel.baseappcompose.ui.components.ReadMoreTextCompose
import com.rasel.baseappcompose.ui.components.ViewCounter
import com.rasel.baseappcompose.ui.theme.JetnewsTheme

/**
 * This composable expects [orderUiState] that represents the order state, [onCancelButtonClicked]
 * lambda that triggers canceling the order and passes the final order to [onSendButtonClicked]
 * lambda
 */
@Composable
fun MovieDetailsScreen(
    orderUiState: OrderUiState,
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val resources = LocalContext.current.resources

    val numberOfCupcakes = resources.getQuantityString(
        R.plurals.cupcakes,
        orderUiState.quantity,
        orderUiState.quantity
    )
    //Load and format a string resource with the parameters.
    val orderSummary = stringResource(
        R.string.order_details,
        numberOfCupcakes,
        orderUiState.flavor,
        orderUiState.date,
        orderUiState.quantity
    )
    val newOrder = stringResource(R.string.new_cupcake_order)
    //Create a list of order summary to display
    val items = listOf(
        // Summary line 1: display selected quantity
        Pair(stringResource(R.string.quantity), numberOfCupcakes),
        // Summary line 2: display selected flavor
        Pair(stringResource(R.string.flavor), orderUiState.flavor),
        // Summary line 3: display selected pickup date
        Pair(stringResource(R.string.pickup_date), orderUiState.date)
    )

    val (expanded, onExpandedChange) = rememberSaveable { mutableStateOf(false) }


    Column(
        modifier = modifier.verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(R.drawable.avatar_10),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "This Division",
                style = MaterialTheme.typography.titleSmall
            )
           TextButton (

               onClick = {}
           ) {
               Text(
                   text = "This Division",
                   style = MaterialTheme.typography.titleLarge
               )
           }
        }

        Row {
            Image(
                painter = painterResource(R.drawable.avatar_1),
                contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
            )
            Image(
                painter = painterResource(R.drawable.avatar_2),
                contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            ViewCounter(
                drawableResource = R.drawable.avatar_1,
                title = "12M",
                description = "Followers"
            )
            ViewCounter(
                drawableResource = R.drawable.avatar_1,
                title = "14K",
                description = "Viewers"
            )
        }
        ReadMoreTextCompose(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(15.dp),
            textString = "Android development is the process of creating mobile applications that run on the Android operating system. It involves a multifaceted approach that requires a diverse skillset and deep technical knowledge. At the core, Android developers utilize the Java or Kotlin programming languages to write the code that brings their app ideas to life. They leverage the Android Software Development Kit (SDK) and a range of powerful tools to design intuitive user interfaces, implement complex functionality, and ensure seamless performance across a wide variety of Android devices. The development workflow typically includes tasks like setting up the development environment, designing wireframes and mockups, writing efficient and well-structured code, integrating third-party libraries and APIs, testing the app thoroughly, and ultimately publishing it on the Google Play Store for users to download and enjoy. Throughout this process, Android developers must stay up-to-date with the latest Android platform updates, design guidelines, and industry best practices to create apps that are not only visually appealing and user-friendly, but also secure, scalable, and optimized for the Android ecosystem. It's a dynamic and constantly evolving field that demands a keen eye for detail, problem-solving abilities, and a passion for delivering high-quality mobile experiences.",
            expandedState = expanded,
            onExpandedChange = onExpandedChange
        )
        Row {
            Image(
                painter = painterResource(R.drawable.avatar_1),
                contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
            )
            Image(
                painter = painterResource(R.drawable.avatar_2),
                contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onSendButtonClicked(newOrder, orderSummary) }
        ) {
            Text("Install")
        }
    }
}

@Preview
@Composable
fun MovieDetailsPreview() {
    JetnewsTheme {
        MovieDetailsScreen(
            orderUiState = OrderUiState(0, "Test", "Test", "$300.00"),
            onSendButtonClicked = { subject: String, summary: String -> },
            onCancelButtonClicked = {},
            modifier = Modifier.fillMaxHeight()
        )
    }
}