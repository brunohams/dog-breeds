package com.example.dogbreeds.di.datasource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import datasource.core.http.Hosts
import datasource.service.BreedsHttpService
import domain.service.BreedsService
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BreedsServiceModule {

    @Provides
    @Singleton
    fun provide(
        httpClient: HttpClient
    ): BreedsService {
        // To test with mock version, we could use a conditional here
        // If flavor == mock then return BreedsMockService()
        return BreedsHttpService(Hosts.BASE_URL, httpClient)
    }
}
