package com.example.dogbreeds.di.datasource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import datasource.core.http.HttpClientFactory
import datasource.core.http.HttpConfigFactory
import datasource.core.http.HttpEngineFactory
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {

    @Provides
    @Singleton
    fun provide(): HttpClient {
        val config = HttpConfigFactory().build()
        val engine = HttpEngineFactory(config).build()
        return HttpClientFactory(engine).build()
    }
}
