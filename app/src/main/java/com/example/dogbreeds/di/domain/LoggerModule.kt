package com.example.dogbreeds.di.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import domain.core.Logger
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoggerModule {

    @Provides
    @Singleton
    fun provide(): Logger {
        // TODO: If production, we could use Timber or any other logger
        return object : Logger {
            override fun d(event: String) {
                println("DEBUG: $event")
            }

            override fun w(event: String) {
                println("WARNING: $event")
            }

            override fun e(throwable: Throwable) {
                throwable.printStackTrace()
            }
        }
    }
}
