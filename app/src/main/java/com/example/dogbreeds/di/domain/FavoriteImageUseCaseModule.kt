package com.example.dogbreeds.di.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import domain.core.Logger
import domain.repository.FavoritesRepository
import domain.service.BreedsService
import domain.usecase.favoriteImage.FavoriteImage
import domain.usecase.favoriteImage.FavoriteImageUseCase
import domain.usecase.fetchBreeds.FetchBreeds
import domain.usecase.fetchBreeds.FetchBreedsUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoriteImageUseCaseModule {

    @Provides
    @Singleton
    fun provide(
        favoritesRepository: FavoritesRepository,
    ): FavoriteImageUseCase = FavoriteImage(favoritesRepository)
}
