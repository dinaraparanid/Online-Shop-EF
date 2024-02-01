package com.paranid5.emonlineship.data.config

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.paranid5.emonlineship.data.config.sources.user.UserDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigRepository @Inject constructor(
    @ApplicationContext context: Context
) : CoroutineScope by CoroutineScope(Dispatchers.IO) {
    private val Context.dataStore by preferencesDataStore("config")
    private val dataStore = context.dataStore

    val userDataSource: UserDataSource by lazy { UserDataSource(dataStore) }
}