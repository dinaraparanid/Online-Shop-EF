package com.paranid5.emonlineship.data.di

import com.paranid5.emonlineship.data.ktor.KtorClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KtorModule {
    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient = KtorClient()
}