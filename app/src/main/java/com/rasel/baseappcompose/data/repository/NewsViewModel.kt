package com.rasel.baseappcompose.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rasel.baseappcompose.data.model.UnsplashPhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository,
): ViewModel() {

    fun getBreakingNews(): Flow<PagingData<UnsplashPhoto>> = repository.getNews().cachedIn(viewModelScope)
}