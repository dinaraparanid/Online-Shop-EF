package com.paranid5.emonlineship.data.config.sources.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.paranid5.emonlineshop.domain.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserDataSource(private val dataStore: DataStore<Preferences>) {
    private companion object {
        private val USER = stringPreferencesKey("user")
    }

    private val json by lazy { Json { ignoreUnknownKeys = true } }

    @OptIn(ExperimentalCoroutinesApi::class)
    val userFlow by lazy {
        dataStore.data
            .mapLatest { preferences -> preferences[USER] }
            .mapLatest { userStr -> userStr?.let(json::decodeUser) }
    }

    suspend fun storeUser(user: User) {
        dataStore.edit { preferences ->
            preferences[USER] = json.encodeToString(user)
        }
    }
}

private fun Json.decodeUser(userStr: String) =
    decodeFromString<User>(userStr)