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

package com.rasel.baseappcompose.data.di

import com.rasel.baseappcompose.data.Dispatcher
import com.rasel.baseappcompose.data.NiaDispatchers
import com.rasel.baseappcompose.data.network.NiaNetworkDataSource
import com.rasel.baseappcompose.data.network.retrofit.AuthAuthenticator
import com.rasel.baseappcompose.data.network.retrofit.NetworkConnectionInterceptor
import com.rasel.baseappcompose.data.network.retrofit.RetrofitNiaNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.Call
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @Dispatcher(NiaDispatchers.Main)
    @Singleton
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Dispatcher(NiaDispatchers.Default)
    @Singleton
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Dispatcher(NiaDispatchers.IO)
    @Singleton
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideRetrofitNiaNetwork(
        networkJson: Json,
        okhttpCallFactory: dagger.Lazy<Call.Factory>,
        networkConnectionInterceptor: NetworkConnectionInterceptor,
        authAuthenticator: AuthAuthenticator
    ): NiaNetworkDataSource = RetrofitNiaNetwork(networkJson, okhttpCallFactory, networkConnectionInterceptor, authAuthenticator)

}
