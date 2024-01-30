package com.paranid5.emonlineship.data

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.paranid5.emonlineship.data.states.UserProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageHandler @Inject constructor(
    @ApplicationContext context: Context
) : CoroutineScope by CoroutineScope(Dispatchers.IO) {
    private val Context.dataStore by preferencesDataStore("config")
    private val dataStore = context.dataStore

    val userProvider by lazy { UserProvider(dataStore) }
}