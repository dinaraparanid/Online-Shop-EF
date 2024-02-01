package com.paranid5.emonlineship.data.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.paranid5.emonlineshop.data.Favourites
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SQLDelightModule {
    @Provides
    @Singleton
    fun provideSQLDriver(@ApplicationContext context: Context): SqlDriver =
        AndroidSqliteDriver(
            schema = Favourites.Schema,
            context = context,
            name = "favourites.db"
        )
}