package com.rasel.baseappcompose.ui.cup_cake

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rasel.baseappcompose.R



/*
SharedTransitionLayout {
    var isExpanded by remember { mutableStateOf(false) }

    val boundsTransform = { _: Rect, _: Rect -> tween<Rect>(550) }

    AnimatedContent(targetState = isExpanded) { target ->
        if (!target) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp)
                    .clickable {
                        isExpanded = !isExpanded
                    }
            ) {
                Image(
                    modifier = Modifier
                        .sharedElement(
                            state = rememberSharedContentState(key = "image"),
                            animatedVisibilityScope = this@AnimatedContent,
                            boundsTransform = boundsTransform,
                        )
                        .size(130.dp),
                    painter = painterResource(id = R.drawable.pokemon_preview),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier
                        .sharedElement(
                            state = rememberSharedContentState(key = "name"),
                            animatedVisibilityScope = this@AnimatedContent,
                            boundsTransform = boundsTransform,
                        )
                        .fillMaxWidth()
                        .padding(12.dp),
                    text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. ",
                    fontSize = 12.sp,
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        isExpanded = !isExpanded
                    }
            ) {
                Image(
                    modifier = Modifier
                        .sharedElement(
                            state = rememberSharedContentState(key = "image"),
                            animatedVisibilityScope = this@AnimatedContent,
                            boundsTransform = boundsTransform,
                        )
                        .fillMaxWidth()
                        .height(320.dp),
                    painter = painterResource(id = R.drawable.pokemon_preview),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier
                        .sharedElement(
                            state = rememberSharedContentState(key = "name"),
                            animatedVisibilityScope = this@AnimatedContent,
                            boundsTransform = boundsTransform,
                        )
                        .fillMaxWidth()
                        .padding(21.dp),
                    text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. ",
                    fontSize = 12.sp,
                )
            }
        }
    }
}*/
