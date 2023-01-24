package com.example.dogbreeds.di.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import domain.core.Logger
import domain.service.BreedsService
import domain.usecase.fetchBreeds.FetchBreeds
import domain.usecase.fetchBreeds.FetchBreedsUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FetchBreedsUseCaseModule {

    @Provides
    @Singleton
    fun provide(
        breedsService: BreedsService,
        logger: Logger
    ): FetchBreedsUseCase = FetchBreeds(breedsService, logger)
}
