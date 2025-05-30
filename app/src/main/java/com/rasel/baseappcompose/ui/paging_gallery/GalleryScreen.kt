package com.rasel.baseappcompose.ui.paging_gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.Placeholder
import com.rasel.baseappcompose.data.mock_data.fakeUnsplashPhoto
import com.rasel.baseappcompose.data.model.UnsplashPhoto
import com.rasel.baseappcompose.designsystem.component.ThemePreviews
import com.rasel.baseappcompose.designsystem.theme.NiaTheme
import com.rasel.baseappcompose.ui.modifiers.interceptKey
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun GalleryScreen(
    modifier: Modifier = Modifier,
    navigateToImageview: (String) -> Unit,
    viewModel: NewsViewModel = hiltViewModel()
) {
    var query by rememberSaveable { mutableStateOf("beautiful girl") }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(query) {
        viewModel.searchRepositories(query)
    }

    val pagingData: LazyPagingItems<UnsplashPhoto> =
        viewModel.getPhotos().collectAsLazyPagingItems()

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search Photos", color = Color.White) },
            modifier = modifier
                .fillMaxWidth()
                .interceptKey(Key.Enter) {
                    // submit a search query when Enter is pressed
                    viewModel.searchRepositories(query)
                    keyboardController?.hide()
                    focusManager.clearFocus(force = true)
                },
            /* colors = TextFieldDefaults.outlinedTextFieldColors(
                 unfocusedBorderColor = Color.White,
                 focusedBorderColor = Color.Blue,
                 textColor = Color.White
             )*/
            leadingIcon = { Icon(Icons.Filled.Search, null) },
            singleLine = true,
            // keyboardOptions change the newline key to a search key on the soft keyboard
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            // keyboardActions submits the search query when the search key is pressed
            keyboardActions = KeyboardActions(
                onSearch = {
                    viewModel.searchRepositories(query)
                    keyboardController?.hide()
                }
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        PhotosList(pagingData, navigateToImageview)
    }
}

@Composable
fun PhotosList(
    pagingData: LazyPagingItems<UnsplashPhoto>,
    navigateToImageview: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(pagingData.itemCount) { index ->
            val repository = pagingData[index]
            if (repository != null) {
                RepositoryItem(repository, navigateToImageview)
            }
        }
        when (val state = pagingData.loadState.refresh) { //FIRST LOAD
            is LoadState.Error -> {
                //TODO Error Item
                //state.error to get error message
            }

            is LoadState.Loading -> { // Loading UI
                item {
                    Column(
                        /*modifier = Modifier
                            .fillParentMaxSize(),*/
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = "Refresh Loading"
                        )

                        CircularProgressIndicator(color = Color.Black)
                    }
                }
            }

            else -> {}
        }

        when (val state = pagingData.loadState.append) { // Pagination
            is LoadState.Error -> {
                //TODO Pagination Error Item
                //state.error to get error message
            }

            is LoadState.Loading -> { // Pagination Loading UI
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(text = "Pagination Loading")

                        CircularProgressIndicator(color = Color.Black)
                    }
                }
            }

            else -> {}
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RepositoryItem(article: UnsplashPhoto, navigateToImageview: (String) -> Unit) {
    Box(
        modifier = Modifier
            .background(Color.Black)
            .padding(8.dp)
            .clickable { navigateToImageview.invoke(article.urls.regular) }
    ) {
        GlideImage(
            modifier = Modifier
                .aspectRatio(9f / 16f),
            model = article.urls.regular,
            contentScale = ContentScale.Crop,
            contentDescription = ""
        )
    }
}

@ThemePreviews()
@Composable
fun PreviewPhotosList() {
    NiaTheme {
        /**  * The preview function should be responsible for creating the fake data and passing it to the  * function that displays it.  */
        // create list of fake data for preview

        // create pagingData from a list of fake data
         val pagingData = PagingData.from(fakeUnsplashPhoto)
        // pass pagingData containing fake data to a MutableStateFlow
        val fakeDataFlow = MutableStateFlow(pagingData)

        val post = fakeDataFlow.collectAsLazyPagingItems()

        PhotosList(pagingData = post, navigateToImageview =  {})
    }
}
