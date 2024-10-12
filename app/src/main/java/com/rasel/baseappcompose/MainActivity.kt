package com.rasel.baseappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.rasel.baseappcompose.data.model.DarkThemeConfig
import com.rasel.baseappcompose.designsystem.theme.NiaTheme
import com.rasel.baseappcompose.ui.JetnewsApplication
import com.rasel.baseappcompose.ui.reply.ReplyApp
import com.rasel.baseappcompose.ui.reply.ReplyHomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ReplyHomeViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by viewModels()


    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appContainer = (application as JetnewsApplication).container
        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)


        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainActivityViewModel.uiState
                    .onEach { uiState = it }
                    .collect()
            }
        }

        setContent {

            val darkTheme = shouldUseDarkTheme(uiState)

            NiaTheme(
                darkTheme = darkTheme,
                androidTheme = shouldUseDarkTheme(uiState),
                disableDynamicTheming = shouldDisableDynamicTheming(uiState),
            ) {
                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }*/
                val displayFeatures = calculateDisplayFeatures(this)
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val windowSize = calculateWindowSizeClass(this)

               /* CupcakeApp(
                    displayFeatures = displayFeatures,
                    replyHomeUIState = uiState,
                    windowSize = windowSize
                )*/
                /*JetnewsApp(
                    appContainer = appContainer,
                    widthSizeClass = windowSize.widthSizeClass
                )*/


                ReplyApp(
                    windowSize = windowSize,
                    displayFeatures = displayFeatures,
                    replyHomeUIState = uiState,
                    closeDetailScreen = {
                        viewModel.closeDetailScreen()
                    },
                    navigateToDetail = { emailId, pane ->
                        viewModel.setOpenedEmail(emailId, pane)
                    },
                    toggleSelectedEmail = { emailId ->
                        viewModel.toggleSelectedEmail(emailId)
                    },
                    appContainer = appContainer
                )
            }
        }
    }
}


/**
 * Returns `true` if the dynamic color is disabled, as a function of the [uiState].
 */
@Composable
private fun shouldDisableDynamicTheming(
    uiState: MainActivityUiState,
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> false
    is MainActivityUiState.Success -> !uiState.userData.useDynamicColor
}

/**
 * Returns `true` if dark theme should be used, as a function of the [uiState] and the
 * current system context.
 */
@Composable
private fun shouldUseDarkTheme(
    uiState: MainActivityUiState,
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> isSystemInDarkTheme()
    is MainActivityUiState.Success -> when (uiState.userData.darkThemeConfig) {
        DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
        DarkThemeConfig.LIGHT -> false
        DarkThemeConfig.DARK -> true
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NiaTheme {
       // CupcakeApp(displayFeatures = emptyList())
    }
}

