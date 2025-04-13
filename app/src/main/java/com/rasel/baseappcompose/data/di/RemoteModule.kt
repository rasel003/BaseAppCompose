package com.rasel.baseappcompose.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.rasel.baseappcompose.core.datastore.TokenManager
import com.rasel.baseappcompose.data.network.retrofit.AuthAuthenticator
import com.rasel.baseappcompose.data.network.retrofit.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager =
        TokenManager(context)

    @Singleton
    @Provides
    fun provideAuthAuthenticator(tokenManager: TokenManager): AuthAuthenticator =
        AuthAuthenticator(tokenManager)

    @Singleton
    @Provides
    fun provideNetworkConnectionInterceptor(
        @ApplicationContext context: Context,
        tokenManager: TokenManager
    ): NetworkConnectionInterceptor {
        return NetworkConnectionInterceptor(tokenManager, context)
    }

   /* @Provides
    @Singleton
    fun provideBlogService(
        networkConnectionInterceptor: NetworkConnectionInterceptor,
        authAuthenticator: AuthAuthenticator
    ): CharacterService {
        return ServiceFactory.createCharacterService(
            BuildConfig.DEBUG,
            BuildConfig.BASE_URL,
            networkConnectionInterceptor,
            authAuthenticator
        )
    }

    @Singleton
    @Provides
    fun provideMyApi(
        networkConnectionInterceptor: NetworkConnectionInterceptor,
        authAuthenticator: AuthAuthenticator
    ): MyApi {
        return ServiceFactory.create(
            BuildConfig.DEBUG,
            BuildConfig.BASE_URL,
            networkConnectionInterceptor,
            authAuthenticator
        )

    }

    @Singleton
    @Provides
    fun provideAuthAPIService(): AuthApiService =
        ServiceFactory.createAuthAPIService("https://api.escuelajs.co/api/v1/")

    @Singleton
    @Provides
    fun provideMainAPIService(
        networkConnectionInterceptor: NetworkConnectionInterceptor,
        authAuthenticator: AuthAuthenticator
    ): MainApiService =
        ServiceFactory.createMainAPIService(
            BuildConfig.DEBUG,
            "https://api.escuelajs.co/api/v1/",
            networkConnectionInterceptor,
            authAuthenticator
        )

    @Provides
    @Singleton
    fun provideCharacterRemote(characterRemote: CharacterRemoteImp): CharacterRemote {
        return characterRemote
    }

    @Provides
    @Singleton
    fun provideLocalizationRemote(localizationRemote: LocalizationRemoteImp): LocalizationRemote {
        return localizationRemote
    }*/
}
