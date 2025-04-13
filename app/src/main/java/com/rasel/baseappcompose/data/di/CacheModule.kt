package com.rasel.baseappcompose.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object CacheModule {

   /* @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context, workManager: WorkManager): AppDatabase {
        return AppDatabase.invoke(context, workManager)
    }

    @Provides
    fun providePlantDao(appDatabase: AppDatabase): PlantDao {
        return appDatabase.getPlantDao()
    }

    @Provides
    fun providePreferenceProvider(@ApplicationContext context: Context): PreferenceProvider {
        return PreferenceProvider(context)
    }

    @Provides
    @Singleton
    fun provideCharacterDao(appDatabase: AppDatabase): CharacterDao {
        return appDatabase.cachedCharacterDao()
    }

    @Provides
    @Singleton
    fun provideCharacterCache(characterCache: CharacterCacheImp): CharacterCache {
        return characterCache
    }

    @Provides
    @Singleton
    fun provideLocalizationCache(localizationCache: LocalizationCacheImp): LocalizationCache {
        return localizationCache
    }*/
}
