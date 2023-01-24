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
import domain.usecase.unfavoriteImage.UnfavoriteImage
import domain.usecase.unfavoriteImage.UnfavoriteImageUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UnfavoriteImageUseCaseModule {

    @Provides
    @Singleton
    fun provide(
        favoritesRepository: FavoritesRepository,
    ): UnfavoriteImageUseCase = UnfavoriteImage(favoritesRepository)
}
