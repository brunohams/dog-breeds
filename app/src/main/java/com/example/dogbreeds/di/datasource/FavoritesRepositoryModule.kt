package com.example.dogbreeds.di.datasource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import datasource.repository.FavoritesMemoryRepository
import domain.repository.FavoritesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoritesRepositoryModule {

    @Provides
    @Singleton
    fun provide(): FavoritesRepository {
        // Using a memory implementation for now
        // Later we could use a database implementation like room
        return FavoritesMemoryRepository()
    }
}
