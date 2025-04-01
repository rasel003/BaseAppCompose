package com.rasel.baseappcompose.ui.images

import android.content.res.Configuration
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.rasel.baseappcompose.R
import com.rasel.baseappcompose.designsystem.theme.Neutral8
import com.rasel.baseappcompose.designsystem.theme.NiaTheme
import com.rasel.baseappcompose.ui.snack_home.Profile


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageView(
    imageUrl : String,
    upPress: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box {
            IconButton(
                onClick = upPress,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .size(36.dp)
                    .background(
                        color = Neutral8.copy(alpha = 0.32f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    tint = Color.Black,
                    contentDescription = stringResource(R.string.label_back),
                )
            }
            GlideImage(
                modifier = Modifier
                    .background(Color.Black),
                model = imageUrl,
                contentScale = ContentScale.Inside,
                contentDescription = ""
            )
        }
    }
}
@PreviewLightDark
@Composable
fun ImageViewPreview() {
    NiaTheme {
        ImageView(imageUrl = "https://images.unsplash.com/photo-1531416738519-cf1b25c203cc?ixid=M3wxNTYwMDZ8MHwxfHNlYXJjaHw3fHxnaXJsfGVufDB8fHx8MTc0MzE4NzEzNXww\\u0026ixlib=rb-4.0.3", upPress = {})
    }
}