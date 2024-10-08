/*
 * Copyright 2024 The Android Open Source Project
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

package com.rasel.baseappcompose.domain.di

import com.rasel.baseappcompose.domain.player.EpisodePlayer
import com.example.jetcaster.core.player.MockEpisodePlayer
import com.rasel.baseappcompose.data.Dispatcher
import com.rasel.baseappcompose.data.NiaDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainDiModule {
    @Provides
    @Singleton
    fun provideEpisodePlayer(
        @Dispatcher(NiaDispatchers.Main) mainDispatcher: CoroutineDispatcher
    ): EpisodePlayer = MockEpisodePlayer(mainDispatcher)
}
