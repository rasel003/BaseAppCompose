package com.rasel.baseappcompose.ui.paging_gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rasel.baseappcompose.data.model.UnsplashPhoto
import com.rasel.baseappcompose.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository,
): ViewModel() {

    private val queryFlow = MutableStateFlow("")


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val photosFlow = queryFlow
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isNotEmpty()) {
                repository.searchPhotos(query).cachedIn(viewModelScope)
            } else {
                flowOf(PagingData.empty())
            }
        }

    fun searchRepositories(query: String) {
        queryFlow.value = query
    }

    fun getPhotos(): Flow<PagingData<UnsplashPhoto>> {
        return photosFlow
    }

//    fun getBreakingNews(query: String): Flow<PagingData<UnsplashPhoto>> = repository.searchPhotos(query).cachedIn(viewModelScope)
}