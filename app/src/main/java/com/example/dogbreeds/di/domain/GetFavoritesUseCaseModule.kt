package com.example.dogbreeds.di.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import domain.core.Logger
import domain.repository.FavoritesRepository
import domain.service.BreedsService
import domain.usecase.fetchBreeds.FetchBreeds
import domain.usecase.fetchBreeds.FetchBreedsUseCase
import domain.usecase.fetchImageLinks.FetchImageLinks
import domain.usecase.fetchImageLinks.FetchImageLinksUseCase
import domain.usecase.getFavorites.GetFavorites
import domain.usecase.getFavorites.GetFavoritesUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GetFavoritesUseCaseModule {

    @Provides
    @Singleton
    fun provide(
        favoritesRepository: FavoritesRepository,
    ): GetFavoritesUseCase = GetFavorites(favoritesRepository)
}
